package co.edu.uniquindio.poo.parcial3.Model;

public class MedicoGeneral extends Persona implements Medico  {

    private String codigo;
    private String especialidad;
    private String contraseña;

    public MedicoGeneral(String nombre, String apellido, String identificacion, String telefono, String email,
                         String codigoGeneral, String contraseña) {

        super(nombre, apellido, identificacion, telefono, email,contraseña);
        this.codigo = codigoGeneral;
        this.especialidad = "Medicina General";
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    @Override
    public String getNombre() {
        return super.getNombre();
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
        System.out.println("Paciente siendo atendido por un médico general");
    }

    @Override
    public double getPrecio() {
        return 30000;
    }

    @Override
    public String toString() {
        return getNombre() + " - " + especialidad;
    }
}
