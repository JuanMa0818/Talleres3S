package co.edu.uniquindio.poo.parcial3.Controller;

import co.edu.uniquindio.poo.parcial3.Model.Clinica;
import co.edu.uniquindio.poo.parcial3.Model.Medico;
import co.edu.uniquindio.poo.parcial3.Model.Paciente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;

public class LoginController {

    @FXML
    private TextField txtEmail;

    @FXML
    private PasswordField txtPassword;

    private Clinica clinica;

    @FXML
    public void initialize() {
        clinica = Clinica.getInstancia("Hospital Central", "900123456");
    }

    @FXML
    private void iniciarSesion(ActionEvent event) {
        String email = txtEmail.getText().trim();
        String password = txtPassword.getText().trim();

        if (email.isEmpty() || password.isEmpty()) {
            mostrarAlerta("Error", "Por favor complete todos los campos");
            return;
        }

        // Buscar paciente
        Paciente paciente = clinica.getPacientes().stream()
                .filter(p -> p.getEmail().equalsIgnoreCase(email) && p.getContraseña().equals(password))
                .findFirst()
                .orElse(null);

        if (paciente != null) {
            mostrarAlerta("Éxito", "Bienvenido/a " + paciente.getNombre());
            abrirVistaPaciente(paciente);
            return;
        }

        // Buscar médico
        Medico medico = clinica.getMedicos().stream()
                .filter(m -> m.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);

        if (medico != null) {
            mostrarAlerta("Éxito", "Bienvenido/a Dr. " + medico.getNombre());
            abrirVistaMedico(medico);
            return;
        }

        mostrarAlerta("Error", "Credenciales incorrectas. Use:\n" +
                "Paciente: camila@gmail.com / 1234\n" +
                "Médico: carlos.medico@hospital.com");
    }

    private void abrirVistaPaciente(Paciente paciente) {
        try {

            String ruta = "/co/edu/uniquindio/poo/parcial3/pacient/PacienteView.fxml";
            URL url = getClass().getResource(ruta);

            if (url == null) {
                System.out.println("ARCHIVO NO ENCONTRADO: " + ruta);
                mostrarAlerta("Error", "El archivo PacienteView.fxml no existe en:\n" + ruta);
                return;
            }

            System.out.println(" ARCHIVO ENCONTRADO: " + url.toString());

            FXMLLoader loader = new FXMLLoader(url);
            Parent root = loader.load();

            System.out.println("FXML CARGADO EXITOSAMENTE");

            // Obtener el controlador y pasar el paciente
            PacienteController controller = loader.getController();
            if (controller == null) {
                System.out.println("CONTROLADOR ES NULL - Verifica fx:controller en FXML");
                mostrarAlerta("Error", "El controlador no se pudo cargar. Verifica fx:controller en el FXML.");
                return;
            }

            controller.setPacienteActual(paciente);
            System.out.println(" PACIENTE PASADO AL CONTROLADOR: " + paciente.getNombre());

            Stage stage = new Stage();
            stage.setScene(new Scene(root, 800, 600));
            stage.setTitle("Panel Paciente - " + paciente.getNombre());
            stage.show();

            // Cerrar login
            ((Stage) txtEmail.getScene().getWindow()).close();

        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Error", "Error al abrir vista: " + e.getMessage());
        }
    }

    private void abrirVistaMedico(Medico medico) {
        try {
            String ruta = "/co/edu/uniquindio/poo/parcial3/DoctorView.fxml";
            URL url = getClass().getResource(ruta);

            if (url == null) {
                System.out.println("ARCHIVO NO ENCONTRADO: " + ruta);
                mostrarAlerta("Error", "El archivo DoctorView.fxml no existe en:\n" + ruta);
                return;
            }

            FXMLLoader loader = new FXMLLoader(url);
            Parent root = loader.load();

            MedicoController controller = loader.getController();
            controller.setMedicoActual(medico);

            Stage stage = new Stage();
            stage.setScene(new Scene(root, 1000, 700));
            stage.setTitle("Panel Médico - " + medico.getNombre());
            stage.show();

            ((Stage) txtEmail.getScene().getWindow()).close();

        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo abrir la vista del médico: " + e.getMessage());
        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}