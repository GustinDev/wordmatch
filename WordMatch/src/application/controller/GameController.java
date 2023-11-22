package application.controller;

import application.model.GameModel;
import application.view.GameView;
import javafx.collections.ObservableList;
import application.model.GameModel.WordPair;

/**
 * La clase GameController maneja la lógica de interacción entre el modelo y la vista.
 */
public class GameController {
    private GameModel model;
    private GameView view;

    /**
     * Constructor de GameController que establece el modelo y la vista.
     * @param model El modelo del juego.
     * @param view La vista del juego.
     */
    public GameController(GameModel model, GameView view) {
        this.model = model;
        this.view = view;
        this.view.setController(this);
        this.view.updateWordLists(model.getWordPairs());
        this.view.updateScore(model.getScore());

        // Configura los eventos de clic en las listas
        setupListClickHandlers();
    }

    /**
     * Configura los manejadores de eventos de clic para las listas de palabras.
     */
    private void setupListClickHandlers() {
        view.getListEnglish().setOnMouseClicked(e -> {
            int selectedIndex = view.getListEnglish().getSelectionModel().getSelectedIndex();
            if (selectedIndex != -1) {
                WordPair selectedPair = model.getWordPairs().get(selectedIndex);
                checkPair(selectedPair, view.getListSpanish().getSelectionModel().getSelectedItem());
            }
        });

        view.getListSpanish().setOnMouseClicked(e -> {
            int selectedIndex = view.getListSpanish().getSelectionModel().getSelectedIndex();
            if (selectedIndex != -1) {
                WordPair selectedPair = model.getWordPairs().get(selectedIndex);
                checkPair(selectedPair, view.getListEnglish().getSelectionModel().getSelectedItem());
            }
        });
    }

    /**
     * Verifica si las palabras seleccionadas son una pareja correcta.
     * @param selectedPair El par de palabras seleccionado.
     * @param selectedWord La palabra seleccionada en la otra lista.
     */
    private void checkPair(WordPair selectedPair, String selectedWord) {
        if (selectedPair != null && selectedWord != null) {
            if (selectedPair.getEnglishWord().equals(selectedWord) || selectedPair.getSpanishWord().equals(selectedWord)) {
                model.correctPair();
                model.removeWordPair(selectedPair);
            } else {
                model.incorrectPair();
            }
            view.updateWordLists(model.getWordPairs());
            view.updateScore(model.getScore());
            checkEndGame();
        }
    }

    /**
     * Verifica si el juego ha terminado y actualiza la interfaz de usuario con un mensaje de fin de juego.
     */
    private void checkEndGame() {
        ObservableList<WordPair> pairs = model.getWordPairs();
        if (pairs.isEmpty()) {
            boolean hasWon = model.getScore() > 0;
            view.showEndGameMessage(hasWon);
        }
    }
}
