package com;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
/**
 * La classe HelloApplication est un exemple de point d'entrée de l'application.
 * Elle configure et lance l'interface utilisateur.
 */
public class HelloApplication extends Application {
    /**
     * Démarre l'application et initialise la scène principale.
     *
     * @param stage La fenêtre principale de l'application.
     * @throws Exception Si une erreur survient lors de l'initialisation.
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }
    /**
     * Méthode principale pour lancer l'application.
     *
     * @param args Les arguments de la ligne de commande.
     */
    public static void main(String[] args) {
        launch();
    }// Lancement de l'application
}