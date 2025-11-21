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
        // Obtener la instancia de la clínica
        clinica = Clinica.getInstancia("Hospital Central", "900123456");
    }

    @FXML
    public void loginPaciente(ActionEvent event) {
        String email = txtEmail.getText().trim();
        String password = txtPassword.getText().trim();

        // Buscar paciente con email y contraseña correctos
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/poo/parcial3/paciente.view/PacienteView.fxml"));
            Parent root = loader.load();

            // Pasar datos del paciente al controller
            co.edu.uniquindio.poo.parcial3.Controller.PacienteController controller =
                    loader.getController();
            controller.setPacienteActual(paciente);

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Panel Paciente - " + paciente.getNombre());
            stage.setResizable(false);
            stage.show();

            // Cerrar ventana de login
            Stage loginStage = (Stage) txtEmail.getScene().getWindow();
            loginStage.close();

        } catch (Exception e) {
            e.printStackTrace();
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
