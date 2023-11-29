package application.controller;

import application.model.GameModel;
import application.view.GameView;
import javafx.collections.ObservableList;

// Clase GameController que actúa como el controlador en el patrón MVC
public class GameController {
    private GameModel model; // Referencia al modelo del juego
    private GameView view; // Referencia a la vista del juego
    private String currentTheme; // Tema actual seleccionado
    private int score; // Puntuación actual en el juego
    private String selectedCardEnglish; // Palabra seleccionada en inglés
    private String selectedCardSpanish; // Palabra seleccionada en español

    // Constructor de GameController
    public GameController(GameModel model, GameView view) {
        this.model = model;
        this.view = view;
        this.view.setController(this); // Asignar este controlador a la vista
        this.score = 0;
        this.selectedCardEnglish = null;
        this.selectedCardSpanish = null;
        onModeSelected("Aprender"); // Iniciar en modo "Aprender"
    }

    // Obtener el modelo del juego
    public GameModel getModel() {
        return model;
    }

    // Obtener el tema actual
    public String getCurrentTheme() {
        return currentTheme;
    }
    
    // Método llamado cuando se selecciona un modo de juego
    public void onModeSelected(String mode) {
        currentTheme = "Animales"; // Restablecer el tema a "Animales" al cambiar de modo
        view.updateThemeSelection(currentTheme); // Actualizar la selección del tema en la vista
        view.updateMode(mode); // Actualizar el modo en la vista
        if ("Aprender".equals(mode)) {
            onThemeSelected(currentTheme);
        } else {
            score = 0; // Restablecer puntuación al cambiar de modos
            view.updateScore(score);
            preparePlayMode();
        }
    }

    // Método llamado cuando se selecciona un tema
    public void onThemeSelected(String theme) {
        currentTheme = theme;
        if (view.getCurrentMode().equals("Aprender")) {
            ObservableList<GameModel.WordPair> words = model.getWordsByTheme(theme);
            view.updateWordLists(words, theme); // Actualizar la lista de palabras en la vista
        } else {
            score = 0; // Restablecer puntuación al cambiar temas en modo jugar
            view.updateScore(score);
            preparePlayMode();
        }
    }

    // Preparar la vista para el modo de juego
    public void preparePlayMode() {
        String currentTheme = getCurrentTheme();
        view.setupPlayMode(currentTheme);
    }

    // Método llamado cuando se selecciona una carta
    public void onCardSelected(String word, String language) {
        if ("English".equals(language)) {
            selectedCardEnglish = word;
            if (selectedCardSpanish != null) {
                checkPair();
            }
        } else {
            selectedCardSpanish = word;
            if (selectedCardEnglish != null) {
                checkPair();
            }
        }
    }

    // Verificar si las cartas seleccionadas forman un par correcto
    private void checkPair() {
        ObservableList<GameModel.WordPair> pairs = model.getWordsByTheme(currentTheme);
        boolean matchFound = pairs.stream()
                                  .anyMatch(pair -> pair.getEnglishWord().equals(selectedCardEnglish) &&
                                                    pair.getSpanishWord().equals(selectedCardSpanish));
        if (matchFound) {
            score++; // Aumentar la puntuación si las cartas coinciden
            view.removeCards(selectedCardEnglish, selectedCardSpanish); // Remover las cartas coincidentes
        } else {
            score--; // Disminuir la puntuación si las cartas no coinciden
        }
        view.updateScore(score); // Actualizar la puntuación en la vista
        selectedCardEnglish = null;
        selectedCardSpanish = null;
        if (view.allCardsRemoved()) {
            view.showEndGameMessage(score > 0 ? "¡Ganaste!" : "Perdiste, tu puntuación es negativa.");
        }
    }

    // Reiniciar el juego
    public void resetGame() {
        score = 0; // Restablecer la puntuación al reiniciar
        view.updateScore(score);
        preparePlayMode(); // Preparar la vista para un nuevo juego
    }
}
