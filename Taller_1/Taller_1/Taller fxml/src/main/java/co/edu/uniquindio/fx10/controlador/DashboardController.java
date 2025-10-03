package co.edu.uniquindio.fx10.controlador;

import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class DashboardController {

    @FXML
    private AnchorPane contenedorCentral;

    @FXML
    private Button btnListado;

    @FXML
    private Button btnFormulario;

    @FXML
    private Button btnVolverDashboardTop;

    @FXML
    public void initialize() {
        // Actualizar la barra superior según el contenido del centro
        contenedorCentral.getChildren().addListener((ListChangeListener<Node>) change -> updateTopBar());
        updateTopBar();
    }

    @FXML
    private void abrirListadoProducto() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/fx10/vista/ListaProductos.fxml"));
            Node node = loader.load();

            // Obtener el controlador y pasarle el contenedor
            ListaProductosController controller = loader.getController();
            controller.setContenedorCentral(contenedorCentral);

            contenedorCentral.getChildren().setAll(node);
            updateTopBar();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void abrirFormularioProducto() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/fx10/vista/FormularioProducto.fxml"));
            Node node = loader.load();

            // Obtener el controlador y pasarle el contenedor
            FormularioProductoController controller = loader.getController();
            controller.setContenedorCentral(contenedorCentral);

            contenedorCentral.getChildren().setAll(node);
            updateTopBar();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void volverAlDashboard() {
        contenedorCentral.getChildren().clear();
        updateTopBar();
    }

    private void updateTopBar() {
        boolean hayVistaActiva = !contenedorCentral.getChildren().isEmpty();
        btnListado.setVisible(!hayVistaActiva);
        btnListado.setManaged(!hayVistaActiva);

        btnFormulario.setVisible(!hayVistaActiva);
        btnFormulario.setManaged(!hayVistaActiva);

        btnVolverDashboardTop.setVisible(hayVistaActiva);
        btnVolverDashboardTop.setManaged(hayVistaActiva);
    }
}