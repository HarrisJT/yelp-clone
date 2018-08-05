package yelp.ui;

import java.util.ArrayList;
import java.util.Objects;
import java.util.logging.Logger;
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
    if (Objects.equals(this.searchParameter, "Businesses")) {
      searchDisplayLabelText.append("Search results for '").append(searchTerm).append('\'');
    } else {
      searchDisplayLabelText.append("Best '").append(searchTerm).append('\'');
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

}
