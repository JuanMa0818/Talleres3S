package model;

public class Moto implements Motoo {
    private String placa;
    private String marca;
    private int modelo;

    private Moto(Builder builder) {
        this.placa = builder.placa;
        this.marca = builder.marca;
        this.modelo = builder.modelo;
    }

    public String getPlaca() { return placa; }
    public String getMarca() { return marca; }
    public int getModelo() { return modelo; }

    @Override
    public String getInfoCompleta() {
        return String.format("Placa: %s, Marca: %s, Modelo: %d", placa, marca, modelo);
    }

    // Builder Pattern
    public static class Builder {
        private String placa;
        private String marca;
        private int modelo;

        public Builder placa(String placa) {
            this.placa = placa;
            return this;
        }

        public Builder marca(String marca) {
            this.marca = marca;
            return this;
        }

        public Builder modelo(int modelo) {
            this.modelo = modelo;
            return this;
        }

        public Moto build() {
            return new Moto(this);
        }
    }
}
