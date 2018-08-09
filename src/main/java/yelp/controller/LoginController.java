package yelp.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import yelp.utils.SceneManager;

public class LoginController {


  private FXMLLoader loader;
  private SceneManager sceneManager = null;

  // ---- FXML  ----

  @FXML
  private TextField usernameText2;

  @FXML
  private TextField passwordText2;

  @FXML
  private Button logInButton;


  public LoginController() {
  }
}
