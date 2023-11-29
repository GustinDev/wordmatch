package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import application.model.GameModel;
import application.view.GameView;
import application.controller.GameController;

//Clase principal que inicia la aplicación JavaFX
public class Main extends Application {
	// Método start: punto de entrada para aplicaciones JavaFX
    @Override
    public void start(Stage primaryStage) {
    	// Creación del modelo, vista y controlador
        GameModel model = new GameModel();
        GameView view = new GameView();
        GameController controller = new GameController(model, view);
     // Creación y configuración de la escena principal
        Scene scene = view.createScene();
     // Agregar hoja de estilos CSS a la escena
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
     // Configurar y mostrar el escenario principal
        primaryStage.setTitle("WordMatch Game");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
 // Método main: punto de entrada para aplicaciones Java
    public static void main(String[] args) {
        launch(args);
    }
}