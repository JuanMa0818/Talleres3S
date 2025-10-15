module co.edu.uniquindio.poo.preparcial {
    requires javafx.controls;
    requires javafx.fxml;


    opens co.edu.uniquindio.poo.preparcial to javafx.fxml;
    exports co.edu.uniquindio.poo.preparcial;
}