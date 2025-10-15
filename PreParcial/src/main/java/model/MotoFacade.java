package model;

public class MotoFacade {

    // Simulamos un gestor general para registrar y modificar motos
    private GestorMotos gestor = GestorMotos.getInstance();

    // Registrar una moto nueva (usa Builder)
    public void registrarMotoNueva(String placa, String marca, int modelo) {
        Moto moto = new Moto.Builder()
                .placa(placa)
                .marca(marca)
                .modelo(modelo)
                .build();

        gestor.getMotos().add(moto);

        System.out.println("=== Registro de moto nueva ===");
        System.out.println(moto.getInfoCompleta());
        System.out.println("Registro exitoso \n");
    }

    // Registrar una moto vieja (usa Adapter)
    public void registrarMotoVieja(String codigo, int anio, int kilometraje) {
        MotoViejaAdapter motoVieja = new MotoViejaAdapter(codigo, anio, kilometraje);
        System.out.println("=== Registro de moto vieja ===");
        System.out.println(motoVieja.getInfoCompleta());
        System.out.println("Moto antigua registrada correctamente \n");
    }

    // Decorar moto con GPS
    public void implementarGPS(Moto moto) {
        Motoo decorada = new MotoConGPS(moto);
        System.out.println("=== Añadiendo GPS ===");
        System.out.println(decorada.getInfoCompleta());
        System.out.println("GPS implementado correctamente \n");
    }

    // Decorar moto con Radio
    public void implementarRadio(Moto moto) {
        Motoo decorada = new MotoConRadio(moto);
        System.out.println("=== Añadiendo Radio ===");
        System.out.println(decorada.getInfoCompleta());
        System.out.println("Radio implementado correctamente \n");
    }

    // Decorar moto con Nitro
    public void implementarNitro(Moto moto) {
        Motoo decorada = new MotoConNitro(moto);
        System.out.println("=== Añadiendo Nitro ===");
        System.out.println(decorada.getInfoCompleta());
        System.out.println("Nitro implementado correctamente \n");
    }
}
