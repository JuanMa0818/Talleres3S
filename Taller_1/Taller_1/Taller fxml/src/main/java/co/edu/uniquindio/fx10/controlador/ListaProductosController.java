package co.edu.uniquindio.fx10.controlador;

import co.edu.uniquindio.fx10.modelo.Producto;
import co.edu.uniquindio.fx10.repositorio.ProductoRepository;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class ListaProductosController {

    @FXML
    private TableView<Producto> tablaProductos;

    @FXML
    private TableColumn<Producto, String> colCodigo;

    @FXML
    private TableColumn<Producto, String> colNombre;

    @FXML
    private TableColumn<Producto, String> colDescripcion;

    @FXML
    private TableColumn<Producto, Double> colPrecio;

    @FXML
    private TableColumn<Producto, Integer> colStock;

    @FXML
    private Button btnCrearProducto;

    @FXML
    private Button btnEliminar;

    private AnchorPane contenedorCentral;

    @FXML
    public void initialize() {
        // Configurar columnas
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        colStock.setCellValueFactory(new PropertyValueFactory<>("stock"));

        // Vincular la tabla con la lista observable del repositorio
        tablaProductos.setItems(ProductoRepository.getInstancia().getProductos());
    }

    /**
     * Establece el contenedor central del Dashboard
     */
    public void setContenedorCentral(AnchorPane contenedor) {
        this.contenedorCentral = contenedor;
    }

    /**
     * ESTE ES EL MÉTODO QUE FALTABA - Abre el formulario de crear producto
     */
    @FXML
    private void onCrearProducto() {
        if (contenedorCentral == null) {
            mostrarAlerta("Error", "No se puede navegar", Alert.AlertType.ERROR);
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/fx10/vista/FormularioProducto.fxml"));
            Node node = loader.load();

            // Obtener el controlador y pasarle el contenedor
            FormularioProductoController controller = loader.getController();
            if (controller != null) {
                controller.setContenedorCentral(contenedorCentral);
            }

            contenedorCentral.getChildren().setAll(node);
        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudo cargar el formulario: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    @FXML
    private void onEliminarProducto() {
        Producto seleccionado = tablaProductos.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarAlerta("Información", "Seleccione un producto para eliminar", Alert.AlertType.INFORMATION);
            return;
        }
        boolean eliminado = ProductoRepository.getInstancia().eliminarProducto(seleccionado);
        if (!eliminado) {
            mostrarAlerta("Error", "No se pudo eliminar el producto", Alert.AlertType.ERROR);
        }
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}