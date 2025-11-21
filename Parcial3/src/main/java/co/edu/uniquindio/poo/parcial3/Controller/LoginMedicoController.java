package co.edu.uniquindio.poo.parcial3.Controller;

import co.edu.uniquindio.poo.parcial3.Model.Clinica;
import co.edu.uniquindio.poo.parcial3.Model.Medico;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginMedicoController {

    @FXML
    private TextField txtEmail;

    @FXML
    private PasswordField txtPassword;

    private Clinica clinica;

    @FXML
    public void initialize() {
        clinica = Clinica.getInstancia("Clínica UNIQUINDIO", "123456789");
        System.out.println("Médicos en sistema: " + clinica.getMedicos().size());

        // Mostrar datos de médicos disponibles (para debug)
        for (Medico medico : clinica.getMedicos()) {
            System.out.println("Médico: " + medico.getNombre() +
                    " | Email: " + medico.getEmail() +
                    " | Contraseña: " + medico.getContraseña());
        }
    }

    @FXML
    private void iniciarSesion(ActionEvent event) {
        String email = txtEmail.getText().trim();
        String password = txtPassword.getText().trim();

        if (email.isEmpty() || password.isEmpty()) {
            mostrarAlerta("Error", "Por favor ingrese email y contraseña");
            return;
        }

        try {
            // Buscar médico por email en los datos quemados de la clínica
            Medico medico = buscarMedicoPorEmail(email);

            if (medico != null && medico.getContraseña().equals(password)) {
                // Login exitoso
                abrirDashboardMedico(medico);

            } else {
                mostrarAlerta("Error", "Email o contraseña incorrectos");
            }

        } catch (Exception e) {
            mostrarAlerta("Error", "Error al iniciar sesión: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private Medico buscarMedicoPorEmail(String email) {
        for (Medico medico : clinica.getMedicos()) {
            if (medico.getEmail().equalsIgnoreCase(email)) {
                return medico;
            }
        }
        return null;
    }

    private void abrirDashboardMedico(Medico medico) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/poo/parcial3/DoctorView.fxml"));
            Parent root = loader.load();

            // Pasar el médico al controlador del dashboard
            MedicoController controller = loader.getController();
            controller.inicializarMedico(medico);
            controller.inicializarMedico(medico);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Dashboard Médico - " + medico.getNombre());
            stage.setMaximized(true);
            stage.show();

            // Cerrar ventana de login
            cerrarVentanaLogin();

        } catch (Exception e) {
            mostrarAlerta("Error", "No se pudo abrir el dashboard: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void cerrarVentanaLogin() {
        Stage stage = (Stage) txtEmail.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void limpiarCampos(ActionEvent event) {
        txtEmail.clear();
        txtPassword.clear();
        txtEmail.requestFocus();
    }

    @FXML
    private void mostrarCredencialesEjemplo(ActionEvent event) {
        StringBuilder credenciales = new StringBuilder();
        credenciales.append("Credenciales de Médicos:\n\n");

        for (Medico medico : clinica.getMedicos()) {
            credenciales.append("• ").append(medico.getNombre())
                    .append("\n  Email: ").append(medico.getEmail())
                    .append("\n  Contraseña: ").append(medico.getContraseña())
                    .append("\n  Especialidad: ").append(medico.getEspecialidad())
                    .append("\n  Teléfono: ").append(medico.getTelefono())
                    .append("\n\n");
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Credenciales de Médicos");
        alert.setHeaderText("Médicos Disponibles en el Sistema");
        alert.setContentText(credenciales.toString());
        alert.setWidth(500);
        alert.showAndWait();
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}