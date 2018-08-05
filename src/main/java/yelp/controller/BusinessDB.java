package yelp.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;
import yelp.model.Business;
import yelp.utils.DatabaseManager;

public class BusinessDB {

  /**
   * Gets all users from database that meet the "name pattern" condition
   *
   * @param namePattern pattern to match user's names
   * @return result of query
   */
  public static ArrayList<Business> getBusinessByName(String namePattern) {

    String selectBusinesses = String
        .format(
            "SELECT id,name,address,postal_code,stars,open,credit_cards,car_parking,bike_parking,wheelchair_accessible,happy_hour,outdoor_seating FROM business WHERE name LIKE '%%%s%%'",
            namePattern);

    ArrayList<Business> businesses = new ArrayList<>();

    PreparedStatement stmt = null;
    ResultSet rs = null;
    Connection connection = null;

    try {
      connection = DatabaseManager.getConnection();
    } catch (SQLException e) {
      e.printStackTrace();
    }

    try {
      stmt = connection.prepareStatement(selectBusinesses);
      rs = stmt.executeQuery();
      while (rs.next()) {
        Business business = new Business();
        business.setId(rs.getString(1));
        business.setName(rs.getString(2));
        business.setAddress(rs.getString(3));
        business.setPostalCode(rs.getString(4));
        business.setStars(Double.parseDouble(rs.getString(5)));
        business.setOpen(Objects.equals(rs.getString(6), "1"));
        business.setCredit_cards(rs.getString(7));
        business.setCar_parking(rs.getString(8));
        business.setBike_parking(rs.getString(9));
        business.setWheelchair_accessible(rs.getString(10));
        business.setHappy_hour(rs.getString(11));
        business.setOutdoor_seating(rs.getString(12));
        businesses.add(business);
      }
    } catch (SQLException sqlExc) {
      System.out.println(sqlExc.getMessage());
    } finally {
      try {
        DatabaseManager.closeAll(stmt, rs, connection);
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }

    return businesses;
  }

}
