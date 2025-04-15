package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Classe de test pour la classe Apprenti.
 */
class ApprentiTest {
    private Apprenti apprenti;

    /**
     * Méthode d'initialisation exécutée avant chaque test.
     */
    @BeforeEach
    void setUp() {
        apprenti = new Apprenti();
    }

    /**
     * Teste la méthode getAbscisse().
     */
    @Test
    void testGetAbscisse() {
        assertEquals(16, apprenti.getAbscisse(), "La position x initiale devrait être 16");
    }

    /**
     * Teste la méthode getOrdonnee().
     */
    @Test
    void testGetOrdonnee() {
        assertEquals(16, apprenti.getOrdonnee(), "La position y initiale devrait être 16");
    }

    /**
     * Teste la méthode getNbPas().
     */
    @Test
    void testGetNbPas() {
        assertEquals(0, apprenti.getNbPas(), "Le nombre de pas initial devrait être 0");
    }

    /**
     * Teste la méthode incrementerNbPas().
     */
    @Test
    void testIncrementerNbPas() {
        apprenti.incrementerNbPas();
        assertEquals(1, apprenti.getNbPas(), "Le nombre de pas devrait être incrémenté à 1");
    }

    /**
     * Teste la méthode resetNbPas().
     */
    @Test
    void testResetNbPas() {
        apprenti.incrementerNbPas();
        apprenti.resetNbPas();
        assertEquals(0, apprenti.getNbPas(), "Le nombre de pas devrait être réinitialisé à 0");
    }

    /**
     * Teste la méthode getPosition().
     */
    @Test
    void testGetPosition() {
        Position expectedPosition = new Position(16, 16);
        assertEquals(expectedPosition.getAbscisse(), apprenti.getPosition().getAbscisse(), "La coordonnée x devrait être 16");
        assertEquals(expectedPosition.getOrdonnee(), apprenti.getPosition().getOrdonnee(), "La coordonnée y devrait être 16");
    }

    /**
     * Teste la méthode getCarriedCrystalColor().
     */
    @Test
    void testGetCarriedCrystalColor() {
        assertEquals(0, apprenti.getCarriedCrystalColor(), "La couleur de cristal portée par défaut devrait être 0");
    }

    /**
     * Teste la méthode setPosition().
     */
    @Test
    void testSetPosition() {
        apprenti.setPosition(5, 10);
        assertEquals(5, apprenti.getAbscisse(), "La nouvelle position x devrait être 5");
        assertEquals(10, apprenti.getOrdonnee(), "La nouvelle position y devrait être 10");
    }

    /**
     * Teste la méthode setCarriedCrystalColor().
     */
    @Test
    void testSetCarriedCrystalColor() {
        apprenti.setCarriedCrystalColor(3);
        assertEquals(3, apprenti.getCarriedCrystalColor(), "La nouvelle couleur du cristal porté devrait être 3");
    }

    /**
     * Teste la méthode deplacerPasAPas().
     */
    @Test
    void testDeplacerPasAPas() {
        apprenti.setPosition(0, 0);
        apprenti.deplacerPasAPas(3, 4);
        assertEquals(1, apprenti.getAbscisse(), "L'apprenti devrait se déplacer de 1 vers x=1");
        assertEquals(0, apprenti.getOrdonnee(), "L'apprenti devrait se déplacer de 1 vers y=0");
    }
}
