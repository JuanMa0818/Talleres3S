package co.edu.uniquindio.poo.parcial3.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML
    private TextField txtEmail;

    @FXML
    private PasswordField txtPassword;

    // Datos quemados
    private final String EMAIL_USUARIO = "camila.gomez@email.com";
    private final String PASSWORD_USUARIO = "12345";

    @FXML
    private void iniciarSesion(ActionEvent event) {
        String email = txtEmail.getText();
        String password = txtPassword.getText();

        if(email.equals(EMAIL_USUARIO) && password.equals(PASSWORD_USUARIO)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Login exitoso");
            alert.setHeaderText(null);
            alert.setContentText("Bienvenida, Camila Gómez");
            alert.showAndWait();

            // Aquí puedes abrir la ventana del paciente o del dashboard
            // Por ejemplo: abrirPacienteView();

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de login");
            alert.setHeaderText(null);
            alert.setContentText("Correo o contraseña incorrectos");
            alert.showAndWait();
        }
    }

    // Ejemplo para abrir la vista del paciente (opcional)
    /*
    private void abrirPacienteView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/poo/parcial3/pacient/PacienteView.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Dashboard Paciente");
            stage.show();

            // Cerrar la ventana de login
            txtEmail.getScene().getWindow().hide();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    */
}
