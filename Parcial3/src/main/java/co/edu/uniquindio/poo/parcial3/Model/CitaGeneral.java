package co.edu.uniquindio.poo.parcial3.Model;

public class CitaGeneral implements Cita{
    private String fecha;
    private String hora;
    private double precio;
    private Paciente paciente;
    private Medico medico;

    public CitaGeneral(String fecha, String hora, double precio) {
        this.fecha = fecha;
        this.hora = hora;
        this.precio = precio;
        this.paciente = null;
        this.medico = null;
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
        return "Cita General";
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
        return 3000;
    }

    @Override
    public Medico getMedico() {
        return medico;
    }

    @Override
    public Paciente getPaciente() {
        return paciente;
    }


    @Override
    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }


    @Override
    public void setMedico(Medico medico) {
        this.medico = medico;
    }
}