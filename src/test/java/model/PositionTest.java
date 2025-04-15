package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * Classe de test pour la classe Position.
 */
class PositionTest {

    /**
     * Teste le constructeur de la classe Position.
     */
    @Test
    void testPositionConstructor() {
        Position position = new Position(5, 10);
        assertEquals(5, position.getAbscisse(), "La coordonnée x devrait être 5");
        assertEquals(10, position.getOrdonnee(), "La coordonnée y devrait être 10");
    }

    /**
     * Teste la méthode getAbscisse().
     */
    @Test
    void testGetAbscisse() {
        Position position = new Position(5, 10);
        assertEquals(5, position.getAbscisse(), "La coordonnée x devrait être 5");

    }

    /**
     * Teste la méthode getOrdonnee().
     */
    @Test
    void testGetOrdonnee() {
        Position position = new Position(5, 10);
        assertEquals(10, position.getOrdonnee(), "La coordonnée y devrait être 10");
    }

    /**
     * Teste la méthode toString().
     */
    @Test
    void testToString() {
        Position position = new Position(5, 10);
        String expected = "Position(x=5, y=10)";
        assertEquals(expected, position.toString(), "La méthode toString devrait retourner 'Position(x=5, y=10)'");
    }
}
