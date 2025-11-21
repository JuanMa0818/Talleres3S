package co.edu.uniquindio.poo.parcial3.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;

public class PerfilPacientController {

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtApellido;

    @FXML
    private TextField txtEmail;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private void guardarPerfil(ActionEvent event) {
        // Ejemplo de guardado: solo muestra un alerta
        String nombre = txtNombre.getText();
        String apellido = txtApellido.getText();
        String email = txtEmail.getText();
        String password = txtPassword.getText();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Perfil guardado");
        alert.setHeaderText(null);
        alert.setContentText("Paciente " + nombre + " " + apellido + " guardado correctamente!");
        alert.showAndWait();
    }
}
