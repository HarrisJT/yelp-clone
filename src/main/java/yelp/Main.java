package main.java.yelp;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

  private Parent rootNode;

  /**
   * The entry point of the application.
   *
   * @param args the input arguments
   */
  public static void main(final String[] args) {
    Application.launch(args);
  }

  @Override
  public void init() throws Exception {
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/templates/client.fxml"));
    rootNode = fxmlLoader.load();
  }

  /**
   * Initializes and launches the main screen.
   */
  @Override
  public void start(Stage primaryStage) throws Exception {
    primaryStage.setTitle("Revidi - Yelp Clone");
    Scene scene = new Scene(rootNode);
    primaryStage.setResizable(false);
    primaryStage.setScene(scene);
    primaryStage.show();
    primaryStage.setOnCloseRequest(event -> Platform.exit());
  }
}
