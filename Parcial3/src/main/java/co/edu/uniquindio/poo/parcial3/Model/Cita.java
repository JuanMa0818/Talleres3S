package co.edu.uniquindio.poo.parcial3.Model;

public interface Cita {

    String getFecha();
    String getHora();
    double getPrecio();
    String getTipo();
    void generarResumen();

    double getCostoConsulta();

    Paciente getPaciente();
    Medico getMedico();
    public void setPaciente(Paciente paciente);
    public void setMedico(Medico medico);
}
