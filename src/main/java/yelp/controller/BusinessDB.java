package yelp.controller;

import java.sql.*;
import java.util.ArrayList;
import yelp.model.Business;
import yelp.utils.DatabaseManager;

public class BusinessDB {

  /**
   * Gets all businesses from database that meet the "name pattern" condition
   *
   * @param query pattern to match user's names
   * @return result of query
   */
  public static ArrayList<Business> getBusinessesByQuery(String query) {
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
      stmt = connection.prepareStatement(query);
      rs = stmt.executeQuery();
      while (rs.next()) {
        String name = rs.getString("name");
        String address = rs.getString("address");
        Business business = new Business();
        business.setId(rs.getString("id"));
        business.setName(name.substring(1, name.length() - 1));
        business.setAddress(address.substring(1, address.length() - 1));
        business.setStars(rs.getDouble("stars"));

        business.setMonday_open(rs.getTime("monday_open"));
        business.setMonday_close(rs.getTime("monday_close"));
        business.setTuesday_open(rs.getTime("tuesday_open"));
        business.setTuesday_close(rs.getTime("tuesday_close"));
        business.setWednesday_open(rs.getTime("wednesday_open"));
        business.setWednesday_close(rs.getTime("wednesday_close"));
        business.setThursday_open(rs.getTime("thursday_open"));
        business.setThursday_close(rs.getTime("thursday_close"));
        business.setFriday_open(rs.getTime("friday_open"));
        business.setFriday_close(rs.getTime("friday_close"));
        business.setSaturday_open(rs.getTime("saturday_open"));
        business.setSaturday_close(rs.getTime("saturday_close"));
        business.setSunday_open(rs.getTime("sunday_open"));
        business.setSunday_close(rs.getTime("sunday_close"));

        //business.setOpen(Objects.equals(rs.getString("open"), "1")); // dont use
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

  public static void insertNewReview(String query) {
    Statement stmt = null;
    Connection connection = null;

    try {
      connection = DatabaseManager.getConnection();
      stmt = connection.createStatement();
      stmt.executeUpdate(query);
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      try {
        stmt.close();
        connection.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }

    System.out.println("insert successful");
  }
}