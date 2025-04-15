package model;

import static vue.ConstanteCarte.NB_DE_CASE_HAUTEUR;
import static vue.ConstanteCarte.NB_DE_CASE_LARGEUR;

/**
 * La classe Apprenti représente l'apprenti qui se déplace sur la carte pour
 * réarranger les cristaux.
 */
public class Apprenti {
    public int abscisse; // Position x de l'apprenti
    public int ordonnee; // Position y de l'apprenti
    public int nbPas; // Compteur de pas
    public int carriedCrystalColor; // Couleur du cristal porté par l'apprenti

    /**
     * Constructeur de l'apprenti. Initialise sa position au centre de la carte
     * et réinitialise les compteurs.
     */
    public Apprenti() {
        setPosition(NB_DE_CASE_LARGEUR / 2, NB_DE_CASE_HAUTEUR / 2);
        this.nbPas = 0;
        this.carriedCrystalColor = 0;
    }

    /**
     * Déplace l'apprenti d'une case vers la position cible.
     *
     * @param xCible La position x cible.
     * @param yCible La position y cible.
     */
    public void deplacerPasAPas(int xCible, int yCible) {
        if (this.abscisse != xCible) {
            this.abscisse += (xCible > this.abscisse) ? 1 : -1; // Déplace d'une case à la fois
            this.nbPas++; // Incrémente le compteur de pas
        } else if (this.ordonnee != yCible) {
            this.ordonnee += (yCible > this.ordonnee) ? 1 : -1; // Déplace d'une case à la fois
            this.nbPas++; // Incrémente le compteur de pas
        }
    }

    // Getters

    /**
     * Obtient la position x de l'apprenti.
     *
     * @return La position x de l'apprenti.
     */
    public int getAbscisse() {
        return abscisse;
    }

    /**
     * Obtient la position y de l'apprenti.
     *
     * @return La position y de l'apprenti.
     */
    public int getOrdonnee() {
        return ordonnee;
    }

    /**
     * Obtient le nombre de pas effectués par l'apprenti.
     *
     * @return Le nombre de pas.
     */
    public int getNbPas() {
        return this.nbPas;
    }

    /**
     * Incrémente le compteur de pas de l'apprenti.
     */
    public void incrementerNbPas() {
        this.nbPas++;
    }

    /**
     * Réinitialise le compteur de pas de l'apprenti.
     */
    public void resetNbPas() {
        this.nbPas = 0;
    }

    /**
     * Obtient la position actuelle de l'apprenti.
     *
     * @return La position actuelle de l'apprenti.
     */
    public Position getPosition() {
        return new Position(abscisse, ordonnee);
    }

    /**
     * Obtient la couleur du cristal porté par l'apprenti.
     *
     * @return La couleur du cristal porté.
     */
    public int getCarriedCrystalColor() {
        return this.carriedCrystalColor;
    }

    /**
     * Définit la position de l'apprenti.
     *
     * @param abscisse La nouvelle position x.
     * @param ordonnee La nouvelle position y.
     */
    public void setPosition(int abscisse, int ordonnee) {
        this.abscisse = abscisse;
        this.ordonnee = ordonnee;
    }

    /**
     * Définit la couleur du cristal porté par l'apprenti.
     *
     * @param color La couleur du cristal.
     */
    public void setCarriedCrystalColor(int color) {
        this.carriedCrystalColor = color;
    }
}
