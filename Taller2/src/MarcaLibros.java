public class MarcaLibros extends LibroDecorator {
    public MarcaLibros(Libro libro) {
        super(libro);
    }

    public double obtenerPrecio() {
        return libro.obtenerPrecio() + 2.0;
    }

    public String obtenerDescripcion() {
        return libro.obtenerDescripcion() + " + Marca Libros";

    }
}

