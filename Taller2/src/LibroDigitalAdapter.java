public class LibroDigitalAdapter implements LibroFisico {
    private LibroDigital libro;

    public LibroDigitalAdapter (LibroDigital libro) {
        this.libro = libro;
    }

    public String obtener() {
        return libro.descargarPDF("Mi Libro");
    }
}
