package application.view;

import application.controller.GameController;
import application.model.GameModel.WordPair;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import java.util.Collections;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import java.util.HashMap;
import java.util.Map;

public class GameView {
    private GameController controller;
    private BorderPane layout;
    private VBox topicsBox; // Contenedor para los temas en el modo Aprender
    private VBox wordsBox; // Contenedor para las palabras en el modo Aprender
    private HBox modeButtonsBox; // Contenedor para los botones de modo
    private Text title;
    private String currentMode = "Aprender";
    private Button learnModeButton;
    private Button playModeButton;
    private Text scoreText; // Texto para mostrar la puntuación
    private Map<String, Button> englishCards; // Botones de palabras en inglés en el modo Jugar
    private Map<String, Button> spanishCards; // Botones de palabras en español en el modo Jugar
 // Constructor de GameView
    public GameView() {
        layout = new BorderPane();
        modeButtonsBox = createModeButtonsBox();
        topicsBox = createTopicsBox();
        wordsBox = new VBox(10);
        title = new Text("WordMatch Game");
        scoreText = new Text("Puntuación: 0");

        // Establecer identificadores para usar con CSS
        layout.setId("layout");
        modeButtonsBox.setId("modeButtonsBox");
        topicsBox.setId("topicsBox");
        wordsBox.setId("wordsBox");
        scoreText.setId("scoreText");

        // Establecer clases de estilo para usar con CSS
        title.getStyleClass().add("title");

        // Configurar la disposición inicial del layout
        layout.setTop(modeButtonsBox);
        layout.setLeft(topicsBox);
        layout.setCenter(wordsBox);
    }
    // Crear el contenedor de botones de modo
    private HBox createModeButtonsBox() {
        HBox box = new HBox(10);
        
        box.setPadding(new Insets(15));
        learnModeButton = new Button("Aprender");
        playModeButton = new Button("Jugar");

        // Establecer clases de estilo para usar con CSS
        learnModeButton.getStyleClass().add("mode-button");
        playModeButton.getStyleClass().add("mode-button");

        learnModeButton.setOnAction(e -> controller.onModeSelected("Aprender"));
        playModeButton.setOnAction(e -> controller.onModeSelected("Jugar"));

        box.getChildren().addAll(learnModeButton, playModeButton);
        return box;
    }
 // Crear el contenedor de temas
    private VBox createTopicsBox() {
        VBox box = new VBox(10);
        box.setPadding(new Insets(15));
        ToggleGroup group = new ToggleGroup();

        RadioButton rbAnimales = createRadioButton("Animales", group);
        RadioButton rbProfesiones = createRadioButton("Profesiones", group);
        RadioButton rbLugares = createRadioButton("Lugares", group);

        box.getChildren().addAll(new Text("TEMAS"), rbAnimales, rbProfesiones, rbLugares);
        return box;
    }
 // Crear un botón de radio para un tema
    private RadioButton createRadioButton(String text, ToggleGroup group) {
        RadioButton radioButton = new RadioButton(text);
        radioButton.setToggleGroup(group);
        radioButton.setOnAction(e -> controller.onThemeSelected(text));
        if ("Animales".equals(text)) {
            radioButton.setSelected(true);
        }
        return radioButton;
    }
    // Asignar el controlador a la vista
    public void setController(GameController controller) {
        this.controller = controller;
    }
 // Crear la escena para la aplicación
    public Scene createScene() {
        return new Scene(layout, 800, 600);
    }
 // Actualizar la lista de palabras en la vista
    public void updateWordLists(ObservableList<WordPair> wordPairs, String theme) {
        wordsBox.getChildren().clear();
        title.setText(theme);
        wordsBox.getChildren().add(title);
        for (WordPair pair : wordPairs) {
            Label label = new Label(pair.getSpanishWord() + " - " + pair.getEnglishWord() + " (" + pair.getPronunciation() + ")");
            wordsBox.getChildren().add(label);
        }
    }
 // Actualizar la selección de tema en la vista
    public void updateThemeSelection(String theme) {
        for (Node node : topicsBox.getChildren()) {
            if (node instanceof RadioButton) {
                RadioButton radioButton = (RadioButton) node;
                radioButton.setSelected(radioButton.getText().equals(theme));
            }
        }
    }
    // Actualizar la vista según el modo seleccionado (Aprender o Jugar)
    public void updateMode(String mode) {
        currentMode = mode;
        learnModeButton.setDisable("Aprender".equals(mode));
        playModeButton.setDisable("Jugar".equals(mode));

        if ("Aprender".equals(mode)) {
            layout.setTop(modeButtonsBox);
            layout.setCenter(wordsBox);
            layout.setBottom(null);
            updateWordLists(controller.getModel().getWordsByTheme(controller.getCurrentTheme()), controller.getCurrentTheme());
        } else if ("Jugar".equals(mode)) {
            scoreText.setText("Puntuación: 0");
            VBox playModeTopBox = new VBox(10);
            playModeTopBox.setAlignment(Pos.CENTER);
            playModeTopBox.getChildren().addAll(modeButtonsBox, scoreText);
            layout.setTop(playModeTopBox);
            setupPlayMode(controller.getCurrentTheme());
        }
    }

    // Obtener el modo actual
    public String getCurrentMode() {
        return currentMode;
    }
    // Configurar la vista para el modo de juego (Jugar)
    public void setupPlayMode(String theme) {
        ObservableList<WordPair> words = controller.getModel().getWordsByTheme(theme);
        ObservableList<String> englishWords = FXCollections.observableArrayList();
        ObservableList<String> spanishWords = FXCollections.observableArrayList();

        for (WordPair pair : words) {
            englishWords.add(pair.getEnglishWord());
            spanishWords.add(pair.getSpanishWord());
        }

        Collections.shuffle(englishWords);
        Collections.shuffle(spanishWords);

        englishCards = new HashMap<>();
        spanishCards = new HashMap<>();

        VBox englishBox = new VBox(10);
        VBox spanishBox = new VBox(10);

        for (String word : englishWords) {
            Button card = new Button(word);
            card.setOnAction(e -> controller.onCardSelected(word, "English"));
            card.getStyleClass().add("card-button");
            englishCards.put(word, card);
            englishBox.getChildren().add(card);
        }

        for (String word : spanishWords) {
            Button card = new Button(word);
            card.setOnAction(e -> controller.onCardSelected(word, "Spanish"));
            card.getStyleClass().add("card-button");
            spanishCards.put(word, card);
            spanishBox.getChildren().add(card);
        }

        HBox cardsBox = new HBox(20);
        cardsBox.setAlignment(Pos.CENTER);
        cardsBox.getChildren().addAll(englishBox, spanishBox);

        // Clear any previous setup and display the new cards for the Play mode
        wordsBox.getChildren().clear();
        wordsBox.getChildren().add(cardsBox);
    }
 // Actualizar la puntuación en la vista
    public void updateScore(int score) {
        scoreText.setText("Puntuación: " + score);
    }
 // Quitar las tarjetas seleccionadas de la vista
    public void removeCards(String englishWord, String spanishWord) {
        Button englishCard = englishCards.remove(englishWord);
        Button spanishCard = spanishCards.remove(spanishWord);

        if (englishCard != null) {
            englishCard.setDisable(true);
        }
        if (spanishCard != null) {
            spanishCard.setDisable(true);
        }
    }
 // Verificar si todas las tarjetas han sido removidas
    public boolean allCardsRemoved() {
        return englishCards.values().stream().allMatch(Button::isDisabled) &&
               spanishCards.values().stream().allMatch(Button::isDisabled);
    }
 // Mostrar un mensaje al final del juego
    public void showEndGameMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message, ButtonType.OK);
        alert.setHeaderText(null); // You can set header text or leave it null for no header
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                controller.resetGame();
            }
        });
    }
    
  
}
