package co.edu.uniquindio.poo.parcial3.Controller;

import co.edu.uniquindio.poo.parcial3.Model.Paciente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PacienteController implements Initializable {

    @FXML private Label lblUsuario;
    @FXML private AnchorPane contenidoPrincipal;

    private Paciente pacienteActual;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("PacienteController INICIALIZADO");
    }

    public void setPacienteActual(Paciente paciente) {
        System.out.println("setPacienteActual llamado con: " +
                (paciente != null ? paciente.getNombre() : "null"));
        this.pacienteActual = paciente;

        if (lblUsuario != null && paciente != null) {
            lblUsuario.setText("Bienvenido, " + paciente.getNombre() + " " + paciente.getApellido());
        }
    }

    public Paciente getPacienteActual() {
        return pacienteActual;
    }



    @FXML
    private void cargarMiPerfil(ActionEvent event) {
        cargarVista("/co/edu/uniquindio/poo/parcial3/pacient/perfilPacient.fxml", "Mi Perfil");
    }

    @FXML
    private void cargarSolicitarCita(ActionEvent event) {
        cargarVista("/co/edu/uniquindio/poo/parcial3/pacient/CitaView.fxml", "Solicitar Cita");
    }

    @FXML
    private void cargarMisCitas(ActionEvent event) {
        cargarVista("/co/edu/uniquindio/poo/parcial3/pacient/mis-citas.fxml", "Mis Citas");
    }

    @FXML
    private void cerrarSesion(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cerrar Sesión");
        alert.setHeaderText("¿Desea cerrar sesión?");
        alert.setContentText("Usuario: " + (pacienteActual != null ? pacienteActual.getNombre() : ""));

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    FXMLLoader loader = new FXMLLoader(
                            getClass().getResource("/co/edu/uniquindio/poo/parcial3/Login-view.fxml")
                    );
                    Parent root = loader.load();

                    Stage stage = (Stage) lblUsuario.getScene().getWindow();
                    stage.getScene().setRoot(root);
                    stage.setTitle("Inicio de Sesión");

                } catch (IOException e) {
                    System.err.println("Error cargando Login: " + e.getMessage());
                    e.printStackTrace();
                    Stage stage = (Stage) lblUsuario.getScene().getWindow();
                    stage.close();
                }
            }
        });
    }



    private void cargarVista(String rutaFXML, String nombreVista) {
        try {
            System.out.println(" Intentando cargar: " + rutaFXML);

            URL recurso = getClass().getResource(rutaFXML);
            if (recurso == null) {
                System.err.println("No se encontró el archivo: " + rutaFXML);
                mostrarAlerta("Error", "No se encontró la vista: " + nombreVista);
                return;
            }

            FXMLLoader loader = new FXMLLoader(recurso);
            Parent vista = loader.load(); // Cargar como Parent (genérico)


            Object controller = loader.getController();
            if (controller != null) {
                try {

                    controller.getClass()
                            .getMethod("setPacienteActual", Paciente.class)
                            .invoke(controller, pacienteActual);
                    System.out.println("Paciente pasado al controlador de " + nombreVista);
                } catch (NoSuchMethodException e) {
                    System.out.println(" El controlador de " + nombreVista + " no tiene setPacienteActual");
                } catch (Exception e) {
                    System.err.println("Error al pasar paciente: " + e.getMessage());
                }
            }


            contenidoPrincipal.getChildren().clear();
            contenidoPrincipal.getChildren().add(vista);

            // Ajustar el tamaño de la vista al contenedor
            AnchorPane.setTopAnchor(vista, 0.0);
            AnchorPane.setBottomAnchor(vista, 0.0);
            AnchorPane.setLeftAnchor(vista, 0.0);
            AnchorPane.setRightAnchor(vista, 0.0);

            System.out.println("Vista cargada exitosamente: " + nombreVista);

        } catch (IOException e) {
            System.err.println(" Error al cargar " + nombreVista + ": " + e.getMessage());
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo cargar la vista: " + nombreVista + "\n\n" + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error inesperado: " + e.getMessage());
            e.printStackTrace();
            mostrarAlerta("Error", "Error inesperado al cargar " + nombreVista);
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