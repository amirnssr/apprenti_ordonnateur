package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Classe de test pour la classe Temple.
 */
class TempleTest {
    private Temple temple;

    /**
     * Méthode d'initialisation exécutée avant chaque test.
     */
    @BeforeEach
    void setUp() {
        temple = new Temple(5, 10, 3, 7);
    }

    /**
     * Teste la méthode getPosition().
     */
    @Test
    void testGetPosition() {
        Position expectedPosition = new Position(5, 10);
        assertEquals(expectedPosition.getAbscisse(), temple.getPosition().getAbscisse(), "La coordonnée x devrait être 5");
        assertEquals(expectedPosition.getOrdonnee(), temple.getPosition().getOrdonnee(), "La coordonnée y devrait être 10");
    }

    /**
     * Teste la méthode getCouleurTemple().
     */
    @Test
    void testGetCouleurTemple() {
        assertEquals(3, temple.getCouleurTemple(), "La couleur du temple devrait être 3");
    }

    /**
     * Teste la méthode getCouleurCristal().
     */
    @Test
    void testGetCouleurCristal() {
        assertEquals(7, temple.getCouleurCristal(), "La couleur du cristal devrait être 7");
    }

    /**
     * Teste la méthode getX().
     */
    @Test
    void testGetX() {
        assertEquals(5, temple.getX(), "La coordonnée x devrait être 5");
    }

    /**
     * Teste la méthode getY().
     */
    @Test
    void testGetY() {
        assertEquals(-10, temple.getY(), "La coordonnée y devrait être -10");
    }

    /**
     * Teste la méthode setCouleurCristal().
     */
    @Test
    void testSetCouleurCristal() {
        temple.setCouleurCristal(4);
        assertEquals(4, temple.getCouleurCristal(), "La nouvelle couleur du cristal devrait être 4");
    }

    /**
     * Teste la méthode toString().
     */
    @Test
    void testToString() {
        String expected = "Temple(Position(5,10), Couleur: Jaune, Cristal: Violet)";
        assertEquals(expected, temple.toString(), "La méthode toString devrait retourner 'Temple(Position(5,10), Couleur: Jaune, Cristal: Violet)'");
    }

    /**
     * Teste le constructeur de la classe Temple.
     */
    @Test
    void testTempleConstructor() {
        Temple newTemple = new Temple(2, 8, 1, 6);
        assertEquals(2, newTemple.getX(), "La coordonnée x devrait être 2");
        assertEquals(-8, newTemple.getY(), "La coordonnée y devrait être -8");
        assertEquals(1, newTemple.getCouleurTemple(), "La couleur du temple devrait être 1");
        assertEquals(6, newTemple.getCouleurCristal(), "La couleur du cristal devrait être 6");
    }
}
