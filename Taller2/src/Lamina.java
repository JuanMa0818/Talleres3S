public class Lamina extends LibroDecorator {
    public Lamina(Libro libro) {
        super(libro);
    }

    public double obtenerPrecio() {
        return libro.obtenerPrecio() + 2.5;
    }

    public String obtenerDescripcion() {
        return libro.obtenerDescripcion() + " + Lámina";
    }
}
