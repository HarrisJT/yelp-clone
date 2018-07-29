package yelp.model;

import java.util.*;

public class Review {
    private String userId;
    private String businessId;
    private String text;
    private int rating;
    private Date date;
    private int votes;

    public Review(String userId, String businessId, String text, int rating, Date date, int votes) {

        this.userId = userId;
        this.businessId = businessId;
        this.text = text;
        this.rating = rating;
        this.date = date;
        this.votes = votes;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }
}
