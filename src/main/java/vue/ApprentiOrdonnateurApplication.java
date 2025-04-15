package vue;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.File;

/**
 * La classe ApprentiOrdonnateurApplication est le point d'entrée de l'application.
 * Elle configure et lance l'interface utilisateur.
 */
public class ApprentiOrdonnateurApplication extends Application {

    /**
     * Démarre l'application et initialise la scène principale.
     *
     * @param stage La fenêtre principale de l'application.
     * @throws Exception Si une erreur survient lors de l'initialisation.
     */
    @Override
    public void start(Stage stage) throws Exception {
        VBoxRoot root = new VBoxRoot(); // Création de la racine de l'interface

        // Création de la scène avec la racine et des dimensions 900x500
        Scene scene = new Scene(root, 900, 500);

        // Chargement de la feuille de style CSS
        File css = new File("css" + File.separator + "styleApplication.css");
        scene.getStylesheets().add(css.toURI().toString());

        // Configuration de la fenêtre principale
        stage.setTitle("Apprenti Ordonnateur");
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
    }

    /**
     * Méthode principale pour lancer l'application.
     *
     * @param args Les arguments de la ligne de commande.
     */
    public static void main(String[] args) {
        launch(args); // Lancement de l'application
    }
}
