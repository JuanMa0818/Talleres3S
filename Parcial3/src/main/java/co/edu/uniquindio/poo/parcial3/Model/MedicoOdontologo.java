package co.edu.uniquindio.poo.parcial3.Model;

public class MedicoOdontologo extends Persona implements Medico {

    private String codigo;
    private String especialidad;

    public MedicoOdontologo(String nombre, String apellido, String identificacion, String telefono,
                            String email, String codigoOdontologo, String contraseña) {

        super(nombre, apellido, identificacion, telefono, email, contraseña);
        this.codigo = codigoOdontologo;
        this.especialidad = "Odontología";
    }

    public String getCodigoOdontologo() {
        return codigo;
    }

    public void setCodigoOdontologo(String codigoOdontologo) {
        this.codigo = codigoOdontologo;
    }

    @Override
    public String getNombre() {
        return super.getNombre();
    }

    @Override
    public double getPrecio() {
        return 50000;
    }

    @Override
    public String getIdentificacion() {
        return super.getIdentificacion();
    }

    @Override
    public String getEspecialidad() {
        return especialidad;
    }

    @Override
    public void atenderPaciente() {
        System.out.println("Paciente siendo atendido por un médico odontólogo");
    }

    @Override
    public String toString() {
        return getNombre() + " - " + especialidad;
    }
}
