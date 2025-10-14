public class LibroBasico implements Libro {
    private String titulo;

    public LibroBasico(String titulo) {
        this.titulo = titulo;
    }

    public String obtenerTitulo() {
        return titulo;
    }

    public double obtenerPrecio() {
        return 15.0;
    }

    public String obtenerDescripcion() {
        return titulo;
    }
}

