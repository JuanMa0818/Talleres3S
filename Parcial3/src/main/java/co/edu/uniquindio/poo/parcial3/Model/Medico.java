package co.edu.uniquindio.poo.parcial3.Model;

public interface Medico {
    String getNombre();
    String getIdentificacion();
    String getEspecialidad();
    double getPrecio();
    String getEmail();
    String getTelefono();
    String getContraseña();
    void atenderPaciente();
}