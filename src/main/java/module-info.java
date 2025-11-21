module co.edu.uniquindio.poo.parcial3 {
    requires javafx.controls;
    requires javafx.fxml;

    // Para permitir acceso desde JavaFX a los controladores
    opens co.edu.uniquindio.poo.parcial3.Controller to javafx.fxml;

    // Para permitir acceso al paquete principal
    opens co.edu.uniquindio.poo.parcial3 to javafx.fxml;

    // Para modelos si se usan en FXML
    opens co.edu.uniquindio.poo.parcial3.Model to javafx.fxml;

    exports co.edu.uniquindio.poo.parcial3;
    exports co.edu.uniquindio.poo.parcial3.Model;
}
