package yelp.ui;

import java.util.ArrayList;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import yelp.model.Business;
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

  private ArrayList<Business> businesses;
  private String searchTerm;

  public SearchResultsController(String searchTerm, ArrayList<Business> businesses) {
    this.businesses = businesses;
    this.searchTerm = searchTerm;
  }

  @FXML
  public void initialize() {
    searchResultsLabel.setText(String.format("Search results for: %s", searchTerm));
    setResultsTable();
  }

  private void setResultsTable() {
    TableColumn<Business, String> tableColumnBusinessName = new TableColumn<>("Business");
    tableColumnBusinessName.setMinWidth(150);
    tableColumnBusinessName.setCellValueFactory(new PropertyValueFactory<>("name"));

    TableColumn<Business, String> tableColumnBusinessAddress = new TableColumn<>("Address");
    tableColumnBusinessAddress.setMinWidth(150);
    tableColumnBusinessAddress
        .setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getAddress()));

    ObservableList<Business> businessObservableList = FXCollections.observableArrayList();

    businessObservableList.addAll(businesses);

    searchResultsTable.getColumns().add(tableColumnBusinessName);
    searchResultsTable.getColumns().add(tableColumnBusinessAddress);

    searchResultsTable.setItems(businessObservableList);
  }

}
