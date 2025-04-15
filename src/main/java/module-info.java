/**
 * Le module de l'application AppOrdo.
 */


module com.example.appordo {
    requires javafx.controls;
    requires javafx.fxml;

    // Export your package to javafx.graphics, which needs to access it.
    exports vue to javafx.graphics;

    // If you use FXML, ensure javafx.fxml can access your controllers
    opens com.example.appordo to javafx.fxml;
}
