package co.edu.uniquindio.poo.parcial3.Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class MiPerfilController implements Initializable {

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtApellido;

    @FXML
    private TextField txtCedula;

    @FXML
    private TextField txtTelefono;

    @FXML
    private TextField txtDireccion;

    @FXML
    private Button btnActualizar;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private void actualizarPerfil() {
        String nombre = txtNombre.getText();
        String apellido = txtApellido.getText();
        String cedula = txtCedula.getText();
        String telefono = txtTelefono.getText();
        String direccion = txtDireccion.getText();


        System.out.println("Perfil actualizado:");
        System.out.println(nombre + " - " + apellido + " - " + cedula + " - " + telefono + " - " + direccion);
    }
}
