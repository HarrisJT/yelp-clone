package yelp.ui;

import javafx.event.ActionEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import yelp.controller.BusinessDB;
import yelp.model.Business;
import yelp.model.Review;

import javax.swing.*;

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
  private TextField ratingInput;

  @FXML
  private TextField reviewText;

  @FXML
  private  TextField votesInput;

  private Business business;

  private ArrayList<Review> reviews;


  public BusinessController(Business business, ArrayList<Review> reviews) {
    this.business = business;
    this.reviews = reviews;
  }

  @FXML
  public void initialize() {
    setBusinessDisplay(business);
    setReviewTable(reviews);
  }

  public void setBusinessDisplay(Business business) {
    businessName.setText(business.getName());
    rating.setText(String.valueOf(business.getStars()));
    address.setText(business.getAddress());

    String openTime = String.format("MON: %s - %s\n" +
            "TUE: %s - %s\n" +
            "WED: %s - %s\n" +
            "THU: %s - %s\n",
        business.getMonday_open().toString(), business.getMonday_close().toString(),
        business.getTuesday_open().toString(), business.getTuesday_close().toString(),
        business.getWednesday_open().toString(), business.getWednesday_close().toString(),
        business.getThursday_open().toString(), business.getThursday_close().toString());

    String weekendOpenTime = String.format("FRI: %s - %s\n" +
            "SAT: %s - %s\n" +
            "SUN: %s - %s\n", business.getFriday_open().toString(),
        business.getFriday_close().toString(),
        business.getSaturday_open().toString(), business.getSaturday_close().toString(),
        business.getSunday_open().toString(), business.getSunday_close().toString());

    hours.setText(openTime);
    weekendHours.setText(weekendOpenTime);
  }

  public void setReviewTable(ArrayList<Review> reviews) {
    // Votes column
    TableColumn<Review, Integer> tableColumnVotes = new TableColumn<>("Votes");
    tableColumnVotes.setCellValueFactory(new PropertyValueFactory<>("votes"));
    tableColumnVotes.prefWidthProperty().bind(reviewsTable.widthProperty().multiply(0.05));

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

    reviewsObservableList.addAll(reviews);

    reviewsTable.getColumns().add(tableColumnVotes);
    reviewsTable.getColumns().add(tableColumnDate);
    reviewsTable.getColumns().add(tableColumnText);
    reviewsTable.setItems(reviewsObservableList);
  }

  public void submitReview(ActionEvent e) {
    String query;

    int rating = Integer.parseInt(ratingInput.getText());
    String review = reviewText.getText();
    int votes = Integer.parseInt(votesInput.getText());

    if (rating > 5 || rating < 1 || votes > 5 || votes < 1 || review == null) {
      return;
    }

    String uId = UUID.randomUUID().toString().replaceAll("-", "");

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date date = new Date();
    String currDate = formatter.format(date);

    query = String.format("INSERT INTO writes_review2 VALUES(%s, %s, %d, '%s', '%s', %d, 0, 0)",
            uId, business.getId(), rating, currDate, review, votes);

    System.out.println(query);

    BusinessDB.insertNewReview(query);
  }
}
