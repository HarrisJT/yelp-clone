package yelp.model;

import java.sql.Date;

public class User {

  private String id;
  private String name;
  private Date startDate;

  public User(String id, String name, Date startDate) {
    this.id = id;
    this.name = name;
    this.startDate = startDate;
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

  public Date getStartDate() {
    return startDate;
  }

  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }
}

