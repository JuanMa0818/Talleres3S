package co.edu.uniquindio.poo.parcial3.Controller;

import co.edu.uniquindio.poo.parcial3.Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.text.BreakIterator;
import java.util.List;
import java.util.ResourceBundle;

public class MedicoController implements Initializable {

    public BreakIterator lblUsuario;
    @FXML
    private Label lblNombreDoctor;

    @FXML
    private Button btnCerrarSesion;

    @FXML
    private Label lblNombrePaciente;
    @FXML
    private Label lblEdadPaciente;
    @FXML
    private Label lblSexoPaciente;
    @FXML
    private Label lblAlergiasPaciente;
    @FXML
    private Label lblEnfermedadesPaciente;

    @FXML
    private TextArea txtSintomas;
    @FXML
    private TextArea txtDiagnostico;
    @FXML
    private TextArea txtTratamiento;
    @FXML
    private Button btnGuardarConsulta;

    @FXML
    private TableView<CitaWrapper> tablaCitas;
    @FXML
    private TableColumn<CitaWrapper, String> colHora;
    @FXML
    private TableColumn<CitaWrapper, String> colPaciente;
    @FXML
    private TableColumn<CitaWrapper, String> colMotivo;
    @FXML
    private TableColumn<CitaWrapper, String> colEstado;

    private ObservableList<CitaWrapper> listaCitas;
    private Clinica clinica;
    private Medico medicoActual;
    private Paciente pacienteSeleccionado;
    private CitaWrapper citaSeleccionada;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        clinica = Clinica.getInstancia("Hospital Central", "900123456");

        configurarTabla();
        configurarEventos();
    }

    private void configurarTabla() {
        colHora.setCellValueFactory(new PropertyValueFactory<>("hora"));
        colPaciente.setCellValueFactory(new PropertyValueFactory<>("nombrePaciente"));
        colMotivo.setCellValueFactory(new PropertyValueFactory<>("motivo"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));

        listaCitas = FXCollections.observableArrayList();
        tablaCitas.setItems(listaCitas);

        // Listener para selección de cita
        tablaCitas.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> {
                    if (newSelection != null) {
                        citaSeleccionada = newSelection;
                        cargarDatosPaciente(newSelection.getPaciente());
                    }
                }
        );
    }

    private void configurarEventos() {
        btnGuardarConsulta.setOnAction(event -> guardarConsulta());
        btnCerrarSesion.setOnAction(event -> cerrarSesion());
    }

    public void inicializarMedico(Medico medico) {
        this.medicoActual = medico;
        lblNombreDoctor.setText(medico.getNombre());
        cargarCitasDelMedico();
    }

    private void cargarCitasDelMedico() {
        listaCitas.clear();

        List<Cita> todasLasCitas = clinica.getTodasLasCitas();

        for (Cita cita : todasLasCitas) {

            // Solo cargar las que pertenecen al médico actual
            if (!cita.getMedico().equals(medicoActual)) {
                continue;
            }

            Paciente paciente = cita.getPaciente(); // AQUÍ ESTÁ LA SOLUCIÓN

            CitaWrapper wrapper = new CitaWrapper(
                    cita,
                    paciente,
                    "Control médico",
                    "Pendiente"
            );

            listaCitas.add(wrapper);
        }
    }

    private void cargarDatosPaciente(Paciente paciente) {
        if (paciente != null) {
            pacienteSeleccionado = paciente;

            lblNombrePaciente.setText("Nombre: " + paciente.getNombre() + " " + paciente.getApellido());
            lblEdadPaciente.setText("Edad: --");
            lblSexoPaciente.setText("Sexo: --");
            lblAlergiasPaciente.setText("Alergias: " +
                    (paciente.getAlergias() != null ? paciente.getAlergias() : "Ninguna"));
            lblEnfermedadesPaciente.setText("Enfermedades Crónicas: --"); // No está en tu modelo

            // Limpiar campos de consulta
            txtSintomas.clear();
            txtDiagnostico.clear();
            txtTratamiento.clear();
        }
    }

    private void guardarConsulta() {
        // Validar que haya un paciente seleccionado
        if (pacienteSeleccionado == null || citaSeleccionada == null) {
            mostrarAlerta("Error", "Debe seleccionar una cita de la agenda", Alert.AlertType.WARNING);
            return;
        }

        // Validar campos
        String sintomas = txtSintomas.getText().trim();
        String diagnostico = txtDiagnostico.getText().trim();
        String tratamiento = txtTratamiento.getText().trim();

        if (sintomas.isEmpty() || diagnostico.isEmpty() || tratamiento.isEmpty()) {
            mostrarAlerta("Campos Vacíos",
                    "Debe completar todos los campos de la consulta",
                    Alert.AlertType.WARNING);
            return;
        }

        try {
            // Crear la consulta
            Consulta consulta = new Consulta(
                    citaSeleccionada.getCita(),
                    pacienteSeleccionado,
                    medicoActual,
                    sintomas,
                    diagnostico,
                    tratamiento
            );

            // Actualizar estado de la cita
            citaSeleccionada.setEstado("Atendida");
            tablaCitas.refresh();

            // Mostrar confirmación
            mostrarAlerta("Éxito",
                    "Consulta guardada correctamente para el paciente: " +
                            pacienteSeleccionado.getNombre(),
                    Alert.AlertType.INFORMATION);

            // Limpiar formulario
            limpiarFormularioConsulta();

            // Log para debugging
            System.out.println("Consulta guardada: " + consulta);

        } catch (Exception e) {
            mostrarAlerta("Error",
                    "Error al guardar la consulta: " + e.getMessage(),
                    Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    private void limpiarFormularioConsulta() {
        txtSintomas.clear();
        txtDiagnostico.clear();
        txtTratamiento.clear();
        pacienteSeleccionado = null;
        tablaCitas.getSelectionModel().clearSelection();
    }

    private void cerrarSesion() {
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Cerrar Sesión");
        confirmacion.setHeaderText("¿Está seguro que desea cerrar sesión?");
        confirmacion.setContentText("Se cerrará la sesión de " + medicoActual.getNombre());

        if (confirmacion.showAndWait().get() == ButtonType.OK) {
            Stage stage = (Stage) btnCerrarSesion.getScene().getWindow();
            stage.close();
            // Aquí deberías abrir la ventana de login nuevamente
        }
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    public void setMedicoActual(Medico medico) {
        this.medicoActual = medico;
    }

    public static class CitaWrapper {
        private Cita cita;
        private Paciente paciente;
        private String motivo;
        private String estado;

        public CitaWrapper(Cita cita, Paciente paciente, String motivo, String estado) {
            this.cita = cita;
            this.paciente = paciente;
            this.motivo = motivo;
            this.estado = estado;
        }

        // Getters para la tabla
        public String getHora() {
            return cita.getHora();
        }

        public String getNombrePaciente() {
            return paciente.getNombre() + " " + paciente.getApellido();
        }

        public String getMotivo() {
            return motivo;
        }

        public String getEstado() {
            return estado;
        }

        public Cita getCita() {
            return cita;
        }

        public Paciente getPaciente() {
            return paciente;
        }

        public void setEstado(String estado) {
            this.estado = estado;
        }
    }
}