package yelp.ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import yelp.controller.ReviewDB;
import yelp.model.Business;
import yelp.model.Review;
import yelp.utils.SceneManager;

public class SearchResultsController extends BorderPane {

  /**
   * The logger.
   */
  private final Logger logger = Logger.getLogger(getClass().getName());

  private SceneManager sceneManager = null;

  // ---- FXML  ----
  @FXML
  private TableView<Business> searchResultsTable;

  @FXML
  private Label searchResultsLabel;

  private AnchorPane anchorPane;

  private ArrayList<Business> businesses;
  private String searchTerm;
  private String areaSearchText;
  private String searchParameter;


  public SearchResultsController(String searchTerm, String areaSearchText, String searchParameter,
      ArrayList<Business> businesses) {
    this.businesses = businesses;
    this.searchTerm = searchTerm;
    this.areaSearchText = areaSearchText;
    this.searchParameter = searchParameter;
  }

  @FXML
  public void initialize() {
    StringBuilder searchDisplayLabelText = new StringBuilder();

    if (searchTerm.length() > 0) {
      if (Objects.equals(this.searchParameter, "Businesses")) {
        searchDisplayLabelText.append("Search results for '").append(searchTerm).append('\'');
      } else {
        searchDisplayLabelText.append("Best '").append(searchTerm).append('\'');
      }
    } else {
      searchDisplayLabelText.append("Everything");
    }

    if (this.areaSearchText.length() > 0) {
      searchDisplayLabelText.append(" near ").append(areaSearchText);
    }

    searchResultsLabel.setText(searchDisplayLabelText.toString());
    setResultsTable();
  }

  private void setResultsTable() {
    TableColumn<Business, String> tableColumnBusinessName = new TableColumn<>("Business");
    tableColumnBusinessName.setMinWidth(200);
    tableColumnBusinessName.setCellValueFactory(new PropertyValueFactory<>("name"));

    TableColumn<Business, String> tableColumnBusinessAddress = new TableColumn<>("Address");
    tableColumnBusinessAddress.setMinWidth(200);
    tableColumnBusinessAddress.setCellValueFactory(new PropertyValueFactory<>("address"));

    TableColumn<Business, String> tableColumnBusinessStars = new TableColumn<>("Stars");
    tableColumnBusinessStars.setMinWidth(150);
    tableColumnBusinessStars.setCellValueFactory(new PropertyValueFactory<>("stars"));

//    TableColumn<Business, String> tableColumnBusinessAddress = new TableColumn<>("Address");
//    tableColumnBusinessAddress.setMinWidth(150);
//    tableColumnBusinessAddress
//        .setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getAddress()));

    ObservableList<Business> businessObservableList = FXCollections.observableArrayList();

    businessObservableList.addAll(businesses);

    searchResultsTable.getColumns().add(tableColumnBusinessName);
    searchResultsTable.getColumns().add(tableColumnBusinessAddress);
    searchResultsTable.getColumns().add(tableColumnBusinessStars);

    searchResultsTable.setItems(businessObservableList);
  }

  public void showBusiness() {
    Business business = searchResultsTable.getSelectionModel().getSelectedItem();

    if (business == null) {
      System.out.println("Nothing selected");
      return;
    } else {
      System.out.println(business.getName());
      try {
        FXMLLoader businessLoader = new FXMLLoader(getClass().getResource("/fxml/business.fxml"));
        Parent p = (Parent) businessLoader.load();

        BusinessController businessController = businessLoader.getController();
        businessLoader.setController(businessController);
        if (businessController == null) {
          System.out.println("business controller is null");
          return;
        }
        businessController.setBusinessDisplay(business);
        // TODO: add all the reviews
        businessController.setReviewTable(loadReviews(business));
        Stage stage = new Stage();
        stage.setScene(new Scene(p));
        stage.show();
      } catch (IOException | NullPointerException e) {
        e.printStackTrace();
      }
    }
  }

  private ArrayList<Review> loadReviews(Business business) {
    String query = String.format("select b.name, w.text, w.votes from business b inner join writes_review2 w on b.id = w.business_id where b.id = %s order by w.votes", business.getId());
    return ReviewDB.getReviews(query);
  }
}