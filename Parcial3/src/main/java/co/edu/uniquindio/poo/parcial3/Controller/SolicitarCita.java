package co.edu.uniquindio.poo.parcial3.Controller;

import co.edu.uniquindio.poo.parcial3.Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class SolicitarCita implements Initializable {

    @FXML public Label lblNombrePaciente;
    @FXML public Label lblSeguroPaciente;
    @FXML public Label lblTipoSangre;
    @FXML public ComboBox<Medico> cmbMedico;
    @FXML public ComboBox<String> cmbEspecialidad;
    @FXML public ComboBox<String> cmbHora;
    @FXML public TextArea taMotivo;
    @FXML public Label lblMedicoSeleccionado;
    @FXML public Label lblEspecialidadSeleccionada;
    @FXML public Label lblCostoConsulta;
    @FXML public DatePicker dpFecha;

    private Paciente pacienteActual;
    private Clinica clinica;
    private ObservableList<Medico> listaMedicos;
    private ObservableList<String> listaEspecialidades;
    private ObservableList<String> listaHoras;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println(" SolicitarCitaController INICIALIZADO");

        clinica = Clinica.getInstancia("Hospital Central", "900123456");
        configurarComponentes();
        cargarDatosIniciales();
    }

    public void setPacienteActual(Paciente paciente) {
        System.out.println("setPacienteActual llamado con: " + (paciente != null ? paciente.getNombre() : "null"));
        this.pacienteActual = paciente;
        actualizarInformacionPaciente();
    }

    private void actualizarInformacionPaciente() {
        if (pacienteActual != null) {
            lblNombrePaciente.setText(pacienteActual.getNombre() + " " + pacienteActual.getApellido());
            lblSeguroPaciente.setText("ID: " + pacienteActual.getIdentificacion());
            lblTipoSangre.setText("Tel: " + pacienteActual.getTelefono() + " | Email: " + pacienteActual.getEmail());
        } else {
            lblNombrePaciente.setText("[Nombre]");
            lblSeguroPaciente.setText("[ID]");
            lblTipoSangre.setText("[Email/Teléfono]");
        }
    }

    private void configurarComponentes() {
        // Configurar DatePicker
        dpFecha.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.isBefore(LocalDate.now()));
            }
        });


        listaEspecialidades = FXCollections.observableArrayList();
        cmbEspecialidad.setItems(listaEspecialidades);

        listaMedicos = FXCollections.observableArrayList();
        cmbMedico.setItems(listaMedicos);

        listaHoras = FXCollections.observableArrayList();
        cmbHora.setItems(listaHoras);

        configurarListeners();
    }

    private void configurarListeners() {
        cmbEspecialidad.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                cargarMedicosPorEspecialidad(new ActionEvent());
                actualizarInformacionEspecialidad();
            }
        });

        cmbMedico.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                actualizarInformacionMedico();
                cargarHorasDisponibles();
            }
        });

        dpFecha.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                cargarHorasDisponibles();
            }
        });
    }

    private void cargarDatosIniciales() {
        cargarEspecialidades();
        cargarHorasDisponibles();
    }

    @FXML
    public void cargarMedicosPorEspecialidad(ActionEvent actionEvent) {
        try {
            String especialidadSeleccionada = cmbEspecialidad.getValue();
            if (especialidadSeleccionada != null) {
                listaMedicos.clear();

                List<Medico> medicos = clinica.getMedicosPorEspecialidad(especialidadSeleccionada);
                listaMedicos.addAll(medicos);

                if (!listaMedicos.isEmpty()) {
                    cmbMedico.setValue(listaMedicos.get(0));
                } else {
                    lblMedicoSeleccionado.setText("No hay médicos disponibles");
                    lblCostoConsulta.setText("$0");
                }
            }
        } catch (Exception e) {
            mostrarAlerta("Error", "Error al cargar médicos", e.getMessage());
        }
    }

    private void cargarEspecialidades() {
        try {
            listaEspecialidades.clear();


            listaEspecialidades.add("Medicina General");
            listaEspecialidades.add("Odontología");

            if (!listaEspecialidades.isEmpty()) {
                cmbEspecialidad.setValue(listaEspecialidades.get(0));
            }
        } catch (Exception e) {
            mostrarAlerta("Error", "Error al cargar especialidades", e.getMessage());
        }
    }

    private void cargarHorasDisponibles() {
        try {
            listaHoras.clear();

            if (dpFecha.getValue() != null && cmbMedico.getValue() != null) {

                listaHoras.addAll(
                        "08:00 AM", "09:00 AM", "10:00 AM", "11:00 AM",
                        "02:00 PM", "03:00 PM", "04:00 PM", "05:00 PM"
                );
            } else {
                listaHoras.add("Seleccione fecha y médico");
            }

            if (!listaHoras.isEmpty()) {
                cmbHora.setValue(listaHoras.get(0));
            }
        } catch (Exception e) {
            mostrarAlerta("Error", "Error al cargar horas", e.getMessage());
        }
    }

    private void actualizarInformacionEspecialidad() {
        String especialidad = cmbEspecialidad.getValue();
        if (especialidad != null) {
            lblEspecialidadSeleccionada.setText(especialidad);
        }
    }

    private void actualizarInformacionMedico() {
        Medico medicoSeleccionado = cmbMedico.getValue();
        if (medicoSeleccionado != null) {
            lblMedicoSeleccionado.setText(medicoSeleccionado.getNombre());
            lblCostoConsulta.setText("$" + medicoSeleccionado.getPrecio());
        }
    }

    @FXML
    public void solicitarCita(ActionEvent actionEvent) {
        try {
            if (!validarFormulario()) {
                return;
            }

            Medico medicoSeleccionado = cmbMedico.getValue();
            String fecha = dpFecha.getValue().toString();
            String hora = cmbHora.getValue();
            String motivo = taMotivo.getText();

            boolean citaSolicitada = clinica.solicitarCita(pacienteActual, medicoSeleccionado, fecha, hora, motivo);

            if (citaSolicitada) {
                mostrarAlerta("Éxito", "Cita Solicitada",
                        "Su cita ha sido solicitada exitosamente.\n\n" +
                                "Paciente: " + pacienteActual.getNombre() + "\n" +
                                "Médico: " + medicoSeleccionado.getNombre() + "\n" +
                                "Fecha: " + fecha + "\n" +
                                "Hora: " + hora + "\n" +
                                "Costo: $" + medicoSeleccionado.getPrecio());

                limpiarFormularioSilencioso();
            } else {
                mostrarAlerta("Error", "Error al solicitar cita",
                        "No se pudo solicitar la cita. Intente nuevamente.");
            }

        } catch (Exception e) {
            mostrarAlerta("Error", "Error al solicitar cita", e.getMessage());
        }
    }

    @FXML
    public void verMisCitas(ActionEvent actionEvent) {
        mostrarAlerta("Información", "Mis Citas",
                "Use el menú lateral para ir a 'Mis Citas' y ver todas sus citas programadas.");
    }

    @FXML
    public void limpiarFormulario(ActionEvent actionEvent) {
        limpiarFormularioSilencioso();
        mostrarAlerta("Información", "Formulario Limpiado",
                "El formulario ha sido restablecido.");
    }


    private void limpiarFormularioSilencioso() {
        try {
            cmbMedico.getSelectionModel().clearSelection();
            cmbHora.getSelectionModel().clearSelection();
            taMotivo.clear();
            dpFecha.setValue(null);

            if (!listaEspecialidades.isEmpty()) {
                cmbEspecialidad.setValue(listaEspecialidades.get(0));
            }

            lblMedicoSeleccionado.setText("No seleccionado");
            lblEspecialidadSeleccionada.setText("No seleccionada");
            lblCostoConsulta.setText("$0");

        } catch (Exception e) {
            System.err.println("Error al limpiar formulario: " + e.getMessage());
        }
    }

    private boolean validarFormulario() {
        StringBuilder errores = new StringBuilder();

        if (pacienteActual == null) {
            errores.append("• No hay paciente seleccionado\n");
        }
        if (cmbEspecialidad.getValue() == null) {
            errores.append("• Seleccione una especialidad\n");
        }
        if (cmbMedico.getValue() == null) {
            errores.append("• Seleccione un médico\n");
        }
        if (dpFecha.getValue() == null) {
            errores.append("• Seleccione una fecha\n");
        }
        if (cmbHora.getValue() == null || cmbHora.getValue().equals("Seleccione fecha y médico")) {
            errores.append("• Seleccione una hora\n");
        }
        if (taMotivo.getText() == null || taMotivo.getText().trim().isEmpty()) {
            errores.append("• Ingrese el motivo de la consulta\n");
        }

        if (errores.length() > 0) {
            mostrarAlerta("Error de Validación", "Complete los siguientes campos:", errores.toString());
            return false;
        }

        return true;
    }

    private void mostrarAlerta(String titulo, String header, String contenido) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(header);
        alert.setContentText(contenido);
        alert.showAndWait();
    }
}