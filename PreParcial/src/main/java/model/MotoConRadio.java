package model;

public class MotoConRadio extends MotoDecorator {
    public MotoConRadio(Moto moto) {
        super(moto);
    }

    @Override
    public String getInfoCompleta() {
        return moto.toString() + " Radio incorporado exitosamente";
    }
}
