package co.edu.uniquindio.poo.parcial3.Controller;

import co.edu.uniquindio.poo.parcial3.Model.Clinica;
import co.edu.uniquindio.poo.parcial3.Model.Cita;
import co.edu.uniquindio.poo.parcial3.Model.Paciente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import java.util.List;

public class citasPacientController {

    @FXML public ComboBox<String> cmbFiltroEstado;
    @FXML public TextField txtBuscarMedico;
    @FXML public Label lblTotalCitas;
    @FXML public Label lblCitasConfirmadas;
    @FXML public Label lblCitasPendientes;
    @FXML public TableView<Cita> tablaCitas;

    private Clinica clinica;
    private Paciente pacienteActual;
    private ObservableList<Cita> listaCitas;
    private FilteredList<Cita> citasFiltradas;

    @FXML
    public void initialize() {
        try {
            clinica = Clinica.getInstancia("NextCare Medical Hub", "123456789-0");
            configurarComponentes();
            cargarDatosIniciales();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void configurarComponentes() {
        // Configurar ComboBox de estados
        cmbFiltroEstado.setItems(FXCollections.observableArrayList(
                "Todas", "Confirmadas", "Pendientes", "Canceladas", "Completas"
        ));
        cmbFiltroEstado.setValue("Todas");

        // Configurar TableView
        configurarTablaCitas();

        // Configurar filtros
        configurarFiltros();
    }

    private void configurarTablaCitas() {
        listaCitas = FXCollections.observableArrayList();
        tablaCitas.setItems(listaCitas);

        tablaCitas.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> seleccionarCita(newValue)
        );
    }

    private void configurarFiltros() {
        citasFiltradas = new FilteredList<>(listaCitas, p -> true);
        txtBuscarMedico.textProperty().addListener((observable, oldValue, newValue) -> {
            aplicarFiltros();
        });
        cmbFiltroEstado.valueProperty().addListener((observable, oldValue, newValue) -> {
            aplicarFiltros();
        });

        SortedList<Cita> sortedData = new SortedList<>(citasFiltradas);
        sortedData.comparatorProperty().bind(tablaCitas.comparatorProperty());
        tablaCitas.setItems(sortedData);
    }

    public void setPacienteActual(Paciente paciente) {
        this.pacienteActual = paciente;
        if (paciente != null) {
            cargarCitasPaciente();
            actualizarEstadisticas();
        } else {
            mostrarAlerta("Advertencia", "Paciente no encontrado", "No se ha establecido un paciente válido.");
        }
    }

    private void cargarDatosIniciales() {
        if (pacienteActual == null) {
            pacienteActual = clinica.buscarPacientePorIdentificacion("1001");
            if (pacienteActual == null && !clinica.getPacientes().isEmpty()) {
                pacienteActual = clinica.getPacientes().get(0);
            }
        }

        if (pacienteActual != null) {
            cargarCitasPaciente();
        } else {
            mostrarAlerta("Información", "Sin datos", "No hay pacientes disponibles para mostrar citas.");
        }
    }

    private void cargarCitasPaciente() {
        try {
            listaCitas.clear();

            if (pacienteActual != null) {
                // CARGAR CITAS REALES DEL PACIENTE DESDE LA CLÍNICA
                List<Cita> citasPaciente = clinica.getCitasPorPaciente(pacienteActual.getIdentificacion());

                if (citasPaciente != null && !citasPaciente.isEmpty()) {
                    listaCitas.addAll(citasPaciente);
                    System.out.println("Citas cargadas: " + citasPaciente.size());
                } else {
                    mostrarAlerta("Información", "Sin citas",
                            "No se encontraron citas para el paciente " + pacienteActual.getNombre() +
                                    ". Se mostrarán datos de ejemplo.");
                    cargarCitasDeEjemplo();
                }
            } else {
                cargarCitasDeEjemplo();
            }

            actualizarEstadisticas();

        } catch (Exception e) {
            mostrarAlerta("Error", "Error al cargar citas",
                    "No se pudieron cargar las citas del paciente: " + e.getMessage());
            e.printStackTrace();
            cargarCitasDeEjemplo(); // Fallback a datos de ejemplo
        }
    }

    private void cargarCitasDeEjemplo() {
        try {
            listaCitas.clear();

            List<Cita> todasLasCitas = clinica.getTodasLasCitas();
            if (todasLasCitas != null && !todasLasCitas.isEmpty()) {
                listaCitas.addAll(todasLasCitas);
                System.out.println("Citas de ejemplo cargadas desde clínica: " + todasLasCitas.size());
            } else {
                // Si no hay citas en la clínica, crear algunas de ejemplo
                System.out.println("No hay citas en la clínica, creando datos de ejemplo...");
                listaCitas.addAll(
                        crearCitaEjemplo("2024-11-25", "09:00 AM", "Dr. Carlos López", "General", "Confirmada", 50000),
                        crearCitaEjemplo("2024-11-26", "10:30 AM", "Dr. Pedro Martínez", "Odontología", "Pendiente", 80000),
                        crearCitaEjemplo("2024-11-27", "02:00 PM", "Dra. Ana García", "General", "Confirmada", 50000),
                        crearCitaEjemplo("2024-11-28", "03:00 PM", "Dra. Laura Rodríguez", "Odontología", "Cancelada", 80000)
                );
            }

            actualizarEstadisticas();

        } catch (Exception e) {
            mostrarAlerta("Error Crítico", "Error al cargar datos",
                    "No se pudieron cargar las citas: " + e.getMessage());
        }
    }

    private Cita crearCitaEjemplo(String fecha, String hora, String medico, String tipo, String estado, double precio) {
        return co.edu.uniquindio.poo.parcial3.Model.FactoryCita.crearCita(
                tipo.toLowerCase().contains("odont") ? "odontologica" : "general",
                fecha, hora, precio
        );
    }

    private void aplicarFiltros() {
        try {
            citasFiltradas.setPredicate(cita -> {
                if (cita == null) return false;

                // Filtro por estado
                String filtroEstado = cmbFiltroEstado.getValue();
                if (filtroEstado != null && !filtroEstado.equals("Todas")) {
                    String estadoCita = determinarEstadoCita(cita);
                    if (!estadoCita.equalsIgnoreCase(filtroEstado)) {
                        return false;
                    }
                }

                // Filtro por texto de búsqueda
                String textoBusqueda = txtBuscarMedico.getText();
                if (textoBusqueda != null && !textoBusqueda.isEmpty()) {
                    String lowerCaseFilter = textoBusqueda.toLowerCase();

                    // Buscar en el tipo de cita (que contiene información del médico)
                    if (cita.getTipo() != null && cita.getTipo().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }

                    // Buscar en la fecha
                    if (cita.getFecha() != null && cita.getFecha().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }

                    // Buscar en la hora
                    if (cita.getHora() != null && cita.getHora().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }

                    return false;
                }

                return true;
            });

            actualizarEstadisticas();

        } catch (Exception e) {
            mostrarAlerta("Error", "Error al aplicar filtros", e.getMessage());
        }
    }

    private String determinarEstadoCita(Cita cita) {
        try {
            // Esta es una implementación temporal
            // En una implementación real, la cita debería tener un campo 'estado'

            // Usar el índice en la lista para determinar estado (solo para ejemplo)
            int index = listaCitas.indexOf(cita);
            if (index >= 0 && index < listaCitas.size()) {
                String[] estados = {"Confirmada", "Pendiente", "Confirmada", "Cancelada"};
                return index < estados.length ? estados[index] : "Pendiente";
            }

            return "Pendiente";
        } catch (Exception e) {
            return "Pendiente";
        }
    }

    private void actualizarEstadisticas() {
        try {
            int total = citasFiltradas.size();
            int confirmadas = (int) citasFiltradas.stream()
                    .filter(c -> c != null && determinarEstadoCita(c).equals("Confirmada"))
                    .count();
            int pendientes = (int) citasFiltradas.stream()
                    .filter(c -> c != null && determinarEstadoCita(c).equals("Pendiente"))
                    .count();

            lblTotalCitas.setText(String.valueOf(total));
            lblCitasConfirmadas.setText(String.valueOf(confirmadas));
            lblCitasPendientes.setText(String.valueOf(pendientes));

        } catch (Exception e) {
            System.err.println("Error al actualizar estadísticas: " + e.getMessage());
            lblTotalCitas.setText("0");
            lblCitasConfirmadas.setText("0");
            lblCitasPendientes.setText("0");
        }
    }

    @FXML
    public void solicitarNuevaCita(ActionEvent actionEvent) {
        try {
            if (pacienteActual == null) {
                mostrarAlerta("Error", "Paciente no disponible",
                        "No se ha identificado un paciente. Por favor, inicie sesión nuevamente.");
                return;
            }

            abrirVentanaNuevaCita();
        } catch (Exception e) {
            mostrarAlerta("Error", "No se pudo abrir la ventana de nueva cita", e.getMessage());
        }
    }

    @FXML
    public void actualizarCitas(ActionEvent actionEvent) {
        try {
            cargarCitasPaciente();
            mostrarAlerta("Éxito", "Citas actualizadas",
                    "La lista de citas se ha actualizado correctamente.\n" +
                            "Total de citas: " + listaCitas.size());
        } catch (Exception e) {
            mostrarAlerta("Error", "Error al actualizar",
                    "No se pudieron actualizar las citas: " + e.getMessage());
        }
    }

    @FXML
    public void buscarPorMedico(KeyEvent keyEvent) {
        // El filtro se aplica automáticamente por el listener
    }


    private void seleccionarCita(Cita cita) {
        if (cita != null) {
            mostrarDetallesCita(cita);
        }
    }

    private void mostrarDetallesCita(Cita cita) {
        try {
            String detalles = String.format(
                    "Detalles de la Cita:\n\n" +
                            "Tipo: %s\n" +
                            "Fecha: %s\n" +
                            "Hora: %s\n" +
                            "Precio: $%,.0f\n" +
                            "Estado: %s",
                    cita.getTipo(),
                    cita.getFecha(),
                    cita.getHora(),
                    cita.getPrecio(),
                    determinarEstadoCita(cita)
            );

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Detalles de Cita");
            alert.setHeaderText("Información de la Cita Seleccionada");
            alert.setContentText(detalles);
            alert.showAndWait();

        } catch (Exception e) {
            mostrarAlerta("Error", "Error al mostrar detalles", e.getMessage());
        }
    }

    private void abrirVentanaNuevaCita() {
        try {
            // Aquí deberías implementar la lógica para abrir una nueva ventana
            // o cambiar de escena para solicitar una nueva cita
            mostrarAlerta("Funcionalidad", "Nueva Cita",
                    "Esta funcionalidad abrirá el formulario para solicitar una nueva cita médica.\n\n" +
                            "Paciente: " + (pacienteActual != null ?
                            pacienteActual.getNombre() + " " + pacienteActual.getApellido() : "No identificado"));
        } catch (Exception e) {
            mostrarAlerta("Error", "Error al abrir ventana", e.getMessage());
        }
    }

    private void mostrarAlerta(String titulo, String header, String contenido) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(header);
        alert.setContentText(contenido);
        alert.showAndWait();
    }

    @FXML
    public void limpiarFiltros(ActionEvent actionEvent) {
        txtBuscarMedico.clear();
        cmbFiltroEstado.setValue("Todas");
    }

    @FXML
    public void refrescarDatos(ActionEvent actionEvent) {
        cargarCitasPaciente();
        limpiarFiltros(actionEvent);
    }

    // Método para debug
    public void mostrarInfoDebug() {
        System.out.println("=== DEBUG INFO ===");
        System.out.println("Paciente actual: " + (pacienteActual != null ?
                pacienteActual.getNombre() + " " + pacienteActual.getApellido() : "null"));
        System.out.println("Total citas en lista: " + listaCitas.size());
        System.out.println("Total citas filtradas: " + citasFiltradas.size());
        System.out.println("Citas en clínica: " + clinica.getCitas().size());
    }
}