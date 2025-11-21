package co.edu.uniquindio.poo.parcial3.Controller;

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

    // Datos quemados del médico
    private final String EMAIL = "john@gmail.com";
    private final String PASSWORD = "abcd";
    private final String NOMBRE = "Dr. John Mago";

    @FXML
    private void iniciarSesion(ActionEvent event) {
        if(txtEmail.getText().equals(EMAIL) && txtPassword.getText().equals(PASSWORD)) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/poo/parcial3/MedicoView.fxml"));
                Parent root = loader.load();

                // Asignar nombre al lblUsuario del médico
                MedicoController controller = loader.getController();
                controller.lblUsuario.setText("Bienvenido, " + NOMBRE);

                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("Dashboard Médico");
                stage.setResizable(false);
                stage.show();

                // Cerrar login
                txtEmail.getScene().getWindow().hide();

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Correo o contraseña incorrectos");
            alert.showAndWait();
        }
    }
}
