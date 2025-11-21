package co.edu.uniquindio.poo.parcial3.Controller;

import co.edu.uniquindio.poo.parcial3.Model.Paciente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class PerfilPacientController implements Initializable {

    @FXML private TextField txtNombre;
    @FXML private TextField txtApellido;
    @FXML private TextField txtEmail;
    @FXML private PasswordField txtPassword;

    private Paciente pacienteActual;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("PerfilPacientController INICIALIZADO");
    }

    public void setPacienteActual(Paciente paciente) {
        this.pacienteActual = paciente;
        if (paciente != null) {
            cargarDatosPaciente();
        }
    }

    private void cargarDatosPaciente() {
        txtNombre.setText(pacienteActual.getNombre());
        txtApellido.setText(pacienteActual.getApellido());
        txtEmail.setText(pacienteActual.getEmail());
    }

    @FXML
    public void guardarPerfil(ActionEvent event) {
        try {
            pacienteActual.setNombre(txtNombre.getText());
            pacienteActual.setApellido(txtApellido.getText());
            pacienteActual.setEmail(txtEmail.getText());

            if (!txtPassword.getText().isEmpty()) {
                pacienteActual.setContraseña(txtPassword.getText());
            }

            mostrarAlerta("Éxito", "Perfil actualizado correctamente");
        } catch (Exception e) {
            mostrarAlerta("Error", "No se pudo actualizar el perfil: " + e.getMessage());
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