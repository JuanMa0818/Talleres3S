module co.edu.uniquindio.poo.parcial3 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.desktop; // Para funcionalidades adicionales si las necesitas

    // Para permitir acceso desde JavaFX a los controladores
    opens co.edu.uniquindio.poo.parcial3.Controller to javafx.fxml;

    // Para permitir acceso al paquete principal
    opens co.edu.uniquindio.poo.parcial3 to javafx.fxml;

    // Para modelos si se usan en FXML (reflexión)
    opens co.edu.uniquindio.poo.parcial3.Model to javafx.fxml;

    // Exportar paquetes
    exports co.edu.uniquindio.poo.parcial3;
    exports co.edu.uniquindio.poo.parcial3.Controller;
    exports co.edu.uniquindio.poo.parcial3.Model;
}