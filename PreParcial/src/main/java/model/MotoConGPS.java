package model;

public class MotoConGPS extends MotoDecorator {

    public MotoConGPS(Moto moto) {
        super(moto);
    }

    @Override
    public String getInfoCompleta() {
        return moto.getInfoCompleta() + " + GPS incorporado";
    }
}
