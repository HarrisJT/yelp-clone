package yelp.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import yelp.model.Business;
import yelp.utils.DatabaseManager;

public class BusinessDB {

  /**
   * Gets all users from database that meet the "name pattern" condition
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
        business.setStars(Double.parseDouble(rs.getString("stars")));

//        business.setMonday_open(rs.getTime("monday_open"));
//        business.setMonday_close(rs.getTime("monday_close"));
//        business.setMonday_open(rs.getTime("monday_open"));
//        business.setMonday_open(rs.getTime("monday_open"));
//        business.setMonday_open(rs.getTime("monday_open"));
//        business.setMonday_open(rs.getTime("monday_open"));
//        business.setMonday_open(rs.getTime("monday_open"));
//        business.setMonday_open(rs.getTime("monday_open"));
//        business.setMonday_open(rs.getTime("monday_open"));
//        business.setMonday_open(rs.getTime("monday_open"));
//        business.setMonday_open(rs.getTime("monday_open"));
//        business.setMonday_open(rs.getTime("monday_open"));
//        business.setMonday_open(rs.getTime("monday_open"));
//        business.setMonday_open(rs.getTime("monday_open"));

        //business.setOpen(Objects.equals(rs.getString("open"), "1"));
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
