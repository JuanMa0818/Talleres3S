//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        BibliotecaFacade biblioteca = new BibliotecaFacade();
        biblioteca.prestarLibro("Juan", "El Quijote");

        Libro libro1 = new LibroBasico("Harry Potter");
        System.out.println("\n1. " + libro1.obtenerDescripcion() + " - $" + libro1.obtenerPrecio());

        Libro libro2 = new MarcaLibros(new LibroBasico("El Hobbit"));
        System.out.println("2. " + libro2.obtenerDescripcion() + " - $" + libro2.obtenerPrecio());

        Libro libro3 = new Lamina(new MarcaLibros(new FundaProtectora(new LibroBasico("Cien años de soledad"))));
        System.out.println("3. " + libro3.obtenerDescripcion() + " - $" + libro3.obtenerPrecio());


        LibroDigital libroDigital = new LibroDigital();
        LibroFisico adaptador = new LibroDigitalAdapter (libroDigital);

        Estante estante = new Estante();
        estante.mostrarLibro(adaptador);

    }
}
