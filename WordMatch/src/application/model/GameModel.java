package application.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * La clase GameModel maneja los datos del juego, incluyendo las palabras y la puntuación.
 */
public class GameModel {
    // Lista observable de pares de palabras inglés-español.
    private ObservableList<WordPair> wordPairs;
    // Puntuación actual del juego.
    private int score;

    /**
     * Constructor de GameModel que inicializa la lista de palabras y establece la puntuación inicial.
     */
    public GameModel() {
        // Inicialización de la lista de pares de palabras.
        wordPairs = FXCollections.observableArrayList(
            new WordPair("House", "Casa"),
            new WordPair("Dog", "Perro"),
            new WordPair("Car", "Coche")
        );
        // Inicialización de la puntuación a 0.
        score = 0;
    }

    /**
     * Obtiene la lista observable de pares de palabras.
     * @return Una lista observable de WordPair.
     */
    public ObservableList<WordPair> getWordPairs() {
        return wordPairs;
    }

    /**
     * Obtiene la puntuación actual.
     * @return Puntuación actual como entero.
     */
    public int getScore() {
        return score;
    }

    /**
     * Incrementa la puntuación en 1.
     */
    public void correctPair() {
        score++;
    }

    /**
     * Decrementa la puntuación en 1, permitiendo puntuaciones negativas.
     */
    public void incorrectPair() {
        score--;
    }

    /**
     * Elimina un par de palabras de la lista de pares.
     * @param pair El par de palabras a eliminar.
     */
    public void removeWordPair(WordPair pair) {
        wordPairs.remove(pair);
    }

    /**
     * Clase interna para manejar pares de palabras.
     */
    public static class WordPair {
        private final String englishWord;
        private final String spanishWord;

        public WordPair(String englishWord, String spanishWord) {
            this.englishWord = englishWord;
            this.spanishWord = spanishWord;
        }

        public String getEnglishWord() {
            return englishWord;
        }

        public String getSpanishWord() {
            return spanishWord;
        }
    }
}
