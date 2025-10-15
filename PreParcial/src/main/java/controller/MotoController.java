package view;

import controller.MotoController;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Moto;

public class MotoViewController {

    @FXML private TextField txtPlaca;
    @FXML private TextField txtMarca;
    @FXML private TextField txtModelo;
    @FXML private TextArea txtAreaSalida;

    private MotoController controller;

    public MotoViewController() {
        controller = new MotoController();
    }

    @FXML
    public void registrarMotoNueva() {
        String placa = txtPlaca.getText();
        String marca = txtMarca.getText();
        int modelo = Integer.parseInt(txtModelo.getText());

        controller.registrarMotoNueva(placa, marca, modelo);
        txtAreaSalida.appendText("✅ Moto nueva registrada: " + placa + "\n");
        limpiarCampos();
    }

    @FXML
    public void registrarMotoVieja() {
        String codigo = txtPlaca.getText();
        int anio = Integer.parseInt(txtModelo.getText());
        int kilometraje = (int)(Math.random() * 100000); // valor de ejemplo

        controller.registrarMotoVieja(codigo, anio, kilometraje);
        txtAreaSalida.appendText("🕰️ Moto vieja registrada: " + codigo + "\n");
        limpiarCampos();
    }

    @FXML
    public void decorarGps() {
        Moto moto = crearMotoDesdeCampos();
        controller.implementarGps(moto);
        txtAreaSalida.appendText("✨ GPS agregado a " + moto.getPlaca() + "\n");
    }

    @FXML
    public void decorarRadio() {
        Moto moto = crearMotoDesdeCampos();
        controller.implementarRadio(moto);
        txtAreaSalida.appendText("📻 Radio agregado a " + moto.getPlaca() + "\n");
    }

    @FXML
    public void decorarNitro() {
        Moto moto = crearMotoDesdeCampos();
        controller.implementarNitro(moto);
        txtAreaSalida.appendText("🔥 Nitro agregado a " + moto.getPlaca() + "\n");
    }

    @FXML
    public void mostrarMotos() {
        txtAreaSalida.appendText("📋 Listado de motos:\n");
        controller.mostrarMotosRegistradas();
    }

    private Moto crearMotoDesdeCampos() {
        return new Moto.Builder()
                .placa(txtPlaca.getText())
                .marca(txtMarca.getText())
                .modelo(Integer.parseInt(txtModelo.getText()))
                .build();
    }

    private void limpiarCampos() {
        txtPlaca.clear();
        txtMarca.clear();
        txtModelo.clear();
    }
}
