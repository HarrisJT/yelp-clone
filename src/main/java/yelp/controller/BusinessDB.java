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
    System.out.println(query);

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
        Business business = new Business();
        business.setId(rs.getString("id"));
        business.setName(rs.getString("name"));
        business.setAddress(rs.getString("address"));
        business.setStars(Double.parseDouble(rs.getString("stars")));
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
