package yelp.model;

import java.util.Date;

public class Review {

  private String userId;
  private String businessId;
  private String text;
  private int rating;
  private Date reviewDate;
  private int votes;

  public Review(){}

  public Review(String userId, String businessId, String text, int rating, Date reviewDate,
      int votes) {

    this.userId = userId;
    this.businessId = businessId;
    this.text = text;
    this.rating = rating;
    this.reviewDate = reviewDate;
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

  public Date getReviewDate() {
    return reviewDate;
  }

  public void setReviewDate(Date reviewDate) {
    this.reviewDate = reviewDate;
  }

  public int getVotes() {
    return votes;
  }

  public void setVotes(int votes) {
    this.votes = votes;
  }
}
