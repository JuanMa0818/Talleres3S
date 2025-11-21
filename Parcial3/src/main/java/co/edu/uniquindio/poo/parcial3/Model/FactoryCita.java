package co.edu.uniquindio.poo.parcial3.Model;

public class FactoryCita {
    public static Cita crearCita(String tipo, String fecha, String hora, double precio) {

        if (tipo == null) return null;

        switch (tipo.toLowerCase()) {

            case "general":
                return new CitaGeneral(fecha, hora, precio);

            case "odontologica":
                return new CitaOdontologica(fecha, hora, precio);

            default:
                throw new IllegalArgumentException("Tipo de cita no válido: " + tipo);
        }
    }
}
