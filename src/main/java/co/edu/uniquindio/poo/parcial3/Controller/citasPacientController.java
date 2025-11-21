package co.edu.uniquindio.poo.parcial3.Controller;

import co.edu.uniquindio.poo.parcial3.Model.Cita;
import co.edu.uniquindio.poo.parcial3.Model.Clinica;
import co.edu.uniquindio.poo.parcial3.Model.Paciente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class citasPacientController implements Initializable {

    @FXML private TableView<Cita> tablaCitas;
    @FXML private TableColumn<Cita, String> colFecha;
    @FXML private TableColumn<Cita, String> colHora;
    @FXML private TableColumn<Cita, String> colMedico;
    @FXML private TableColumn<Cita, String> colEspecialidad;
    @FXML private TableColumn<Cita, String> colMotivo;
    @FXML private TableColumn<Cita, String> colEstado;
    @FXML private TableColumn<Cita, Double> colCosto;

    private Paciente pacienteActual;
    private Clinica clinica;
    private ObservableList<Cita> listaCitas;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("citasPacientController INICIALIZADO");

        clinica = Clinica.getInstancia("Hospital Central", "900123456");
        listaCitas = FXCollections.observableArrayList();

        configurarColumnas();
        tablaCitas.setItems(listaCitas);
    }

    private void configurarColumnas() {
        // Configurar columnas básicas
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        colHora.setCellValueFactory(new PropertyValueFactory<>("hora"));
        colCosto.setCellValueFactory(new PropertyValueFactory<>("precio"));

        // CORREGIDO: Columna Médico
        colMedico.setCellValueFactory(cellData -> {
            Cita cita = cellData.getValue();
            if (cita.getMedico() != null) {
                return new javafx.beans.property.SimpleStringProperty(
                        cita.getMedico().getNombre() + " " + cita.getMedico());
            }
            return new javafx.beans.property.SimpleStringProperty("Sin asignar");
        });

        // CORREGIDO: Columna Especialidad - usar la especialidad REAL del médico
        colEspecialidad.setCellValueFactory(cellData -> {
            Cita cita = cellData.getValue();
            if (cita.getMedico() != null) {
                return new javafx.beans.property.SimpleStringProperty(cita.getMedico().getEspecialidad());
            }
            return new javafx.beans.property.SimpleStringProperty("No especificada");
        });

        // Motivo
        colMotivo.setCellValueFactory(cellData -> {
            return new javafx.beans.property.SimpleStringProperty("Consulta médica");
        });

        // Estado
        colEstado.setCellValueFactory(cellData -> {
            return new javafx.beans.property.SimpleStringProperty("Pendiente");
        });

        System.out.println(" Columnas configuradas correctamente");
    }

    public void setPacienteActual(Paciente paciente) {
        this.pacienteActual = paciente;
        if (paciente != null) {
            System.out.println("Paciente asignado a Mis Citas: " + paciente.getNombre());
            cargarCitasPaciente();
        }
    }

    private void cargarCitasPaciente() {
        try {
            if (pacienteActual == null) {
                System.out.println(" No hay paciente actual");
                return;
            }

            List<Cita> citas = clinica.getCitasPorPaciente(pacienteActual.getIdentificacion());
            listaCitas.setAll(citas);

            System.out.println(" Cargadas " + citas.size() + " citas para " + pacienteActual.getNombre());

            //  DIAGNÓSTICO MEJORADO
            for (Cita cita : citas) {
                System.out.println("   Cita: " + cita.getFecha() + " | " +
                        cita.getHora() + " | " +
                        "Tipo cita: " + cita.getTipo() + " | " +
                        "Médico: " + (cita.getMedico() != null ?
                        cita.getMedico().getNombre() + " (" + cita.getMedico().getEspecialidad() + ")" : "NULL") + " | $" +
                        cita.getPrecio());
            }

        } catch (Exception e) {
            System.err.println(" Error al cargar citas: " + e.getMessage());
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudieron cargar las citas", e.getMessage());
        }
    }

    @FXML
    public void solicitarNuevaCita(ActionEvent event) {
        mostrarAlerta("Información", "Solicitar Nueva Cita",
                "Use el menú lateral para ir a 'Solicitar Cita' y agendar una nueva cita médica.");
    }

    @FXML
    public void actualizarCitas(ActionEvent event) {
        System.out.println("Usuario solicitó actualizar citas");
        cargarCitasPaciente();
        mostrarAlerta("Información", "Lista Actualizada",
                "Se han actualizado las citas del paciente: " + pacienteActual.getNombre());
    }

    private void mostrarAlerta(String titulo, String header, String contenido) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(header);
        alert.setContentText(contenido);
        alert.showAndWait();
    }
}