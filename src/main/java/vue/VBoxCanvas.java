package vue;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import controleur.Controleur;
import model.Temple;

import java.util.ArrayList;
import java.util.List;

import static vue.ConstanteCarte.*;

/**
 * La classe VBoxCanvas représente l'interface graphique principale où les temples et l'apprenti sont dessinés.
 */
public class VBoxCanvas extends VBox {
    public Controleur controleur;
    public Label nbDePas = new Label("Nombre de pas : 0");
    public Label infoTemple = new Label("Informations des temples:");
    public Label carriedCrystalLabel = new Label("Cristal porté: ");
    public Label playerPositionLabel = new Label("Position de l'apprenti : (0, 0)");
    public Canvas canvasCarte = new Canvas();
    public GraphicsContext graphicsContext2D = canvasCarte.getGraphicsContext2D();
    public List<Temple> temples = new ArrayList<>();

    /**
     * Constructeur de la classe VBoxCanvas.
     *
     * @param controleur Le contrôleur qui gère les interactions.
     */
    public VBoxCanvas(Controleur controleur) {
        this.controleur = controleur;
        initialSetup();
        handleMouseClicks();
        controleur.centerApprenti(); // Centrer l'apprenti avant de redessiner
        redrawCanvas();
    }

    /**
     * Configure l'interface initiale.
     */
    public void initialSetup() {
        ScrollPane mainScrollPane = new ScrollPane();
        mainScrollPane.setFitToWidth(true);
        mainScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        mainScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        canvasCarte.setWidth(LARGEUR_CANVAS);
        canvasCarte.setHeight(HAUTEUR_CANVAS);
        drawGrid();

        MenuBar menuBar = new MenuBar();
        Menu quitMenu = new Menu("Quitter");
        MenuItem exitItem = new MenuItem("Exit");
        quitMenu.getItems().add(exitItem);
        menuBar.getMenus().add(quitMenu);
        exitItem.setOnAction(event -> Platform.exit());

        Menu helpMenu = new Menu("Aide");
        MenuItem aboutItem = new MenuItem("Règles");
        helpMenu.getItems().add(aboutItem);
        menuBar.getMenus().add(helpMenu);
        aboutItem.setOnAction(event -> showRules());

        Button dropCrystalButton = new Button("Déposer cristal");
        dropCrystalButton.setOnAction(event -> controleur.deposerCristal(controleur.findTempleAtCurrentPosition()));

        Button exchangeCrystalButton = new Button("Échanger Cristaux");
        exchangeCrystalButton.setOnAction(event -> controleur.echangerCristaux(controleur.findTempleAtCurrentPosition()));

        Button takeCrystalButton = new Button("Prendre Cristal");
        takeCrystalButton.setOnAction(event -> controleur.prendreCristal(controleur.findTempleAtCurrentPosition()));

        Button sortButton = new Button("Trier les Temples");
        sortButton.setOnAction(event -> controleur.trierEtRearrangerCrystaux());

        Button optimizedSortButton = new Button("Algorithme Heuristique");
        optimizedSortButton.setOnAction(event -> controleur.optimizedSort());

        HBox buttonBox = new HBox(10, takeCrystalButton, dropCrystalButton, exchangeCrystalButton, sortButton, optimizedSortButton);
        buttonBox.setPadding(new Insets(10));

        VBox labelContainer = new VBox(10, nbDePas, infoTemple, carriedCrystalLabel, playerPositionLabel, buttonBox);
        labelContainer.setPadding(new Insets(10));
        HBox mainContent = new HBox(labelContainer, canvasCarte);
        HBox.setHgrow(canvasCarte, Priority.ALWAYS);

        VBox layout = new VBox(menuBar, mainContent);
        mainScrollPane.setContent(layout);

        ScrollBar scrollBar = new ScrollBar();
        scrollBar.setOrientation(Orientation.VERTICAL);
        scrollBar.setMin(0);
        scrollBar.setMax(1.0);
        scrollBar.setVisibleAmount(0.1);
        scrollBar.valueProperty().addListener((obs, oldValue, newValue) -> mainScrollPane.setVvalue(newValue.doubleValue()));

        HBox finalLayout = new HBox(mainScrollPane, scrollBar);
        this.getChildren().add(finalLayout);
    }

    /**
     * Dessine la grille sur le canvas.
     */
    public void drawGrid() {
        graphicsContext2D.setStroke(COULEUR_GRILLE);
        for (int i = 0; i < LARGEUR_CANVAS; i += CARRE) {
            for (int j = 0; j < HAUTEUR_CANVAS; j += CARRE) {
                graphicsContext2D.strokeRect(i, j, CARRE, CARRE);
            }
        }
    }

    /**
     * Gère les clics de souris sur le canvas.
     */
    public void handleMouseClicks() {
        canvasCarte.setOnMouseClicked(event -> {
            int abscisse = (int) (event.getX() / CARRE) - (NB_DE_CASE_LARGEUR / 2);
            int ordonne = (int) (event.getY() / CARRE) - (NB_DE_CASE_HAUTEUR / 2);
            controleur.handleMouseClick(abscisse, ordonne);
        });
    }

    /**
     * Dessine les temples sur le canvas.
     *
     * @param newTemples La liste des nouveaux temples à dessiner.
     */
    public void drawTemples(List<Temple> newTemples) {
        temples.clear();
        temples.addAll(newTemples);
        updateTempleInfo();
        redrawCanvas();
    }

    /**
     * Redessine tout le canvas.
     */
    public void redrawCanvas() {
        graphicsContext2D.clearRect(0, 0, LARGEUR_CANVAS, HAUTEUR_CANVAS);
        drawGrid();
        for (Temple temple : temples) {
            Color templeColor = getColorForTemple(temple.getCouleurTemple());
            graphicsContext2D.setFill(templeColor);

            double x = temple.getX() + 16;
            double y = temple.getY() + 16;
            double offsetX = (LARGEUR_CANVAS / 2) - ((NB_DE_CASE_LARGEUR * CARRE) / 2);
            double offsetY = (HAUTEUR_CANVAS / 2) - ((NB_DE_CASE_HAUTEUR * CARRE) / 2);

            x = offsetX + (x * CARRE);
            y = offsetY + (y * CARRE);

            graphicsContext2D.fillRect(x, y, CARRE, CARRE);

            if (temple.getCouleurCristal() != 0) {
                Color crystalColor = getColorForTemple(temple.getCouleurCristal());
                graphicsContext2D.setFill(crystalColor.deriveColor(0, 1.2, 1.2, 0.8));
                graphicsContext2D.fillRect(x + 5, y + 5, CARRE - 10, CARRE - 10);
            }
        }
        drawPlayer();
    }

    /**
     * Dessine l'apprenti sur le canvas.
     */
    public void drawPlayer() {
        graphicsContext2D.setFill(COULEUR_APPRENTI);

        // Centre de la carte en termes de cases
        double centerX = (LARGEUR_CANVAS / 2) - ((NB_DE_CASE_LARGEUR * CARRE) / 2);
        double centerY = (HAUTEUR_CANVAS / 2) - ((NB_DE_CASE_HAUTEUR * CARRE) / 2);

        // Position actuelle de l'apprenti ajustée pour le centrage initial
        double x = centerX + ((controleur.getApprenti().getPosition().getAbscisse() + 16) * CARRE);
        double y = centerY + ((controleur.getApprenti().getPosition().getOrdonnee() + 16) * CARRE);

        // Ajustement pour centrer l'apprenti dans la case
        x += (CARRE - LARGEUR_OVALE) / 2;
        y += (CARRE - HAUTEUR_OVALE) / 2;

        graphicsContext2D.fillOval(x, y, LARGEUR_OVALE, HAUTEUR_OVALE);
    }

    /**
     * Obtient la couleur pour un temple donné en fonction de son identifiant.
     *
     * @param templeId L'identifiant du temple.
     * @return La couleur du temple.
     */
    public Color getColorForTemple(int templeId) {
        Color[] colors = new Color[]{
                Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.BLUE,
                Color.TURQUOISE, Color.PURPLE, Color.PINK, Color.BROWN, Color.GREY
        };
        return colors[(templeId - 1) % colors.length];
    }

    /**
     * Met à jour les informations sur les temples affichées dans l'interface.
     */
    public void updateTempleInfo() {
        StringBuilder templeInfo = new StringBuilder("Informations des temples:\n");
        for (Temple temple : temples) {
            templeInfo.append(temple.toString()).append("\n");
        }
        infoTemple.setText(templeInfo.toString());
    }

    /**
     * Affiche les règles du jeu dans une boîte de dialogue.
     */
    public void showRules() {
        Alert aboutDialog = new Alert(Alert.AlertType.INFORMATION);
        aboutDialog.setTitle("À propos du Jeu");
        aboutDialog.setHeaderText("Règles de L'Apprenti Ordonnateur");

        Label contentLabel = new Label(REGLE_DU_JEU);
        contentLabel.setWrapText(true);

        ScrollPane scrollPane = new ScrollPane(contentLabel);
        scrollPane.setPrefSize(600, 100);
        scrollPane.setFitToWidth(true);

        aboutDialog.getDialogPane().setContent(scrollPane);
        aboutDialog.showAndWait();
    }

    /**
     * Met à jour le compteur de pas affiché.
     *
     * @param steps Le nombre de pas.
     */
    public void setStepCount(int steps) {
        nbDePas.setText("Nombre de pas : " + steps);
    }

    /**
     * Met à jour l'étiquette de la couleur du cristal porté.
     *
     * @param color La couleur du cristal.
     */
    public void setCarriedCrystalLabel(int color) {
        carriedCrystalLabel.setText("Cristal porté: " + getColorName(color));
    }

    /**
     * Met à jour l'étiquette de la position de l'apprenti.
     *
     * @param x La coordonnée x de l'apprenti.
     * @param y La coordonnée y de l'apprenti.
     */
    public void setPlayerPositionLabel(int x, int y) {
        playerPositionLabel.setText("Position de l'apprenti : (" + x + ", " + -y + ")");
    }

    /**
     * Obtient le nom de la couleur en fonction de son identifiant.
     *
     * @param colorId L'identifiant de la couleur.
     * @return Le nom de la couleur.
     */
    public String getColorName(int colorId) {
        switch(colorId) {
            case 1: return "Rouge";
            case 2: return "Orange";
            case 3: return "Jaune";
            case 4: return "Vert";
            case 5: return "Bleu";
            case 6: return "Turquoise";
            case 7: return "Violet";
            case 8: return "Rose";
            case 9: return "Marron";
            case 10: return "Gris";
            default: return "Aucun";
        }
    }

    /**
     * La classe CanvasClickEvent représente un événement de clic sur le canvas.
     */
    public static class CanvasClickEvent extends Event {
        public static final EventType<CanvasClickEvent> CANVAS_CLICK = new EventType<>(Event.ANY, "CANVAS_CLICK");
        private final int abscisse;
        private final int ordonnee;

        /**
         * Constructeur de l'événement CanvasClickEvent.
         *
         * @param eventType Le type de l'événement.
         * @param abscisse  La coordonnée x du clic.
         * @param ordonnee  La coordonnée y du clic.
         */
        public CanvasClickEvent(EventType<? extends Event> eventType, int abscisse, int ordonnee) {
            super(eventType);
            this.abscisse = abscisse;
            this.ordonnee = ordonnee;
        }

        /**
         * Obtient la coordonnée x du clic.
         *
         * @return La coordonnée x.
         */
        public int getAbscisse() {
            return abscisse;
        }

        /**
         * Obtient la coordonnée y du clic.
         *
         * @return La coordonnée y.
         */
        public int getOrdonnee() {
            return ordonnee;
        }
    }
}
