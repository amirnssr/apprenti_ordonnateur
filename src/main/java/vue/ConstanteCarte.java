package vue;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

/**
 * Interface ConstanteCarte contient toutes les constantes utilisées pour la carte du jeu.
 */
public interface ConstanteCarte {
    // Taille d'une case
    int CARRE = 20;

    // Nombre de cases en largeur et hauteur
    int NB_DE_CASE_LARGEUR = 32;
    int NB_DE_CASE_HAUTEUR = 32;

    // Dimensions du canvas
    int LARGEUR_CANVAS = CARRE * NB_DE_CASE_LARGEUR;
    int HAUTEUR_CANVAS = CARRE * NB_DE_CASE_HAUTEUR;

    // Dimensions de l'ovale représentant l'apprenti
    int LARGEUR_OVALE = CARRE / 2;
    int HAUTEUR_OVALE = CARRE / 2;

    // Couleurs utilisées dans l'interface
    Paint COULEUR_APPRENTI = Color.BLANCHEDALMOND;
    Paint COULEUR_GRILLE = Color.PERU;
    Paint COULEUR_CASE = Color.WHITESMOKE;

    // Intitulé du menu pour les scénarios
    String INTITULE_MENU_SCENARIOS = "Scenarios";

    // Règles du jeu
    String REGLE_DU_JEU = "L'apprenti commence à la position (0,0) et peut se déplacer dans les directions cardinales.\n" +
            "Il peut transporter un seul cristal à la fois, le prenant ou l'échangeant dans les temples.\n" +
            "Les temples contiennent des cristaux qui ne correspondent pas nécessairement à la couleur du temple suite à une tempête magique.\n" +
            "L'objectif est de réaligner les cristaux avec les temples correspondants en minimisant les déplacements.\n" +
            "Le jeu se termine lorsque tous les cristaux sont correctement alignés, et le total des mouvements est comptabilisé.";

    // Noms des couleurs des temples
    String[] TEMPLE_COLOR_NAMES = {
            "Rouge", "Orange", "Jaune", "Vert", "Bleu",
            "Turquoise", "Violet", "Rose", "Marron", "Gris"
    };


}
