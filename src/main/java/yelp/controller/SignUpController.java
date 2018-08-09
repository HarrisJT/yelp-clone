package yelp.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import yelp.utils.SceneManager;

public class SignUpController {

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
  }
}
