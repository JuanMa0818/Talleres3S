package model;

public class MotoConNitro extends MotoDecorator {
    public MotoConNitro(Moto moto) {
        super(moto);
    }

    @Override
    public String getInfoCompleta() {
        return moto.toString() + " Nitro incorporado exitosamente";
    }
}
