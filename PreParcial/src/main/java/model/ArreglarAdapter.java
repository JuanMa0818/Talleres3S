
package model;

public class ArreglarAdapter implements Motoo {
    private SIstemaArreglar arreglar;
    public ArreglarAdapter(SIstemaArreglar arreglar) {
        this.arreglar = arreglar;

    }

    @Override
    public void realizarArreglo(String placa,String marca,int modelo) {
        arreglar.enviarAArreglar(placa,marca,modelo);

    }

}