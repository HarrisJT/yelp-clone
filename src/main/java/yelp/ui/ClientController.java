package yelp.ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import yelp.controller.BusinessController;
import yelp.model.Business;
import yelp.utils.SceneManager;

public class ClientController extends StackPane {
  /**
   * The logger.
   */
  private final Logger logger = Logger.getLogger(getClass().getName());

  private FXMLLoader loader;
  private SceneManager sceneManager = null;
  // java.util.concurrent.Executor typically provides a pool of threads...
  private Executor threadPool;

  // ---- FXML  ----
  @FXML
  private BorderPane borderPane;
  @FXML
  private AnchorPane anchorPane;
  @FXML
  private ProgressIndicator progressIndicator;
  @FXML
  private TextField searchBar;
  @FXML
  private Button searchButton;
  @FXML
  private Button homeButton;
  @FXML
  private MenuItem menuLoginButton;
  @FXML
  private MenuItem menuSignUpButton;

  public ClientController() {
    sceneManager = new SceneManager();

    // create executor that uses daemon threads:
    threadPool = Executors.newCachedThreadPool(runnable -> {
      Thread t = new Thread(runnable);
      t.setDaemon(true);
      return t;
    });

    // ---- FXML LOADER ----
    loader = new FXMLLoader(getClass().getResource("/fxml/templates/client.fxml"));
    loader.setController(this);
    loader.setRoot(this);

    try {
      loader.load();
      // Load the home screen
      sceneManager.loadAndSwitchToFXML(loader.getController(), "home", anchorPane);
    } catch (IOException ex) {
      logger.log(Level.SEVERE, "", ex);
    }
  }

  /**
   * Called as soon as .fxml is initialized
   */
  @FXML
  private void initialize() {
    logger.log(Level.INFO, "ClientController Initialized");


    // Search Bar
    searchBar.setOnAction(a -> loadResults(searchBar.getText()));

    searchBar.focusedProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue) {
        Platform.runLater(() -> searchBar.selectAll());
      }
    });

    //Search Button
    searchButton.setOnAction(searchBar.getOnAction());

    // Home Button
    homeButton.setOnAction(a -> {
      try {
        sceneManager.loadAndSwitchToFXML(loader.getController(), "home", anchorPane);
      } catch (IOException e) {
        e.printStackTrace();
      }
    });

    // Account Settings Menu
    menuLoginButton.setOnAction(a -> {
      try {
        sceneManager.loadAndSwitchToFXML(loader.getController(), "login", anchorPane);
      } catch (IOException e) {
        e.printStackTrace();
      }
    });
    menuSignUpButton.setOnAction(a -> {
      try {
        sceneManager.loadAndSwitchToFXML(loader.getController(), "signUp", anchorPane);
      } catch (IOException e) {
        e.printStackTrace();
      }
    });
  }

  private void loadResults(final String searchText) {
    Task<ArrayList<Business>> businessSearchTask = new Task<ArrayList<Business>>() {
      @Override
      public ArrayList<Business> call() throws Exception {
        return BusinessController.getBusinessByName(searchText);
      }
    };

    businessSearchTask.setOnSucceeded(e -> {
      SearchResultsController searchResultsController = new SearchResultsController(searchText,
          businessSearchTask.getValue());

      FXMLLoader searchResultsLoader = new FXMLLoader();
      searchResultsLoader.setController(searchResultsController);
      searchResultsLoader.setLocation(getClass().getResource("/fxml/searchResults.fxml"));

      anchorPane.getChildren().clear();
      try {
        anchorPane.getChildren().add(searchResultsLoader.load());
      } catch (IOException ex) {
        ex.printStackTrace();
      }
    });

    threadPool.execute(businessSearchTask);
  }
}
