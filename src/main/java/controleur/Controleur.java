package controleur;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import model.Apprenti;
import model.Position;
import model.Temple;
import vue.VBoxCanvas;
import vue.VBoxRoot;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import static vue.ConstanteCarte.NB_DE_CASE_HAUTEUR;
import static vue.ConstanteCarte.NB_DE_CASE_LARGEUR;

/**
 * Le contrôleur gère les interactions entre l'interface utilisateur et les actions de l'apprenti.
 */
public class Controleur implements EventHandler<ActionEvent> {
    public VBoxRoot root;
    public VBoxCanvas canvas;
    public Apprenti apprenti;
    public AnimationTimer animationTimer;
    public List<Temple> temples;
    public boolean suppressAlerts = false;
    public boolean gameEndAlertDisplayed = false; // Variable de contrôle pour vérifier si l'alerte de fin de jeu a été affichée

    /**
     * Constructeur du contrôleur.
     *
     * @param root La racine de l'interface utilisateur.
     */
    public Controleur(VBoxRoot root) {
        this.root = root;
        this.apprenti = new Apprenti();
        this.temples = new ArrayList<>();
    }

    /**
     * Gère les événements d'action, comme le chargement d'un scénario.
     *
     * @param event L'événement d'action.
     */
    @Override
    public void handle(ActionEvent event) {
        MenuItem source = (MenuItem) event.getSource();
        File scenarioFile = (File) source.getUserData();
        temples = loadTemples(scenarioFile);

        // Réinitialiser l'apprenti
        apprenti.setPosition(NB_DE_CASE_LARGEUR / 2, NB_DE_CASE_HAUTEUR / 2);
        apprenti.resetNbPas();
        apprenti.setCarriedCrystalColor(0);

        // Redessiner le canvas avec le nouveau scénario
        canvas = new VBoxCanvas(this);
        canvas.drawTemples(temples);
        root.setVBoxCanvas(canvas);
        root.updateDisplay();

        canvas.addEventHandler(VBoxCanvas.CanvasClickEvent.CANVAS_CLICK, e -> handleMouseClick(e.getAbscisse(), e.getOrdonnee()));
        resetStepCount(); // Réinitialiser le nombre de pas
        updateTempleInfo(); // Mise à jour des infos des temples
        gameEndAlertDisplayed = false; // Réinitialiser l'alerte de fin de jeu
    }

    /**
     * Charge les temples à partir d'un fichier.
     *
     * @param file Le fichier contenant les données des temples.
     * @return La liste des temples.
     */
    public List<Temple> loadTemples(File file) {
        List<Temple> temples = new ArrayList<>();
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String[] parts = scanner.nextLine().trim().split("\\s+");
                int x = Integer.parseInt(parts[0]);
                int y = Integer.parseInt(parts[1]);
                int templeColor = Integer.parseInt(parts[2]);
                int crystalColor = Integer.parseInt(parts[3]);
                temples.add(new Temple(x, y, templeColor, crystalColor));
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + file.getPath());
        }
        return temples;
    }

    /**
     * Gère les clics de souris sur le canvas.
     *
     * @param xCible La position x cible.
     * @param yCible La position y cible.
     */
    public void handleMouseClick(int xCible, int yCible) {
        startMovingTo(xCible, yCible);
    }

    /**
     * Démarre le déplacement de l'apprenti vers une cible.
     *
     * @param xCible La position x cible.
     * @param yCible La position y cible.
     */
    public void startMovingTo(int xCible, int yCible) {
        if (animationTimer != null) {
            animationTimer.stop();
        }

        animationTimer = new AnimationTimer() {
            public void handle(long now) {
                if (apprenti.getAbscisse() != xCible || apprenti.getOrdonnee() != yCible) {
                    apprenti.deplacerPasAPas(xCible, yCible);
                    canvas.redrawCanvas();
                    canvas.setPlayerPositionLabel(apprenti.getAbscisse(), apprenti.getOrdonnee()); // Mise à jour de la position
                    updateTempleInfo(); // Mise à jour des infos des temples
                } else {
                    canvas.setStepCount(apprenti.getNbPas());
                    stop();
                }
            }
        };
        animationTimer.start();
    }

    /**
     * L'apprenti prend un cristal d'un temple.
     *
     * @param temple Le temple où prendre le cristal.
     */
    public void prendreCristal(Temple temple) {
        if (temple != null && temple.getCouleurCristal() != 0 && apprenti.getCarriedCrystalColor() == 0) {
            apprenti.setCarriedCrystalColor(temple.getCouleurCristal());
            temple.setCouleurCristal(0);
            canvas.setCarriedCrystalLabel(apprenti.getCarriedCrystalColor());
            canvas.redrawCanvas();
            updateTempleInfo(); // Mise à jour des infos des temples
            checkGameEnd();
        } else if (!suppressAlerts) {
            showAlert("Impossible de prendre", "Aucun cristal disponible pour prendre.");
        }
    }

    /**
     * L'apprenti échange les cristaux avec un temple.
     *
     * @param temple Le temple avec lequel échanger les cristaux.
     */
    public void echangerCristaux(Temple temple) {
        if (temple != null && apprenti.getCarriedCrystalColor() != 0 && temple.getCouleurCristal() != 0) {
            int tempCrystalColor = temple.getCouleurCristal();
            temple.setCouleurCristal(apprenti.getCarriedCrystalColor());
            apprenti.setCarriedCrystalColor(tempCrystalColor);
            canvas.setCarriedCrystalLabel(apprenti.getCarriedCrystalColor());
            canvas.redrawCanvas();
            updateTempleInfo(); // Mise à jour des infos des temples
            checkGameEnd();
        } else if (!suppressAlerts) {
            showAlert("Impossible d'échanger", "Aucun échange possible ici.");
        }
    }

    /**
     * L'apprenti dépose un cristal dans un temple.
     *
     * @param temple Le temple où déposer le cristal.
     */
    public void deposerCristal(Temple temple) {
        if (temple != null && temple.getCouleurCristal() == 0 && apprenti.getCarriedCrystalColor() != 0) {
            temple.setCouleurCristal(apprenti.getCarriedCrystalColor());
            apprenti.setCarriedCrystalColor(0);
            canvas.setCarriedCrystalLabel(0);
            canvas.redrawCanvas();
            updateTempleInfo(); // Mise à jour des infos des temples
            checkGameEnd();
        } else if (!suppressAlerts) {
            showAlert("Impossible de déposer", "Vous ne pouvez pas déposer de cristal ici.");
        }
    }

    /**
     * Affiche une alerte avec un message.
     *
     * @param header Le titre de l'alerte.
     * @param content Le contenu de l'alerte.
     */
    public void showAlert(String header, String content) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur d'Action");
            alert.setHeaderText(header);
            alert.setContentText(content);
            alert.showAndWait();
        });
    }

    /**
     * Vérifie si le jeu est terminé (tous les cristaux sont alignés).
     */
    public void checkGameEnd() {
        if (areAllCrystalsCorrectlyPlaced() && !gameEndAlertDisplayed) {
            gameEndAlertDisplayed = true; // Marquer que l'alerte a été affichée
            Platform.runLater(() -> {
                Alert gameEndAlert = new Alert(Alert.AlertType.INFORMATION);
                gameEndAlert.setTitle("Partie Terminée");
                gameEndAlert.setHeaderText("Félicitations !");
                gameEndAlert.setContentText("Tous les cristaux sont alignés !");
                gameEndAlert.showAndWait();
                resetStepCount(); // Réinitialiser le nombre de pas
            });
        }
    }

    /**
     * Vérifie si tous les cristaux sont correctement placés.
     *
     * @return true si tous les cristaux sont correctement placés, false sinon.
     */
    public boolean areAllCrystalsCorrectlyPlaced() {
        for (Temple temple : temples) {
            if (temple.getCouleurCristal() != temple.getCouleurTemple()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Mise à jour des informations des temples.
     */
    public void updateTempleInfo() {
        Platform.runLater(() -> canvas.updateTempleInfo());
    }

    // Algorithme de tri (niveau 1)

    /**
     * Trie et réarrange les cristaux en utilisant un algorithme de tri.
     */
    public void trierEtRearrangerCrystaux() {
        alignNextCrystal(1);
    }

    /**
     * Aligne le cristal suivant par couleur.
     *
     * @param color La couleur du cristal à aligner.
     */
    public void alignNextCrystal(int color) {
        if (areAllCrystalsCorrectlyPlaced()) {
            checkGameEnd();
            return;
        }

        if (color > temples.size()) {
            checkGameEnd();
            return;
        }

        Temple startingTemple = findTempleByCrystalColor(color);
        if (startingTemple != null) {
            moveCrystalToCorrectTemple(color, startingTemple, () -> alignNextCrystal(color + 1));
        } else {
            alignNextCrystal(color + 1);
        }
    }

    /**
     * Déplace un cristal vers le bon temple.
     *
     * @param targetColor La couleur cible du temple.
     * @param startingTemple Le temple de départ.
     * @param onFinish Action à exécuter après avoir déplacé le cristal.
     */
    public void moveCrystalToCorrectTemple(int targetColor, Temple startingTemple, Runnable onFinish) {
        moveToWithDelay(startingTemple.getX(), startingTemple.getY(), () -> {
            if (apprenti.getCarriedCrystalColor() == 0) {
                prendreCristal(startingTemple);
            }

            if (areAllCrystalsCorrectlyPlaced()) {
                checkGameEnd();
                return;
            }

            Temple targetTemple = findTempleByColor(targetColor);
            if (targetTemple != null) {
                moveToWithDelay(targetTemple.getX(), targetTemple.getY(), () -> {
                    if (apprenti.getCarriedCrystalColor() != 0 && targetTemple.getCouleurCristal() == 0) {
                        deposerCristal(targetTemple);
                    } else if (apprenti.getCarriedCrystalColor() != 0 && targetTemple.getCouleurCristal() != 0) {
                        echangerCristaux(targetTemple);
                    }

                    int nextTargetColor = apprenti.getCarriedCrystalColor();
                    if (nextTargetColor != 0) {
                        moveToWithDelay(startingTemple.getX(), startingTemple.getY(), () -> {
                            deposerCristal(startingTemple);
                            if (areAllCrystalsCorrectlyPlaced()) {
                                checkGameEnd();
                            } else if (onFinish != null) {
                                onFinish.run();
                            }
                        });
                    } else {
                        if (areAllCrystalsCorrectlyPlaced()) {
                            checkGameEnd();
                        } else if (onFinish != null) {
                            onFinish.run();
                        }
                    }
                });
            }
        });
    }

    // Algorithme heuristique (niveau 2)

    /**
     * Utilise un algorithme heuristique pour trier les cristaux.
     */
    public void optimizedSort() {
        if (areAllCrystalsCorrectlyPlaced()) {
            checkGameEnd();
            return;
        }

        suppressAlerts = true;

        // Collecte des temples qui ne sont pas encore alignés
        List<Temple> unsortedTemples = new ArrayList<>();
        for (Temple temple : temples) {
            if (temple.getCouleurCristal() != 0 && temple.getCouleurCristal() != temple.getCouleurTemple()) {
                unsortedTemples.add(temple);
            }
        }

        // Déplacer l'apprenti en évitant les détours inutiles
        moveToNextTarget(unsortedTemples);
    }

    /**
     * Déplace l'apprenti vers la prochaine cible en fonction de l'algorithme heuristique.
     *
     * @param unsortedTemples Liste des temples non alignés.
     */
    public void moveToNextTarget(List<Temple> unsortedTemples) {
        if (unsortedTemples.isEmpty()) {
            suppressAlerts = false;
            checkGameEnd();
            return;
        }

        if (apprenti.getCarriedCrystalColor() != 0) {
            // Si l'apprenti porte un cristal, il doit se rendre au temple de la couleur correspondante
            Temple targetTemple = findTempleByColor(apprenti.getCarriedCrystalColor());
            moveToWithDelay(targetTemple.getX(), targetTemple.getY(), () -> {
                if (apprenti.getCarriedCrystalColor() != 0 && targetTemple.getCouleurCristal() != 0) {
                    echangerCristaux(targetTemple);
                    updateTempleInfo(); // Mise à jour des infos des temples
                } else {
                    deposerCristal(targetTemple);
                    updateTempleInfo(); // Mise à jour des infos des temples
                }
                moveToNextTarget(unsortedTemples);
            });
        } else {
            // Trouver le temple le plus proche qui a un cristal non aligné
            Temple nextTemple = findNearestTempleWithMisalignedCrystal(unsortedTemples);
            if (nextTemple != null) {
                moveToWithDelay(nextTemple.getX(), nextTemple.getY(), () -> {
                    prendreCristal(nextTemple);
                    updateTempleInfo(); // Mise à jour des infos des temples
                    unsortedTemples.remove(nextTemple);
                    moveToNextTarget(unsortedTemples);
                });
            } else {
                suppressAlerts = false;
                checkGameEnd();
            }
        }
    }

    /**
     * Trouve le temple le plus proche avec un cristal mal aligné.
     *
     * @param temples Liste des temples.
     * @return Le temple le plus proche avec un cristal mal aligné.
     */
    public Temple findNearestTempleWithMisalignedCrystal(List<Temple> temples) {
        // Convertir la liste de temples en un flux (stream)
        return temples.stream()
                // Filtrer pour ne garder que les temples avec un cristal mal aligné
                .filter(t -> t.getCouleurCristal() != 0 && t.getCouleurCristal() != t.getCouleurTemple())
                // Trouver le temple avec la distance de Manhattan minimale par rapport à l'apprenti
                .min(Comparator.comparingInt(t -> getManhattanDistance(apprenti.getPosition(), t.getPosition())))
                // Si aucun temple ne correspond aux critères, retourner null
                .orElse(null);
    }

    /**
     * Calcule la distance de Manhattan entre deux positions.
     *
     * @param pos1 La première position.
     * @param pos2 La deuxième position.
     * @return La distance de Manhattan entre les deux positions.
     */
    public int getManhattanDistance(Position pos1, Position pos2) {
        return Math.abs(pos1.getAbscisse() - pos2.getAbscisse()) + Math.abs(pos1.getOrdonnee() - pos2.getOrdonnee());
    }

    /**
     * Déplace l'apprenti vers une cible avec un délai.
     *
     * @param xCible La position x cible.
     * @param yCible La position y cible.
     * @param onFinish Action à exécuter après avoir atteint la cible.
     */
    public void moveToWithDelay(int xCible, int yCible, Runnable onFinish) {
        if (animationTimer != null) {
            animationTimer.stop();
        }

        animationTimer = new AnimationTimer() {
            private long lastUpdate = 0;

            @Override
            public void handle(long now) {
                // Vérifie si 200 millisecondes se sont écoulées depuis la dernière mise à jour
                if (now - lastUpdate >= 200_000_000) {
                    // Code pour mettre à jour la position de l'apprenti
                    if (apprenti.getAbscisse() != xCible || apprenti.getOrdonnee() != yCible) {
                        apprenti.deplacerPasAPas(xCible, yCible);
                        canvas.redrawCanvas();
                        canvas.setPlayerPositionLabel(apprenti.getAbscisse(), apprenti.getOrdonnee()); // Mise à jour de la position
                        updateTempleInfo(); // Mise à jour des infos des temples
                    } else {
                        canvas.setStepCount(apprenti.getNbPas());
                        stop();
                        if (areAllCrystalsCorrectlyPlaced()) {
                            checkGameEnd();
                        } else if (onFinish != null) {
                            onFinish.run();
                        }
                    }
                    // Met à jour le temps de la dernière mise à jour
                    lastUpdate = now;
                }
            }
        };
        animationTimer.start();
    }

    // Autres méthodes

    /**
     * Retourne l'apprenti.
     *
     * @return L'apprenti.
     */
    public Apprenti getApprenti() {
        return apprenti;
    }

    /**
     * Trouve le temple à la position actuelle de l'apprenti.
     *
     * @return Le temple à la position actuelle de l'apprenti.
     */
    public Temple findTempleAtCurrentPosition() {
        return findTempleByPosition(apprenti.getAbscisse(), apprenti.getOrdonnee());
    }

    /**
     * Trouve un temple par sa position.
     *
     * @param x La position x du temple.
     * @param y La position y du temple.
     * @return Le temple à la position spécifiée.
     */
    public Temple findTempleByPosition(int x, int y) {
        for (Temple temple : temples) {
            if (temple.getX() == x && temple.getY() == y) {
                return temple;
            }
        }
        return null;
    }

    /**
     * Centre l'apprenti au point de départ (0,0).
     */
    public void centerApprenti() {
        apprenti.setPosition(0, 0); // Mettre la position initiale de l'apprenti au centre logique
    }

    /**
     * Réinitialise le nombre de pas de l'apprenti.
     */
    public void resetStepCount() {
        apprenti.resetNbPas();
        canvas.setStepCount(apprenti.getNbPas());
    }

    /**
     * Trouve un temple par la couleur de son cristal.
     *
     * @param color La couleur du cristal.
     * @return Le temple avec la couleur de cristal spécifiée.
     */
    public Temple findTempleByCrystalColor(int color) {
        for (Temple temple : temples) {
            if (temple.getCouleurCristal() == color) {
                return temple;
            }
        }
        return null;
    }

    /**
     * Trouve un temple par sa couleur.
     *
     * @param color La couleur du temple.
     * @return Le temple avec la couleur spécifiée.
     */
    public Temple findTempleByColor(int color) {
        for (Temple temple : temples) {
            if (temple.getCouleurTemple() == color) {
                return temple;
            }
        }
        return null;
    }
}
