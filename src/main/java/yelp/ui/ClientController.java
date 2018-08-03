package yelp.ui;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import yelp.utils.SceneManager;

public class ClientController extends StackPane {

  /**
   * The logger.
   */
  private final Logger logger = Logger.getLogger(getClass().getName());

  private FXMLLoader loader;
  private SceneManager sceneManager = null;

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
    // ---- FXML LOADER ----
    loader = new FXMLLoader(getClass().getResource("/fxml/templates/client.fxml"));
    loader.setController(this);
    loader.setRoot(this);

    try {
      loader.load();
    } catch (IOException ex) {
      logger.log(Level.SEVERE, "", ex);
    }

    // Load the home screen
    SceneManager.getInstance()
        .loadAndSwitchToFXML(loader.getController(), "home", anchorPane);
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
    homeButton.setOnAction(a -> SceneManager.getInstance()
        .loadAndSwitchToFXML(loader.getController(), "home", anchorPane));

    // Account Settings Menu
    menuLoginButton.setOnAction(a -> SceneManager.getInstance()
        .loadAndSwitchToFXML(loader.getController(), "login", anchorPane));
    menuSignUpButton.setOnAction(a -> SceneManager.getInstance()
        .loadAndSwitchToFXML(loader.getController(), "signUp", anchorPane));
  }

  private void loadResults(String searchText) {
    new Thread(() -> {

      // TODO: Load FXML
//      Platform.runLater(() -> SceneManager.getInstance()
//          .loadAndSwitchToFXML(loader.getController(), "searchResults", anchorPane));

      // TODO: GET SQL RESULT OF BUSINESS NAMES HERE
    }).start();
  }

}
