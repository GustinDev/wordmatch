package application.view;
import javafx.collections.ObservableList;
import javafx.util.Pair;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import application.controller.GameController;
import application.model.GameModel.WordPair;

/**
 * La clase GameView maneja la interfaz de usuario del juego.
 */
public class GameView {
    private GameController controller;
    private VBox layout;
    private ListView<String> listEnglish, listSpanish;
    private Label scoreLabel, endGameLabel;

    /**
     * Constructor de GameView que inicializa la interfaz de usuario.
     */
    public GameView() {
        layout = new VBox(10);
        listEnglish = new ListView<>();
        listSpanish = new ListView<>();
        scoreLabel = new Label("Score: 0");
        endGameLabel = new Label();
        layout.getChildren().addAll(listEnglish, listSpanish, scoreLabel, endGameLabel);
    }

    /**
     * Establece el controlador para la vista.
     * @param controller El controlador del juego.
     */
    public void setController(GameController controller) {
        this.controller = controller;
    }

    /**
     * Crea la escena para la ventana principal del juego.
     * @return La escena creada.
     */
    public Scene createScene() {
        return new Scene(layout, 300, 300);
    }

    /**
     * Actualiza las listas de palabras en la interfaz de usuario.
     * @param wordPairs La lista de pares de palabras a mostrar.
     */
    public void updateWordLists(ObservableList<WordPair> wordPairs) {
        listEnglish.getItems().clear();
        listSpanish.getItems().clear();
        wordPairs.forEach(pair -> {
            listEnglish.getItems().add(pair.getEnglishWord());
            listSpanish.getItems().add(pair.getSpanishWord());
        });
    }

    /**
     * Actualiza la etiqueta de puntuación en la interfaz de usuario.
     * @param score La puntuación actual a mostrar.
     */
    public void updateScore(int score) {
        scoreLabel.setText("Score: " + score);
    }

    /**
     * Muestra un mensaje de fin de juego dependiendo del resultado.
     * @param hasWon Verdadero si el jugador ha ganado, falso si ha perdido.
     */
    public void showEndGameMessage(boolean hasWon) {
        endGameLabel.setText(hasWon ? "¡Ganaste!" : "Perdiste...");
    }

    public ListView<String> getListEnglish() {
        return listEnglish;
    }

    public ListView<String> getListSpanish() {
        return listSpanish;
    }
}
