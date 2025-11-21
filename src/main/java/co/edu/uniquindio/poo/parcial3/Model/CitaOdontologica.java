package co.edu.uniquindio.poo.parcial3.Model;

public class CitaOdontologica implements Cita{
    private String fecha;
    private String hora;
    private double precio;
    private Paciente paciente;
    private Medico medico;     // ✅ AGREGADO

    public CitaOdontologica(String fecha, String hora, double precio) {
        this.fecha = fecha;
        this.hora = hora;
        this.precio = precio;
        this.paciente = null;  // ✅ INICIALIZADO
        this.medico = null;    // ✅ INICIALIZADO
    }

    @Override
    public String getFecha() {
        return fecha;
    }

    @Override
    public String getHora() {
        return hora;
    }

    @Override
    public double getPrecio() {
        return precio;
    }

    @Override
    public String getTipo() {
        return "Cita Odontológica";
    }

    @Override
    public void generarResumen() {
        System.out.println("Resumen: " + getTipo() +
                " | Fecha: " + fecha +
                " | Hora: " + hora +
                " | Precio: $" + precio);
    }

    @Override
    public double getCostoConsulta() {
        return 5000;
    }

    // ✅ CORREGIDO: Ahora retorna el paciente
    @Override
    public Paciente getPaciente() {
        return paciente;
    }

    // ✅ CORREGIDO: Ahora retorna el médico
    @Override
    public Medico getMedico() {
        return medico;
    }

    // ✅ CORREGIDO: Ahora sí asigna el paciente
    @Override
    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    // ✅ CORREGIDO: Ahora sí asigna el médico
    @Override
    public void setMedico(Medico medico) {
        this.medico = medico;
    }
}