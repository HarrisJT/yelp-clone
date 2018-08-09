package yelp.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import yelp.database.ReviewDB;
import yelp.model.Business;
import yelp.model.Review;

public class SearchResultsController extends BorderPane {

  /**
   * The logger.
   */
  private final Logger logger = Logger.getLogger(getClass().getName());

  private Executor threadPool;

  // ---- FXML  ----
  @FXML
  private TableView<Business> searchResultsTable;

  @FXML
  private Label searchResultsLabel;

  @FXML
  private AnchorPane anchorPane;

  private ArrayList<Business> businesses;
  private String searchTerm;
  private String areaSearchText;
  private String searchParameter;

  public SearchResultsController(String searchTerm, String areaSearchText, String searchParameter,
      ArrayList<Business> businesses, ClientController clientController) {
    this.businesses = businesses;
    this.searchTerm = searchTerm;
    this.areaSearchText = areaSearchText;
    this.searchParameter = searchParameter;
    this.anchorPane = clientController.getAnchorPane();
    this.threadPool = clientController.getThreadPool();
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

    searchResultsTable
        .setPrefHeight(anchorPane.getScene().getHeight() - searchResultsLabel.getHeight() - 135);

    searchResultsTable.setRowFactory(tv -> {
      TableRow<Business> row = new TableRow<>();
      row.setOnMouseClicked(event -> {
        if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY) {
          Business clickedRow = row.getItem();
          showBusiness(clickedRow);
        }
      });
      return row;
    });
  }

  private void setResultsTable() {
    TableColumn<Business, String> tableColumnBusinessStars = new TableColumn<>("Stars");
    tableColumnBusinessStars.setResizable(false);
    tableColumnBusinessStars.setCellValueFactory(new PropertyValueFactory<>("stars"));
    tableColumnBusinessStars.prefWidthProperty()
        .bind(searchResultsTable.widthProperty().multiply(0.05));

    TableColumn<Business, String> tableColumnBusinessName = new TableColumn<>("Business");
    tableColumnBusinessName.setCellValueFactory(new PropertyValueFactory<>("name"));
    tableColumnBusinessName.prefWidthProperty()
        .bind(searchResultsTable.widthProperty().multiply(0.35));

    TableColumn<Business, String> tableColumnBusinessAddress = new TableColumn<>("Address");
    tableColumnBusinessAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
    tableColumnBusinessAddress.prefWidthProperty()
        .bind(searchResultsTable.widthProperty().multiply(0.30));

    ObservableList<Business> businessObservableList = FXCollections.observableArrayList();

    businessObservableList.addAll(businesses);

    searchResultsTable.getColumns().add(tableColumnBusinessStars);
    searchResultsTable.getColumns().add(tableColumnBusinessName);
    searchResultsTable.getColumns().add(tableColumnBusinessAddress);
    searchResultsTable.setItems(businessObservableList);
  }

  private void showBusiness(Business selectedBusiness) {
    if (businesses == null) {
      return;
    }

    Task<ArrayList<Review>> reviewsTask = new Task<ArrayList<Review>>() {
      @Override
      public ArrayList<Review> call() throws Exception {
        String query = String.format(
            "SELECT w.text, w.review_date, w.votes, w.rating FROM business b INNER JOIN writes_review2 w ON b.id = w.business_id WHERE b.id = '%s' ORDER BY w.votes DESC",
            selectedBusiness.getId());
        return ReviewDB.getReviews(query);
      }
    };

    reviewsTask.setOnSucceeded(e -> {
      BusinessController businessController = new BusinessController(selectedBusiness,
          reviewsTask.getValue());

      FXMLLoader businessFXMLLoader = new FXMLLoader();
      businessFXMLLoader.setController(businessController);
      businessFXMLLoader.setLocation(getClass().getResource("/fxml/business.fxml"));

      anchorPane.getChildren().clear();
      try {
        anchorPane.getChildren().add(businessFXMLLoader.load());
      } catch (IOException ex) {
        ex.printStackTrace();
      }
    });

    threadPool.execute(reviewsTask);
  }
}