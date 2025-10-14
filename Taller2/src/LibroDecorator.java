public abstract class LibroDecorator implements Libro{
    protected Libro libro;

    public LibroDecorator (Libro libro) {
        this.libro = libro;
    }

    public String obtenerTitulo() {
        return libro.obtenerTitulo();
    }

    public double obtenerPrecio() {
        return libro.obtenerPrecio();
    }

    public String obtenerDescripcion() {
        return libro.obtenerDescripcion();
    }
}
