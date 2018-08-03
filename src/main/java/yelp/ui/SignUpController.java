package yelp.ui;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import yelp.utils.SceneManager;

public class SignUpController {

  /**
   * The logger.
   */
  private final Logger logger = Logger.getLogger(getClass().getName());

  private FXMLLoader loader;
  private SceneManager sceneManager = null;

  // ---- FXML  ----

  @FXML
  private TextField usernameText;

  @FXML
  private TextField passwordText;

  @FXML
  private TextField verifyPasswordText;

  @FXML
  private Button signUpButton;


  public SignUpController() {
    logger.log(Level.INFO, "SignUpController Constructed");

    // TODO: INITIALIZE HERE
  }
}