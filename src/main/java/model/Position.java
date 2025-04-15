package model;

/**
 * La classe Position représente une position sur la carte avec des coordonnées x et y.
 * Elle inclut également la couleur du cristal porté, initialisée à 0 (aucun cristal porté).
 */
public class Position {
    public int x; // Coordonnée x
    public int y; // Coordonnée y
    public int carriedCrystalColor; // Couleur du cristal porté (0 = aucun cristal)

    /**
     * Constructeur de la position.
     *
     * @param x La coordonnée x.
     * @param y La coordonnée y.
     */
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
        this.carriedCrystalColor = 0; // 0 = aucun cristal porté
    }

    // Getters

    /**
     * Obtient la coordonnée x.
     *
     * @return La coordonnée x.
     */
    public int getAbscisse() {
        return this.x;
    }

    /**
     * Obtient la coordonnée y.
     *
     * @return La coordonnée y.
     */
    public int getOrdonnee() {
        return this.y;
    }

    /**
     * Retourne une représentation sous forme de chaîne de la position.
     *
     * @return La chaîne représentant la position.
     */
    @Override
    public String toString() {
        return String.format("Position(x=%d, y=%d)", x, y);
    }
}
