package model;

public abstract class MotoDecorator implements Motoo {
    protected Moto moto;

    public MotoDecorator(Moto moto) {
        this.moto = moto;
    }

    @Override
    public String getMarca() {
        return moto.getMarca();
    }

    @Override
    public String getPlaca() {
        return moto.getPlaca();
    }

    @Override
    public int getModelo() {
        return moto.getModelo();
    }
}
