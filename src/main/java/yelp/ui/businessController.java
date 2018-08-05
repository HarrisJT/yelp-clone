package yelp.ui;

import javafx.scene.text.Text;
import yelp.model.Business;

public class businessController {

    // ---- FXML ----
    private Text businessName;
    private Text rating;
    private Text address;
    private Text hours;


    private Business business;

    public businessController(Business business) {
        this.business = business;
    }

    public void setBusinessDisplay() {
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
}
