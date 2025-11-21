package co.edu.uniquindio.poo.parcial3.Controller;

import co.edu.uniquindio.poo.parcial3.Model.*;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class SolicitarCita {
    public Label lblNombrePaciente;
    public Label lblSeguroPaciente;
    public Label lblTipoSangre;
    public ComboBox<String> cmbMedico;
    public ComboBox<String> cmbEspecialidad;
    public ComboBox<String> cmbHora;
    public TextArea taMotivo;
    public Label lblMedicoSeleccionado;
    public Label lblEspecialidadSeleccionada;
    public Label lblCostoConsulta;
    public DatePicker dpFecha;

    private Clinica clinica;
    private Paciente pacienteActual;
    private Medico medicoSeleccionado;

    // Método de inicialización
    public void initialize() {
        try {
            clinica = Clinica.getInstancia("Clínica UNIQUINDIO", "123456789");

            // Configurar combobox de especialidades con datos REALES de la clínica
            cargarEspecialidades();

            // Configurar combobox de horas con datos REALES de la clínica
            cargarHorasDisponibles();

            // Configurar DatePicker
            configurarDatePicker();

            // Cargar paciente actual
            cargarPacienteActual();

            // Configurar listeners
            configurarListeners();

            System.out.println("Controlador inicializado. Médicos en clínica: " + clinica.getMedicos().size());

        } catch (Exception e) {
            mostrarAlerta("Error", "Error al inicializar: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void cargarEspecialidades() {
        try {
            // Obtener especialidades REALES de la clínica
            List<String> especialidades = clinica.getEspecialidades();
            ObservableList<String> especialidadesList = FXCollections.observableArrayList(especialidades);
            cmbEspecialidad.setItems(especialidadesList);

            System.out.println("Especialidades cargadas: " + especialidades);

        } catch (Exception e) {
            mostrarAlerta("Error", "Error al cargar especialidades: " + e.getMessage());
            // Fallback a datos de ejemplo
            cmbEspecialidad.setItems(FXCollections.observableArrayList("General", "Odontología"));
        }
    }

    private void cargarHorasDisponibles() {
        try {
            // Obtener horas REALES de la clínica
            List<String> horas = clinica.getHorasDisponibles();
            ObservableList<String> horasList = FXCollections.observableArrayList(horas);
            cmbHora.setItems(horasList);

            System.out.println("Horas cargadas: " + horas);

        } catch (Exception e) {
            mostrarAlerta("Error", "Error al cargar horas: " + e.getMessage());
            // Fallback a datos de ejemplo
            cmbHora.setItems(FXCollections.observableArrayList(
                    "08:00 AM", "09:00 AM", "10:00 AM", "11:00 AM",
                    "02:00 PM", "03:00 PM", "04:00 PM"
            ));
        }
    }

    private void configurarDatePicker() {
        // Configurar para que solo permita fechas futuras
        dpFecha.setDayCellFactory(picker -> new javafx.scene.control.DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.isBefore(LocalDate.now()));
            }
        });
        dpFecha.setValue(LocalDate.now());
    }

    private void cargarPacienteActual() {
        try {
            // Buscar paciente REAL en la clínica
            pacienteActual = clinica.buscarPacientePorIdentificacion("1001");

            if (pacienteActual != null) {
                actualizarDatosPaciente();
                System.out.println("Paciente cargado: " + pacienteActual.getNombre());
            } else {
                // Si no encuentra, usar el primer paciente disponible
                if (!clinica.getPacientes().isEmpty()) {
                    pacienteActual = clinica.getPacientes().get(0);
                    actualizarDatosPaciente();
                    System.out.println("Paciente cargado (fallback): " + pacienteActual.getNombre());
                } else {
                    mostrarAlerta("Advertencia", "No se encontraron pacientes en el sistema");
                }
            }

        } catch (Exception e) {
            mostrarAlerta("Error", "Error al cargar paciente: " + e.getMessage());
        }
    }

    private void configurarListeners() {
        // Listener para la selección de médico
        cmbMedico.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                actualizarMedicoSeleccionado(newValue);
            }
        });
    }

    public void cargarMedicosPorEspecialidad(ActionEvent actionEvent) {
        try {
            String especialidad = cmbEspecialidad.getValue();
            if (especialidad != null && !especialidad.isEmpty()) {

                // Convertir nombre de especialidad a formato interno
                String especialidadInterna = convertirEspecialidad(especialidad);

                // Obtener médicos REALES por especialidad
                List<Medico> medicos = clinica.getMedicosPorEspecialidad(especialidadInterna);

                ObservableList<String> nombresMedicos = FXCollections.observableArrayList();

                for (Medico medico : medicos) {
                    String infoMedico = String.format("%s - %s - Tel: %s",
                            medico.getNombre(),
                            medico.getEspecialidad());
                    nombresMedicos.add(infoMedico);
                }

                cmbMedico.setItems(nombresMedicos);
                cmbMedico.getSelectionModel().clearSelection();

                lblEspecialidadSeleccionada.setText("Especialidad: " + especialidad);
                lblCostoConsulta.setText("$0");
                medicoSeleccionado = null;
                lblMedicoSeleccionado.setText("Médico: No seleccionado");

                System.out.println("Médicos cargados para " + especialidad + ": " + medicos.size());

                if (medicos.isEmpty()) {
                    mostrarAlerta("Información", "No hay médicos disponibles para la especialidad: " + especialidad);
                }
            }
        } catch (Exception e) {
            mostrarAlerta("Error", "Error al cargar médicos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void solicitarCita(ActionEvent actionEvent) {
        try {
            // Validaciones
            if (!validarFormulario()) {
                return;
            }

            // Obtener datos del formulario
            String fecha = dpFecha.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String hora = cmbHora.getValue();
            String motivo = taMotivo.getText().trim();

            // Solicitar cita REAL en el sistema
            boolean exito = clinica.solicitarCita(pacienteActual, medicoSeleccionado, fecha, hora, motivo);

            if (exito) {
                // Obtener la última cita agregada
                List<Cita> citas = clinica.getCitas();
                if (!citas.isEmpty()) {
                    Cita citaCreada = citas.get(citas.size() - 1);

                    // Mostrar resumen de la cita
                    mostrarResumenCita(citaCreada, fecha, hora);

                    // Generar resumen en consola
                    citaCreada.generarResumen();
                }

                // Limpiar formulario
                limpiarFormulario(null);

            } else {
                mostrarAlerta("Error", "No se pudo agendar la cita. Intente nuevamente.");
            }

        } catch (Exception e) {
            mostrarAlerta("Error", "Error al solicitar la cita: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void verMisCitas(ActionEvent actionEvent) {
        try {
            if (pacienteActual != null) {
                // Obtener citas REALES del paciente
                List<Cita> citas = clinica.getCitasPorPaciente(pacienteActual.getIdentificacion());

                StringBuilder mensaje = new StringBuilder();
                mensaje.append("=== MIS CITAS ===\n\n");
                mensaje.append("Paciente: ").append(pacienteActual.getNombre())
                        .append(" ").append(pacienteActual.getApellido()).append("\n\n");

                if (citas.isEmpty()) {
                    mensaje.append("No tiene citas programadas.\n");
                } else {
                    for (int i = 0; i < citas.size(); i++) {
                        Cita cita = citas.get(i);
                        mensaje.append("Cita #").append(i + 1).append(":\n")
                                .append("  Tipo: ").append(cita.getTipo()).append("\n")
                                .append("  Fecha: ").append(cita.getFecha()).append("\n")
                                .append("  Hora: ").append(cita.getHora()).append("\n")
                                .append("  Costo: $").append(String.format("%,.0f", cita.getPrecio())).append("\n\n");
                    }
                }

                mensaje.append("Total citas: ").append(citas.size());

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Mis Citas Médicas");
                alert.setHeaderText("Historial de Citas");
                alert.setContentText(mensaje.toString());
                alert.setWidth(400);
                alert.showAndWait();

            } else {
                mostrarAlerta("Error", "No se ha cargado la información del paciente");
            }

        } catch (Exception e) {
            mostrarAlerta("Error", "Error al cargar las citas: " + e.getMessage());
        }
    }

    public void limpiarFormulario(ActionEvent actionEvent) {
        cmbEspecialidad.getSelectionModel().clearSelection();
        cmbMedico.getSelectionModel().clearSelection();
        cmbMedico.setItems(FXCollections.observableArrayList());
        cmbHora.getSelectionModel().clearSelection();
        dpFecha.setValue(LocalDate.now());
        taMotivo.clear();
        lblMedicoSeleccionado.setText("Médico: No seleccionado");
        lblEspecialidadSeleccionada.setText("Especialidad: No seleccionada");
        lblCostoConsulta.setText("$0");
        medicoSeleccionado = null;
    }

    // ==================== MÉTODOS AUXILIARES ====================

    private void actualizarDatosPaciente() {
        if (pacienteActual != null) {
            lblNombrePaciente.setText(pacienteActual.getNombre() + " " + pacienteActual.getApellido());
            lblSeguroPaciente.setText(pacienteActual.getIdentificacion());
            lblTipoSangre.setText(pacienteActual.getEmail() + " | Tel: " + pacienteActual.getTelefono());
        }
    }

    private void actualizarMedicoSeleccionado(String medicoInfo) {
        try {
            // Extraer el nombre del médico del texto seleccionado
            String nombreMedico = medicoInfo.split(" - ")[0];

            // Buscar el médico REAL en la lista de la clínica
            for (Medico medico : clinica.getMedicos()) {
                if (medico.getNombre().equals(nombreMedico)) {
                    medicoSeleccionado = medico;
                    lblMedicoSeleccionado.setText(medico.getNombre());

                    // Mostrar costo REAL del médico
                    double costo = medico.getPrecio();
                    lblCostoConsulta.setText("$" + String.format("%,.0f", costo));
                    break;
                }
            }

            System.out.println("Médico seleccionado: " + (medicoSeleccionado != null ? medicoSeleccionado.getNombre() : "null"));

        } catch (Exception e) {
            mostrarAlerta("Error", "Error al cargar información del médico: " + e.getMessage());
        }
    }

    private boolean validarFormulario() {
        StringBuilder errores = new StringBuilder();

        if (cmbEspecialidad.getValue() == null || cmbEspecialidad.getValue().isEmpty()) {
            errores.append("• Debe seleccionar una especialidad\n");
        }

        if (medicoSeleccionado == null) {
            errores.append("• Debe seleccionar un médico\n");
        }

        if (dpFecha.getValue() == null) {
            errores.append("• Debe seleccionar una fecha\n");
        }

        if (cmbHora.getValue() == null || cmbHora.getValue().isEmpty()) {
            errores.append("• Debe seleccionar una hora\n");
        }

        if (taMotivo.getText() == null || taMotivo.getText().trim().isEmpty()) {
            errores.append("• Debe ingresar el motivo de la consulta\n");
        }

        if (errores.length() > 0) {
            mostrarAlerta("Error de Validación", "Por favor complete los siguientes campos:\n\n" + errores.toString());
            return false;
        }

        return true;
    }

    private void mostrarResumenCita(Cita cita, String fecha, String hora) {
        String mensaje = String.format(
                "✅ CITA AGENDADA EXITOSAMENTE\n\n" +
                        "📋 Resumen de la Cita:\n" +
                        "─────────────────────\n" +
                        "👤 Paciente: %s %s\n" +
                        "🆔 Identificación: %s\n" +
                        "👨‍⚕️ Médico: %s\n" +
                        "🎯 Especialidad: %s\n" +
                        "📅 Fecha: %s\n" +
                        "⏰ Hora: %s\n" +
                        "💰 Costo: $%,.0f\n" +
                        "📝 Motivo: %s\n\n" +
                        "📞 Contacto del médico: %s\n" +
                        "✉️ Email: %s",
                pacienteActual.getNombre(),
                pacienteActual.getApellido(),
                pacienteActual.getIdentificacion(),
                medicoSeleccionado.getNombre(),
                medicoSeleccionado.getEspecialidad(),
                fecha,
                hora,
                cita.getPrecio(),
                taMotivo.getText()
        );

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Cita Agendada");
        alert.setHeaderText("¡Cita médica programada con éxito!");
        alert.setContentText(mensaje);
        alert.setWidth(500);
        alert.showAndWait();
    }

    private String convertirEspecialidad(String especialidad) {
        switch (especialidad.toLowerCase()) {
            case "general":
                return "general";
            case "odontología":
                return "odontologo";
            default:
                return especialidad.toLowerCase();
        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    // Métodos públicos para configuración externa
    public void setPacienteActual(Paciente paciente) {
        this.pacienteActual = paciente;
        if (paciente != null) {
            actualizarDatosPaciente();
        }
    }

    public Paciente getPacienteActual() {
        return pacienteActual;
    }

    public Medico getMedicoSeleccionado() {
        return medicoSeleccionado;
    }
}