package co.edu.uniquindio.poo.parcial3.Model;

public class CitaGeneral implements Cita{

    private String fecha;
    private String hora;
    private double precio;

    public CitaGeneral(String fecha, String hora, double precio) {
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
}
