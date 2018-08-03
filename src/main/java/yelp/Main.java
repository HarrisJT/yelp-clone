package main.java.yelp;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import yelp.ui.ClientController;

public class Main extends Application {

  /**
   * The entry point of the application.
   *
   * @param args the input arguments
   */
  public static void main(final String[] args) {
    launch(args);
  }

  /**
   * Initializes and launches the main screen.
   */
  @Override
  public void start(Stage primaryStage) {
    double visualScreenWidth = Screen.getPrimary().getVisualBounds().getWidth();
    double visualScreenHeight = Screen.getPrimary().getVisualBounds().getHeight();

    //Root
    BorderPane root = new BorderPane();
    root.setCenter(new ClientController());

    //Scene
    Scene scene = new Scene(root, visualScreenWidth * .8, visualScreenHeight * .8);

    primaryStage.setTitle("Revidi - Yelp Clone");
    primaryStage.setScene(scene);
    primaryStage.setOnCloseRequest(cl -> System.exit(0));
    primaryStage.show();
  }
}
