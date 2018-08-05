package yelp.ui;

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
import yelp.controller.BusinessDB;
import yelp.model.Business;
import yelp.utils.SceneManager;

public class ClientController extends StackPane {

  /**
   * The logger.
   */
  private final Logger logger = Logger.getLogger(getClass().getName());

  private FXMLLoader loader;
  private SceneManager sceneManager;
  // java.util.concurrent.Executor typically provides a pool of threads
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

  private void loadResults(final String searchText, final String areaSearchText,
      final String searchParameter) {
    Task<ArrayList<Business>> businessSearchTask = new Task<ArrayList<Business>>() {
      @Override
      public ArrayList<Business> call() throws Exception {
        if (areaSearchText.length() < 1) {
          if (Objects.equals(searchParameter, "Categories")) {
            // CASE 1: BUSINESSES (NO AREA)
            String query = String
                .format(
                    "SELECT id,name,address,postal_code,stars,open,credit_cards,car_parking,bike_parking,wheelchair_accessible,happy_hour,outdoor_seating FROM business WHERE name LIKE '%%%s%%'",
                    searchText);
            return BusinessDB.getBusinessesByQuery(query);
          }
        } else {
          if (Objects.equals(searchParameter, "Businesses")) {
            // CASE 3: BUSINESSES w/ AREA
//            String query = String
//                .format(
//                    "SELECT id,name,address,postal_code,stars,open,credit_cards,car_parking,bike_parking,wheelchair_accessible,happy_hour,outdoor_seating FROM business WHERE name LIKE '%%%s%%'",
//                    searchText);
          } else {
            // CASE 4: CATEGORIES w/ AREA
            String[] cityState = areaSearchText.trim().split(",");
            String query = String
                .format(
                    "SELECT b1.id, b1.name, b1.address, b1.stars FROM (SELECT b.id, b.name, b.address, b.stars FROM business b INNER JOIN postal_code p ON b.postal_code= p.code WHERE p.city = '%s' AND p.state = '%s') as b1 INNER JOIN belongs_to bt ON b1.id = bt.business_id WHERE bt.category_name = '%s' ORDER BY b1.stars DESC"
                    , cityState[0].trim(), cityState[1].trim(), searchText
                );
            return BusinessDB.getBusinessesByQuery(query);

          }
        }
        throw new IllegalArgumentException("No Argument");
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
