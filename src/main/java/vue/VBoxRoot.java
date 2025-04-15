package vue;

import controleur.Controleur;
import javafx.geometry.Insets;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import java.io.File;
import model.Apprenti;

/**
 * La classe VBoxRoot représente la racine de l'interface utilisateur.
 * Elle contient la barre de menu et le canvas principal.
 */
public class VBoxRoot extends VBox implements ConstanteCarte {
    private Apprenti apprenti;
    private Controleur controleur;
    private VBoxCanvas vueCanvas;
    private MenuBar menuBar;

    /**
     * Constructeur de la classe VBoxRoot.
     * Initialise l'apprenti, le contrôleur et configure la barre de menu.
     */
    public VBoxRoot() {
        // Initialisation des instances de modèles et de contrôleurs
        this.apprenti = new Apprenti();
        this.controleur = new Controleur(this);

        // Création de la barre de menu
        this.menuBar = new MenuBar();
        this.getChildren().add(menuBar);
        VBox.setMargin(menuBar, new Insets(9));

        // Ajout du menu des scénarios
        Menu menuScenarios = new Menu(INTITULE_MENU_SCENARIOS);
        menuBar.getMenus().add(menuScenarios);

        // Lecture des fichiers de scénarios et ajout dans le menu
        File[] scenarios = new File("scenarios").listFiles();
        if (scenarios != null) {
            for (File scenario : scenarios) {
                MenuItem menuItem = new MenuItem(scenario.getName());
                menuItem.setUserData(scenario);
                menuItem.setOnAction(controleur);
                menuScenarios.getItems().add(menuItem);
            }
        } else {
            System.out.println("Pas de scénario trouvé");
        }
    }

    /**
     * Configure et met à jour l'affichage principal avec le canvas de la vue.
     *
     * @param vueCanvas Le canvas à afficher.
     */
    public void setVBoxCanvas(VBoxCanvas vueCanvas) {
        this.vueCanvas = vueCanvas;
    }

    /**
     * Met à jour l'affichage en ajoutant le canvas à la vue.
     */
    public void updateDisplay() {
        this.getChildren().clear();
        this.getChildren().add(menuBar);
        if (vueCanvas != null) {
            this.getChildren().add(vueCanvas);
        }
    }
}
