package yelp.ui;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import yelp.model.Business;
import yelp.model.Review;

public class BusinessController implements Initializable {

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
  private TableView<Review> reviewsTable;



  @Override
  public void initialize(URL location, ResourceBundle resources) {

  }

  public void setBusinessDisplay(Business business) {
    businessName.setText(business.getName());
    rating.setText(String.valueOf(business.getStars()));
    address.setText(business.getAddress());

        String openTime = String.format("MON: %s - %s\n" +
                                        "TUE: %s - %s\n" +
                                        "WED: %s - %s\n" +
                                        "THU: %s - %s\n" +
                                        "FRI: %s - %s\n" +
                                        "SAT: %s - %s\n" +
                                        "SUN: %s - %s\n",
                business.getMonday_open().toString(), business.getMonday_close().toString(),
                business.getTuesday_open().toString(), business.getTuesday_close().toString(),
                business.getWednesday_open().toString(), business.getWednesday_close().toString(),
                business.getThursday_open().toString(), business.getThursday_close().toString(),
                business.getFriday_open().toString(), business.getFriday_close().toString(),
                business.getSaturday_open().toString(), business.getSaturday_close().toString(),
                business.getSunday_open().toString(), business.getSunday_close().toString());

    hours.setText(openTime);
  }

  public void setReviewTable(ArrayList<Review> reviews) {
    // Votes cloumn
    TableColumn<Review, Integer> tableColumnVotes = new TableColumn<>("Votes");
    tableColumnVotes.setMinWidth(200);
    tableColumnVotes.setCellValueFactory(new PropertyValueFactory<>("votes"));

    // Text cloumn
    TableColumn<Review, String> tableColumnText = new TableColumn<>("Review");
    tableColumnText.setMinWidth(300);
    tableColumnText.setCellValueFactory(new PropertyValueFactory<>("text"));

    ObservableList<Review> reviewsObservableList = FXCollections.observableArrayList();

    reviewsObservableList.addAll(reviews);

    reviewsTable.getColumns().add(tableColumnVotes);
    reviewsTable.getColumns().add(tableColumnText);

    reviewsTable.setItems(reviewsObservableList);

  }
}
