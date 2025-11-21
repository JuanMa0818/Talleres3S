package co.edu.uniquindio.poo.parcial3.Model;

public class CitaOdontologica implements Cita{
    private String fecha;
    private String hora;
    private double precio;

    public CitaOdontologica(String fecha, String hora, double precio) {
        this.fecha = fecha;
        this.hora = hora;
        this.precio = precio;
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
}
