package co.edu.uniquindio.poo.parcial3.Model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Clase que representa una consulta médica realizada
 */
public class Consulta {

    private String idConsulta;
    private LocalDateTime fechaHora;
    private Cita citaAsociada;
    private Paciente paciente;
    private Medico medico;
    private String sintomas;
    private String diagnostico;
    private String tratamiento;
    private static int contadorConsultas = 1;

    /**
     * Constructor completo de Consulta
     */
    public Consulta(Cita citaAsociada, Paciente paciente, Medico medico,
                    String sintomas, String diagnostico, String tratamiento) {
        this.idConsulta = generarIdConsulta();
        this.fechaHora = LocalDateTime.now();
        this.citaAsociada = citaAsociada;
        this.paciente = paciente;
        this.medico = medico;
        this.sintomas = sintomas;
        this.diagnostico = diagnostico;
        this.tratamiento = tratamiento;
    }

    /**
     * Genera un ID único para la consulta
     */
    private String generarIdConsulta() {
        return "CONS-" + String.format("%04d", contadorConsultas++);
    }

    /**
     * Genera un resumen de la consulta
     */
    public String generarResumen() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        StringBuilder resumen = new StringBuilder();
        resumen.append("═══════════════════════════════════════\n");
        resumen.append("       RESUMEN DE CONSULTA MÉDICA\n");
        resumen.append("═══════════════════════════════════════\n\n");

        resumen.append("ID Consulta: ").append(idConsulta).append("\n");
        resumen.append("Fecha: ").append(fechaHora.format(formatter)).append("\n\n");

        resumen.append("PACIENTE:\n");
        resumen.append("  - Nombre: ").append(paciente.getNombre()).append(" ")
                .append(paciente.getApellido()).append("\n");
        resumen.append("  - Identificación: ").append(paciente.getIdentificacion()).append("\n");
        resumen.append("  - Alergias: ").append(paciente.getAlergias()).append("\n\n");

        resumen.append("MÉDICO:\n");
        resumen.append("  - Nombre: ").append(medico.getNombre()).append("\n");
        resumen.append("  - Especialidad: ").append(medico.getEspecialidad()).append("\n\n");

        resumen.append("SÍNTOMAS:\n");
        resumen.append("  ").append(sintomas).append("\n\n");

        resumen.append("DIAGNÓSTICO:\n");
        resumen.append("  ").append(diagnostico).append("\n\n");

        resumen.append("TRATAMIENTO:\n");
        resumen.append("  ").append(tratamiento).append("\n\n");

        resumen.append("Costo de la consulta: $").append(citaAsociada.getCostoConsulta()).append("\n");
        resumen.append("═══════════════════════════════════════\n");

        return resumen.toString();
    }

    /**
     * Imprime el resumen de la consulta
     */
    public void imprimirResumen() {
        System.out.println(generarResumen());
    }

    // ========== GETTERS ==========

    public String getIdConsulta() {
        return idConsulta;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public Cita getCitaAsociada() {
        return citaAsociada;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public Medico getMedico() {
        return medico;
    }

    public String getSintomas() {
        return sintomas;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public String getTratamiento() {
        return tratamiento;
    }

    // ========== SETTERS ==========

    public void setSintomas(String sintomas) {
        this.sintomas = sintomas;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public void setTratamiento(String tratamiento) {
        this.tratamiento = tratamiento;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return "Consulta{" +
                "id='" + idConsulta + '\'' +
                ", fecha=" + fechaHora.format(formatter) +
                ", paciente=" + paciente.getNombre() +
                ", medico=" + medico.getNombre() +
                '}';
    }
}