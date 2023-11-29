package application.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

// Clase GameModel que actúa como el modelo en el patrón MVC para la aplicación
public class GameModel {
    // Listas observables para almacenar pares de palabras en diferentes categorías
    private ObservableList<WordPair> animals; // Lista para animales
    private ObservableList<WordPair> professions; // Lista para profesiones
    private ObservableList<WordPair> places; // Lista para lugares

    // Constructor de GameModel
    public GameModel() {
        // Inicialización de la lista de animales con pares de palabras
        animals = FXCollections.observableArrayList(
            new WordPair("Gato", "Cat", "kat"),
            new WordPair("Perro", "Dog", "dɔɡ"),
            new WordPair("Pato", "Duck", "dʌk"),
            new WordPair("Pez", "Fish", "fɪʃ"),
            new WordPair("Rana", "Frog", "frɔɡ")
        );

        // Inicialización de la lista de profesiones con pares de palabras
        professions = FXCollections.observableArrayList(
            new WordPair("Doctor", "Doctor", "ˈdɒktər"),
            new WordPair("Profesor", "Teacher", "ˈtiːtʃər"),
            new WordPair("Policía", "Police", "pəˈliːs"),
            new WordPair("Cocinero", "Cook", "kʊk"),
            new WordPair("Ingeniero", "Engineer", "ˌendʒɪˈnɪər")
        );

        // Inicialización de la lista de lugares con pares de palabras
        places = FXCollections.observableArrayList(
            new WordPair("Ciudad", "City", "ˈsɪti"),
            new WordPair("País", "Country", "ˈkʌntri"),
            new WordPair("Parque", "Park", "pɑːrk"),
            new WordPair("Restaurante", "Restaurant", "ˈrɛstrɒnt"),
            new WordPair("Escuela", "School", "skuːl")
        );
    }

    // Método para obtener palabras basadas en el tema seleccionado
    public ObservableList<WordPair> getWordsByTheme(String theme) {
        switch (theme) {
            case "Animales":
                return animals;
            case "Profesiones":
                return professions;
            case "Lugares":
                return places;
            default:
                return FXCollections.observableArrayList(); // Retorna una lista vacía si no coincide ningún tema
        }
    }

    // Clase interna WordPair para almacenar pares de palabras en español e inglés y su pronunciación
    public static class WordPair {
        private String spanishWord; // Palabra en español
        private String englishWord; // Palabra correspondiente en inglés
        private String pronunciation; // Pronunciación de la palabra en inglés

        // Constructor de WordPair
        public WordPair(String spanishWord, String englishWord, String pronunciation) {
            this.spanishWord = spanishWord;
            this.englishWord = englishWord;
            this.pronunciation = pronunciation;
        }

        // Getters para los campos de WordPair
        public String getSpanishWord() { return spanishWord; }
        public String getEnglishWord() { return englishWord; }
        public String getPronunciation() { return pronunciation; }
    }
}
