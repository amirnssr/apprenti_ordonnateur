package model;

import vue.ConstanteCarte;

/**
 * La classe Temple représente un temple sur la carte avec ses coordonnées,
 * sa couleur et la couleur du cristal qu'il contient.
 */
public class Temple {

    public int x, y; // Coordonnées du temple
    public int couleurTemple; // Couleur du temple
    public int couleurCristal; // Couleur du cristal

    /**
     * Constructeur de la classe Temple.
     *
     * @param x              La coordonnée x du temple.
     * @param y              La coordonnée y du temple.
     * @param couleurTemple  La couleur du temple.
     * @param couleurCristal La couleur du cristal dans le temple.
     */
    public Temple(int x, int y, int couleurTemple, int couleurCristal) {
        this.x = x;
        this.y = y;
        this.couleurTemple = couleurTemple;
        this.couleurCristal = couleurCristal;
    }

    // Getters

    /**
     * Obtient la position du temple.
     *
     * @return La position du temple.
     */
    public Position getPosition() {
        return new Position(x, y);
    }

    /**
     * Obtient la couleur du temple.
     *
     * @return La couleur du temple.
     */
    public int getCouleurTemple() {
        return couleurTemple;
    }

    /**
     * Obtient la couleur du cristal dans le temple.
     *
     * @return La couleur du cristal.
     */
    public int getCouleurCristal() {
        return couleurCristal;
    }

    /**
     * Obtient la coordonnée x du temple.
     *
     * @return La coordonnée x.
     */
    public int getX() {
        return x;
    }

    /**
     * Obtient la coordonnée y du temple.
     *
     * @return La coordonnée y.
     */
    public int getY() {
        return -y;
    }

    /**
     * Définit une nouvelle couleur pour le cristal dans le temple.
     *
     * @param nouvelleCouleurCristal La nouvelle couleur du cristal.
     */
    public void setCouleurCristal(int nouvelleCouleurCristal) {
        this.couleurCristal = nouvelleCouleurCristal;
    }

    /**
     * Retourne une représentation sous forme de chaîne du temple.
     *
     * @return La chaîne représentant le temple.
     */
    @Override
    public String toString() {
        String templeColorName = getColorName(couleurTemple);
        String cristalColorName = getColorName(couleurCristal);
        return String.format("Temple(Position(%d,%d), Couleur: %s, Cristal: %s)", x, y, templeColorName, cristalColorName);
    }

    /**
     * Obtient le nom de la couleur en fonction de son identifiant.
     *
     * @param colorId L'identifiant de la couleur.
     * @return Le nom de la couleur.
     */
    public String getColorName(int colorId) {
        if (colorId > 0 && colorId <= ConstanteCarte.TEMPLE_COLOR_NAMES.length) {
            return ConstanteCarte.TEMPLE_COLOR_NAMES[colorId - 1];
        } else {
            return "Aucun";
        }
    }
}
