package co.edu.uniquindio.poo.parcial3.Controller;

import co.edu.uniquindio.poo.parcial3.Model.Paciente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class PacienteController {

    @FXML
    Label lblUsuario;

    @FXML
    private AnchorPane contenidoPrincipal;

    @FXML
    public void cargarMiPerfil(ActionEvent actionEvent) {
        cargarFXMLEnAnchorPane("/co/edu/uniquindio/poo/parcial3/pacient/perfilPacient.fxml");
    }

    @FXML
    public void cargarSolicitarCita(ActionEvent actionEvent) {
        cargarFXMLEnAnchorPane("/co/edu/uniquindio/poo/parcial3/pacient/CitaView.fxml");
    }

    @FXML
    public void cargarMisCitas(ActionEvent actionEvent) {
        cargarFXMLEnAnchorPane("/co/edu/uniquindio/poo/parcial3/pacient/mis-citas.fxml");
    }

    @FXML
    public void cerrarSesion(ActionEvent actionEvent) {
        irPantalla("/co/edu/uniquindio/poo/parcial3/MainView.fxml", "Home");
    }

    /**
     * Carga un FXML dentro del AnchorPane central
     */
    private void cargarFXMLEnAnchorPane(String nombreArchivoFxml) {
        try {
            // Limpiar el contenedor
            contenidoPrincipal.getChildren().clear();

            // Cargar la vista
            FXMLLoader loader = new FXMLLoader(getClass().getResource(nombreArchivoFxml));
            Parent contenido = loader.load();

            // Ajustar para que llene todo el AnchorPane
            AnchorPane.setTopAnchor(contenido, 0.0);
            AnchorPane.setBottomAnchor(contenido, 0.0);
            AnchorPane.setLeftAnchor(contenido, 0.0);
            AnchorPane.setRightAnchor(contenido, 0.0);

            contenidoPrincipal.getChildren().add(contenido);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Abre un nuevo Stage con un FXML
     */
    private void irPantalla(String fxml, String titulo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle(titulo);
            stage.setResizable(false);
            stage.show();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setPacienteActual(Paciente paciente) {
    }
}
