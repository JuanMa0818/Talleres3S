package co.edu.uniquindio.poo.parcial3.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainController {

    @FXML
    private void abrirLoginPaciente(ActionEvent event) {
        abrirLogin("/co/edu/uniquindio/poo/parcial3/pacient/LoginPacienteView.fxml", "Login Paciente");
    }

    @FXML
    private void abrirLoginMedico(ActionEvent event) {
        abrirLogin("/co/edu/uniquindio/poo/parcial3/LoginMedicoView.fxml", "Login Médico");
    }

    private void abrirLogin(String fxml, String titulo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle(titulo);
            stage.setResizable(false);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
