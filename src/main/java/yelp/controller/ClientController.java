package yelp.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import yelp.database.BusinessDB;
import yelp.model.Business;
import yelp.utils.SceneManager;

public class ClientController extends StackPane {

  /**
   * The logger.
   */
  private final Logger logger = Logger.getLogger(getClass().getName());

  private FXMLLoader loader;
  private SceneManager sceneManager;
  // java.util.concurrent.Executor provides a pool of threads
  private Executor threadPool;

  // ---- FXML  ----
  @FXML
  private BorderPane borderPane;

  @FXML
  private AnchorPane anchorPane;

  @FXML
  private ComboBox<String> searchFieldComboBox;

  @FXML
  private TextField searchBar;

  @FXML
  private TextField areaSearchBar;

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
      sceneManager.loadAndSwitchToFXML(loader.getController(), "login", anchorPane);
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
    searchBar.setOnAction(a -> loadResults(searchBar.getText(), areaSearchBar.getText(),
        searchFieldComboBox.getValue()));

    searchBar.focusedProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue) {
        Platform.runLater(() -> searchBar.selectAll());
      }
    });

    // Search Bar
    areaSearchBar.setOnAction(a -> loadResults(searchBar.getText(), areaSearchBar.getText(),
        searchFieldComboBox.getValue()));

    areaSearchBar.focusedProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue) {
        Platform.runLater(() -> areaSearchBar.selectAll());
      }
    });

    //Search Button
    searchButton.setOnAction(searchBar.getOnAction());

    // Home Button
    homeButton.setOnAction(a -> {
      sceneManager.loadAndSwitchToFXML(loader.getController(), "home", anchorPane);
    });

    // Account Settings Menu
    menuLoginButton.setOnAction(a -> {
      sceneManager.loadAndSwitchToFXML(loader.getController(), "login", anchorPane);
    });
    menuSignUpButton.setOnAction(a -> {
      sceneManager.loadAndSwitchToFXML(loader.getController(), "signUp", anchorPane);
    });
  }

  public Executor getThreadPool() {
    return this.threadPool;
  }

  public AnchorPane getAnchorPane() {
    return this.anchorPane;
  }

  private void loadResults(final String searchText, final String areaSearchText,
      final String searchParameter) {
    Task<ArrayList<Business>> businessSearchTask = new Task<ArrayList<Business>>() {
      @Override
      public ArrayList<Business> call() throws Exception {
        String query;
        if (areaSearchText.isEmpty()) {
          if (Objects.equals(searchParameter, "Businesses")) {
            // CASE 1: BUSINESSES (NO AREA)
            query = String
                .format(
                    "SELECT * FROM business WHERE name LIKE '%%%s%%' ORDER BY stars DESC;",
                    searchText);
          } else {
            query = String
                .format(
                    "SELECT * FROM business b INNER JOIN belongs_to bt ON b.id = bt.business_id WHERE bt.category_name LIKE '%%%s%%' ORDER BY b.stars DESC;",
                    searchText);
          }
        } else {
          String[] cityState = areaSearchText.trim().split(",");
          if (searchText.isEmpty()) {
            // EXTRA CASE: AREA w/o OTHER SEARCH TERM
            query = String.format(
                "SELECT * FROM business b INNER JOIN postal_code p ON b.postal_code= p.code WHERE p.city LIKE '%%%s%%' AND p.state LIKE '%%%s%%' ORDER BY b.stars DESC;",
                cityState[0].trim(), cityState[1].trim());
          } else {
            if (Objects.equals(searchParameter, "Businesses")) {
              // CASE 3: BUSINESSES w/ AREA
              query = String
                  .format(
                      "SELECT * FROM business b INNER JOIN postal_code p ON b.postal_code= p.code WHERE p.city LIKE '%%%s%%' AND p.state LIKE '%%%s%%' AND b.name LIKE '%%%s%%' ORDER BY b.stars DESC;"
                      , cityState[0].trim(), cityState[1].trim(), searchText);
            } else {
              // CASE 4: CATEGORIES w/ AREA
              query = String
                  .format(
                      "SELECT * FROM (SELECT b.id, b.name, b.address, b.stars FROM business b INNER JOIN postal_code p ON b.postal_code = p.code WHERE p.city = '%s' AND p.state = '%s') as b1 INNER JOIN belongs_to bt ON b1.id = bt.business_id WHERE bt.category_name = '%s' ORDER BY b1.stars DESC;"
                      , cityState[0].trim(), cityState[1].trim(), searchText
                  );

            }
          }
        }
        return BusinessDB.getBusinessesByQuery(query);
      }
    };

    // On success, start a new searchResults pane with the retrieved information and load it
    businessSearchTask.setOnSucceeded(e -> {
      SearchResultsController searchResultsController = new SearchResultsController(searchText,
          areaSearchText, searchParameter, businessSearchTask.getValue(), this);

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
