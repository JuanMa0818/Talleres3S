package model;

import java.util.ArrayList;

public class GestorMotos {

    private static GestorMotos instance;
    private ArrayList<Moto> motos;

    // Constructor privado (para patrón Singleton)
    private GestorMotos() {
        this.motos = new ArrayList<>();
    }

    public static GestorMotos getInstance() {
        if (instance == null) {
            instance = new GestorMotos();
        }
        return instance;
    }

    public ArrayList<Moto> getMotos() {
        return motos;
    }

    public void agregarMoto(Moto moto) {
        motos.add(moto);
    }
}
