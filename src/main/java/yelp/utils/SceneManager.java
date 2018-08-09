package yelp.utils;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

public class SceneManager {

  /**
   * The logger.
   */
  private final Logger logger = Logger.getLogger(getClass().getName());
  private Executor threadPool;

  public SceneManager() {
    // Start a new thread pool
    threadPool = Executors.newCachedThreadPool(runnable -> {
      Thread t = new Thread(runnable);
      t.setDaemon(true);
      return t;
    });
  }

  /**
   * With the given fxml resource name, load it into the root pane.
   *
   * @param currentController calling object's controller
   * @param resourceName name of fxml file from fxml folder in resources
   * @param root the root pane
   */
  public void loadAndSwitchToFXML(Object currentController, String resourceName, Pane root) {
    Task<Pane> jfxTask = new Task<Pane>() {
      @Override
      protected Pane call() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(
            currentController.getClass().getResource(String.format("/fxml/%s.fxml", resourceName)));
        return fxmlLoader.load();
      }
    };

    // On success or failure, clear the root pane first. (needs to be on ui thread)
    jfxTask.setOnSucceeded(event -> {
      root.getChildren().clear();
      root.getChildren().add(jfxTask.getValue());
    });

    jfxTask.setOnFailed(event -> {
      root.getChildren().clear();
      logger.log(Level.SEVERE, "", jfxTask.getException());
      logger.log(Level.SEVERE, "", event);
    });

    threadPool.execute(jfxTask);
  }

}
