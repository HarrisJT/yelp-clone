package yelp.utils;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class SceneManager {

  private static final ExecutorService threadPool = Executors.newCachedThreadPool();
  private static SceneManager instance = null;
  /**
   * The logger.
   */
  private final Logger logger = Logger.getLogger(getClass().getName());

  public static SceneManager getInstance() {
    if (instance == null) {
      instance = new SceneManager();
    }
    return instance;
  }

  public void loadAndSwitchToFXML(Object currentController, String resourceName, Pane root) {

    StackPane stack = new StackPane();

    ProgressBar indicator = new ProgressBar();
    indicator.setPrefWidth(350);
    indicator.setVisible(false);

    Label label = new Label();
    label.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
    label.setAlignment(Pos.CENTER);
    label.setStyle("-fx-font-weight:bold; -fx-text-fill: #202020; -fx-font-size:10;");
    label.setText("Loading...");

    stack.getChildren().addAll(indicator, label);
    stack.setManaged(false);
    stack.setVisible(false);

    indicator.visibleProperty().addListener(e -> {
      if (indicator.isVisible()) {
        stack.setManaged(true);
        stack.setVisible(true);
      } else {
        stack.setManaged(false);
        stack.setVisible(false);
      }
    });

    // doesn't currently do anything as it centers to a frame as big as itself.
    stack.setAlignment(Pos.CENTER);
    root.getChildren().add(stack);

    Task<Pane> jfxTask = new Task<Pane>() {
      @Override
      protected Pane call() throws IOException {
        new Thread(() -> {
          Platform.runLater(() -> indicator.progressProperty().bind(this.progressProperty()));
          indicator.setVisible(true);
        }).start();

        // remove after testing
//        try {
//          Thread.sleep(2000);
//        } catch (InterruptedException e) {
//          e.printStackTrace();
//        }

        FXMLLoader fxmlLoader = new FXMLLoader(
            currentController.getClass().getResource(String.format("/fxml/%s.fxml", resourceName)));

        return fxmlLoader.load();
      }
    };

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
