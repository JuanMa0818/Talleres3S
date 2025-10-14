class BibliotecaFacade {
    private BaseDatos bd;
    private VerificadorDisponibilidad verificador;
    private SistemaDeuda deuda;

    public BibliotecaFacade() {
        this.bd = new BaseDatos();
        this.verificador = new VerificadorDisponibilidad();
        this.deuda = new SistemaDeuda();
    }

    public void prestarLibro(String usuario, String libro) {
        System.out.println("\nPidiendo libro: " + libro);
        bd.conectar();

        if (verificador.esDisponible(libro)) {
            deuda.registrarPrestamo(usuario, libro);
            System.out.println("Libro prestado");
        } else {
            System.out.println("Libro no disponible");
        }
    }
}