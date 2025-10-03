package co.edu.uniquindio.fx10.controlador;

import co.edu.uniquindio.fx10.modelo.Producto;
import co.edu.uniquindio.fx10.repositorio.ProductoRepository;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

/**
 * Controlador para el formulario de creación de productos
 */
public class FormularioProductoController {

    @FXML
    private TextField txtCodigo;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtDescripcion;

    @FXML
    private TextField txtPrecio;

    @FXML
    private TextField txtStock;

    @FXML
    private Button btnGuardar;

    @FXML
    private Button btnCancelar;

    private ProductoRepository productoRepository;
    private AnchorPane contenedorCentral;

    @FXML
    public void initialize() {
        productoRepository = ProductoRepository.getInstancia();
    }

    /**
     * Establece el contenedor central del Dashboard
     */
    public void setContenedorCentral(AnchorPane contenedor) {
        this.contenedorCentral = contenedor;
    }

    /**
     * Maneja el evento de guardar producto
     */
    @FXML
    private void onGuardarProducto() {
        if (!validarCampos()) {
            return;
        }

        try {
            String codigo = txtCodigo.getText().trim();
            String nombre = txtNombre.getText().trim();
            String descripcion = txtDescripcion.getText().trim();
            double precio = Double.parseDouble(txtPrecio.getText().trim());
            int stock = Integer.parseInt(txtStock.getText().trim());

            // Verificar si el código ya existe
            if (productoRepository.buscarPorCodigo(codigo) != null) {
                mostrarAlerta("Error", "Ya existe un producto con ese código", Alert.AlertType.ERROR);
                return;
            }

            // Crear y guardar el producto
            Producto nuevoProducto = new Producto(codigo, nombre, descripcion, precio, stock);
            productoRepository.agregarProducto(nuevoProducto);

            mostrarAlerta("Éxito", "Producto creado correctamente", Alert.AlertType.INFORMATION);

            // Volver a la lista de productos
            volverAListaProductos();

        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "El precio y stock deben ser valores numéricos válidos", Alert.AlertType.ERROR);
        }
    }

    /**
     * Maneja el evento de cancelar
     */
    @FXML
    private void onCancelar() {
        volverAListaProductos();
    }

    /**
     * Vuelve a mostrar la lista de productos
     */
    private void volverAListaProductos() {
        if (contenedorCentral == null) {
            mostrarAlerta("Error", "No se puede navegar", Alert.AlertType.ERROR);
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/fx10/vista/ListaProductos.fxml"));
            Node node = loader.load();

            // Obtener el controlador y pasarle el contenedor
            ListaProductosController controller = loader.getController();
            controller.setContenedorCentral(contenedorCentral);

            contenedorCentral.getChildren().setAll(node);

        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudo volver a la lista de productos: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    /**
     * Valida que los campos del formulario estén completos
     */
    private boolean validarCampos() {
        if (txtCodigo.getText().trim().isEmpty()) {
            mostrarAlerta("Error de validación", "El código es obligatorio", Alert.AlertType.WARNING);
            return false;
        }
        if (txtNombre.getText().trim().isEmpty()) {
            mostrarAlerta("Error de validación", "El nombre es obligatorio", Alert.AlertType.WARNING);
            return false;
        }
        if (txtDescripcion.getText().trim().isEmpty()) {
            mostrarAlerta("Error de validación", "La descripción es obligatoria", Alert.AlertType.WARNING);
            return false;
        }
        if (txtPrecio.getText().trim().isEmpty()) {
            mostrarAlerta("Error de validación", "El precio es obligatorio", Alert.AlertType.WARNING);
            return false;
        }
        if (txtStock.getText().trim().isEmpty()) {
            mostrarAlerta("Error de validación", "El stock es obligatorio", Alert.AlertType.WARNING);
            return false;
        }
        return true;
    }

    /**
     * Muestra una alerta al usuario
     */
    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}