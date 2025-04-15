
package com;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
/**
 * La classe HelloController est un exemple de contrôleur pour l'application.
 */
public class HelloController {
    @FXML
    private Label welcomeText;
    /**
     * Méthode appelée lorsque le bouton est cliqué.
     * Affiche un message de bienvenue.
     */
    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}