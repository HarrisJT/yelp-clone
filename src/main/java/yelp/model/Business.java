package yelp.model;

import java.sql.Time;

public class Business {

  private String id;
  private String name;
  private String address;
  private String postalCode;
  private double stars;
  private boolean open;

  private Time monday_open;
  private Time monday_close;
  private Time tuesday_open;
  private Time tuesday_close;
  private Time wednesday_open;
  private Time wednesday_close;
  private Time thursday_open;
  private Time thursday_close;
  private Time friday_open;
  private Time friday_close;
  private Time saturday_open;
  private Time saturday_close;
  private Time sunday_open;
  private Time sunday_close;

  private String credit_cards;
  private String car_parking;
  private String bike_parking;
  private String wheelchair_accessible;
  private String happy_hour;
  private String outdoor_seating;

  public Business(String id, String name, String address, String postalCode, double stars,
      boolean open, Time monday_open, Time monday_close, Time tuesday_open, Time tuesday_close,
      Time wednesday_open, Time wednesday_close, Time thursday_open, Time thursday_close,
      Time friday_open, Time friday_close, Time saturday_open, Time saturday_close,
      Time sunday_open, Time sunday_close, String credit_cards, String car_parking,
      String bike_parking, String wheelchair_accessible, String happy_hour,
      String outdoor_seating) {
    this.id = id;
    this.name = name;
    this.address = address;
    this.postalCode = postalCode;
    this.stars = stars;
    this.open = open;
    this.monday_open = monday_open;
    this.monday_close = monday_close;
    this.tuesday_open = tuesday_open;
    this.tuesday_close = tuesday_close;
    this.wednesday_open = wednesday_open;
    this.wednesday_close = wednesday_close;
    this.thursday_open = thursday_open;
    this.thursday_close = thursday_close;
    this.friday_open = friday_open;
    this.friday_close = friday_close;
    this.saturday_open = saturday_open;
    this.saturday_close = saturday_close;
    this.sunday_open = sunday_open;
    this.sunday_close = sunday_close;
    this.credit_cards = credit_cards;
    this.car_parking = car_parking;
    this.bike_parking = bike_parking;
    this.wheelchair_accessible = wheelchair_accessible;
    this.happy_hour = happy_hour;
    this.outdoor_seating = outdoor_seating;
  }

  public Business() {
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getPostalCode() {
    return postalCode;
  }

  public void setPostalCode(String postalCode) {
    this.postalCode = postalCode;
  }

  public double getStars() {
    return stars;
  }

  public void setStars(double stars) {
    this.stars = stars;
  }

  public boolean isOpen() {
    return open;
  }

  public void setOpen(boolean open) {
    this.open = open;
  }

  public String getCredit_cards() {
    return credit_cards;
  }

  public void setCredit_cards(String credit_cards) {
    this.credit_cards = credit_cards;
  }

  public String getCar_parking() {
    return car_parking;
  }

  public void setCar_parking(String car_parking) {
    this.car_parking = car_parking;
  }

  public String getBike_parking() {
    return bike_parking;
  }

  public void setBike_parking(String bike_parking) {
    this.bike_parking = bike_parking;
  }

  public String getWheelchair_accessible() {
    return wheelchair_accessible;
  }

  public void setWheelchair_accessible(String wheelchair_accessible) {
    this.wheelchair_accessible = wheelchair_accessible;
  }

  public String getHappy_hour() {
    return happy_hour;
  }

  public void setHappy_hour(String happy_hour) {
    this.happy_hour = happy_hour;
  }

  public String getOutdoor_seating() {
    return outdoor_seating;
  }

  public void setOutdoor_seating(String outdoor_seating) {
    this.outdoor_seating = outdoor_seating;
  }
}