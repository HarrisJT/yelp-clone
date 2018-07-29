package yelp.utils;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneManager {

  private static SceneManager instance = null;

  public static SceneManager getInstance() {
    if (instance == null) {
      instance = new SceneManager();
    }
    return instance;
  }

  public Object loadScene(Object currentController, String resourceName, Node root)
      throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(
        currentController.getClass().getResource(String.format("/templates/%s.fxml", resourceName)));
    Parent anchor = fxmlLoader.load();

    Scene scene = new Scene(anchor);
    ((Stage) (root.getScene().getWindow())).setScene(scene);

    return fxmlLoader.getController();
  }
}
