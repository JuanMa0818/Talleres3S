package co.edu.uniquindio.poo.parcial3.Controller;

import co.edu.uniquindio.poo.parcial3.Model.Clinica;
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

public class LoginPacienteController {

    @FXML
    private TextField txtEmail;

    @FXML
    private PasswordField txtPassword;

    private Clinica clinica;

    public void initialize() {
        // Obtienes SIEMPRE la misma instancia de la clínica
        clinica = Clinica.getInstancia("Hospital Central", "900123456");
    }

    @FXML
    public void loginPaciente(ActionEvent event) {
        String email = txtEmail.getText().trim();
        String password = txtPassword.getText().trim();

        if(email.isEmpty() || password.isEmpty()){
            mostrarAlerta("Campos vacíos", "Ingresa email y contraseña");
            return;
        }

        Paciente paciente = clinica.getPacientes().stream()
                .filter(p -> p.getEmail().equalsIgnoreCase(email)
                        && p.getContraseña().equals(password))
                .findFirst()
                .orElse(null);

        if (paciente != null) {
            abrirVistaPaciente(paciente);
        } else {
            mostrarAlerta("Datos incorrectos", "Email o contraseña incorrectos");
        }
    }

    private void abrirVistaPaciente(Paciente paciente) {
        try {

            // Lista de rutas posibles
            String[] rutas = {
                    "/co/edu/uniquindio/poo/parcial3/paciente/PacienteView.fxml",
                    "/co/edu/uniquindio/poo/parcial3/pacient/PacienteView.fxml",
                    "/co/edu/uniquindio/poo/parcial3/View/PacienteView.fxml",
                    "PaciezznteView.fxml"
            };

            FXMLLoader loader = null;
            Parent root = null;

            for(String ruta : rutas){
                try{
                    loader = new FXMLLoader(getClass().getResource(ruta));
                    root = loader.load();
                    System.out.println("FXML encontrado en: " + ruta);
                    break;
                } catch (Exception ignored){
                    System.out.println("No se encontró en: " + ruta);
                }
            }

            if(root == null){
                mostrarAlerta("Error", "No se pudo cargar la interfaz del paciente");
                return;
            }

            PacienteController controller = loader.getController();
            controller.setPacienteActual(paciente);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Panel Paciente - " + paciente.getNombre());
            stage.setResizable(false);
            stage.show();

            // Cerrar login
            Stage loginStage = (Stage) txtEmail.getScene().getWindow();
            loginStage.close();

        } catch (Exception e){
            e.printStackTrace();
            mostrarAlerta("Error", "Error al abrir la vista: " + e.getMessage());
        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
