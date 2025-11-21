package co.edu.uniquindio.poo.parcial3.Model;

public class FactoryMedico {
    public static Medico crearMedico(String tipo, String nombre,String apellido,String telefono, String email,
                                     String identificacion, String codigo) {

        if (tipo == null) return null;

        switch (tipo.toLowerCase()) {
            case "general":
                return new MedicoGeneral(nombre, apellido, identificacion, telefono, email, codigo);

            case "odontologo":
                return new MedicoOdontologo(nombre, apellido,identificacion, telefono, email, codigo);

            default:
                throw new IllegalArgumentException("Especialidad no válida: " + tipo);
        }

    }

}
