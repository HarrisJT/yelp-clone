package yelp.ui;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
  private MenuItem loginButton;
  @FXML
  private MenuItem signUpButton;

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

    anchorPane.prefWidthProperty().bind(borderPane.widthProperty());
  }

  /**
   * Called as soon as .fxml is initialized
   */
  @FXML
  private void initialize() {
    logger.log(Level.INFO, "ClientController Initialize");

    loginButton.setOnAction(a -> SceneManager.getInstance()
        .loadAndSwitchToFXML("Loading...", loader.getController(), "login", anchorPane));
    signUpButton.setOnAction(a -> SceneManager.getInstance()
        .loadAndSwitchToFXML("Loading...", loader.getController(), "signUp", anchorPane));

    SceneManager.getInstance()
        .loadAndSwitchToFXML("Loading...", loader.getController(), "home", anchorPane);
  }

}
