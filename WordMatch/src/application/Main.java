package application;

import application.model.GameModel;
import application.view.GameView;
import application.controller.GameController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * La clase Main es el punto de entrada de la aplicación JavaFX.
 */
public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        // Inicializa el modelo, la vista y el controlador
        GameModel model = new GameModel();
        GameView view = new GameView();
        GameController controller = new GameController(model, view);

        // Crea la escena y la muestra
        Scene scene = view.createScene();
        primaryStage.setTitle("WordMatch Game");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
