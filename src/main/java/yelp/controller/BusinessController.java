package yelp.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import yelp.database.ReviewDB;
import yelp.model.Business;
import yelp.model.Review;

public class BusinessController {

  // ---- FXML ----
  @FXML
  private Text businessName;

  @FXML
  private Text rating;

  @FXML
  private Text address;

  @FXML
  private Text hours;

  @FXML
  private Text weekendHours;

  @FXML
  private TableView<Review> reviewsTable;

  @FXML
  private TextField ratingTextField;

  @FXML
  private TextArea reviewTextArea;

  @FXML
  private Button submitReviewButton;

  private Business business;

  private ArrayList<Review> reviews;


  public BusinessController(Business business, ArrayList<Review> reviews) {
    this.business = business;
    this.reviews = reviews;
  }

  @FXML
  public void initialize() {
    submitReviewButton.setOnAction(a -> submitReview());

    setBusinessDisplay();
    setReviewTable();
  }

  /**
   * Sets up the business information in the FXML frame
   */
  private void setBusinessDisplay() {
    Business currentBusiness = this.business;
    businessName.setText(currentBusiness.getName());
    rating.setText(String.valueOf(currentBusiness.getStars()));
    address.setText(currentBusiness.getAddress());

    String openTime = String.format("MON: %s - %s\n" +
            "TUE: %s - %s\n" +
            "WED: %s - %s\n" +
            "THU: %s - %s\n",
        currentBusiness.getMonday_open().toString(), currentBusiness.getMonday_close().toString(),
        currentBusiness.getTuesday_open().toString(), currentBusiness.getTuesday_close().toString(),
        currentBusiness.getWednesday_open().toString(),
        currentBusiness.getWednesday_close().toString(),
        currentBusiness.getThursday_open().toString(),
        currentBusiness.getThursday_close().toString());

    String weekendOpenTime = String.format("FRI: %s - %s\n" +
            "SAT: %s - %s\n" +
            "SUN: %s - %s\n", currentBusiness.getFriday_open().toString(),
        currentBusiness.getFriday_close().toString(),
        currentBusiness.getSaturday_open().toString(),
        currentBusiness.getSaturday_close().toString(),
        currentBusiness.getSunday_open().toString(), currentBusiness.getSunday_close().toString());

    hours.setText(openTime);
    weekendHours.setText(weekendOpenTime);
  }

  /**
   * Sets up the business information in the FXML frame
   */
  private void setReviewTable() {
    // Votes column
    TableColumn<Review, Integer> tableColumnVotes = new TableColumn<>("Votes");
    tableColumnVotes.setCellValueFactory(new PropertyValueFactory<>("votes"));
    tableColumnVotes.prefWidthProperty().bind(reviewsTable.widthProperty().multiply(0.05));

    // Votes column
    TableColumn<Review, Integer> tableColumnRating = new TableColumn<>("Rating");
    tableColumnRating.setCellValueFactory(new PropertyValueFactory<>("rating"));
    tableColumnRating.prefWidthProperty().bind(reviewsTable.widthProperty().multiply(0.05));

    // Date column
    TableColumn<Review, String> tableColumnDate = new TableColumn<>("Date");
    tableColumnDate.setCellValueFactory(new PropertyValueFactory<>("reviewDate"));
    tableColumnDate.prefWidthProperty().bind(reviewsTable.widthProperty().multiply(0.10));

    // Text column
    TableColumn<Review, String> tableColumnText = new TableColumn<>("Review");
    tableColumnText.setCellFactory(tc -> {
      TableCell<Review, String> cell = new TableCell<>();
      Text text = new Text();
      cell.setGraphic(text);
      cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
      text.wrappingWidthProperty().bind(tableColumnText.widthProperty());
      text.textProperty().bind(cell.itemProperty());
      return cell;
    });
    tableColumnText.setCellValueFactory(new PropertyValueFactory<>("text"));
    tableColumnText.prefWidthProperty().bind(reviewsTable.widthProperty().multiply(0.80));

    ObservableList<Review> reviewsObservableList = FXCollections.observableArrayList();

    reviewsObservableList.addAll(this.reviews);

    reviewsTable.getColumns().add(tableColumnVotes);
    reviewsTable.getColumns().add(tableColumnRating);
    reviewsTable.getColumns().add(tableColumnDate);
    reviewsTable.getColumns().add(tableColumnText);
    reviewsTable.setItems(reviewsObservableList);
  }

  /**
   * Called when a new review is submitted.
   */
  private void submitReview() {
    String query;

    Double rating = Double.parseDouble(ratingTextField.getText());
    String review = reviewTextArea.getText();

    String uId = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 21);

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date date = new Date();
    String currDate = formatter.format(date);

    // Gives 0 to all the "vote" parameters
    query = String
        .format("INSERT INTO writes_review2 VALUES('%s', '%s', %f, '%s', '%s', 0, 0, 0);", uId,
            business.getId(), rating, currDate, review);

    ReviewDB.insertNewReview(query);
  }
}
