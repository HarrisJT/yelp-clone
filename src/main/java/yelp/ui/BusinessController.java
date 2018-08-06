package yelp.ui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import yelp.model.Business;

import java.net.URL;
import java.util.ResourceBundle;

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


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setBusinessDisplay(Business business) {
        businessName.setText(business.getName());
        rating.setText(String.valueOf(business.getStars()));
        address.setText(business.getAddress());

//        String openTime = String.format("MON: %s - %s\n" +
//                                        "TUE: %s - %s\n" +
//                                        "WED: %s - %s\n" +
//                                        "THU: %s - %s\n" +
//                                        "FRI: %s - %s\n" +
//                                        "SAT: %s - %s\n" +
//                                        "SUN: %s - %s\n",
//                business.getMonday_open().toString(), business.getMonday_close().toString(),
//                business.getTuesday_open().toString(), business.getTuesday_close().toString(),
//                business.getWednesday_open().toString(), business.getWednesday_close().toString(),
//                business.getThursday_open().toString(), business.getThursday_close().toString(),
//                business.getFriday_open().toString(), business.getFriday_close().toString(),
//                business.getSaturday_open().toString(), business.getSaturday_close().toString(),
//                business.getSunday_open().toString(), business.getSunday_close().toString());

        hours.setText(String.format("MON: %s", business.getMonday_open()));
    }
}
