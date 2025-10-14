public class FundaProtectora extends LibroDecorator {
    public FundaProtectora (Libro libro) {
        super (libro);
    }

    public double obtenerPrecio() {
        return libro.obtenerPrecio() + 3.0;
    }

    public String obtenerDescripcion() {
        return libro.obtenerDescripcion() + " + Funda Protectora";
    }
}
