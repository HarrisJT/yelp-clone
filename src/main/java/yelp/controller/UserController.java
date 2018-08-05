package yelp.controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import yelp.utils.DatabaseManager;

public class UserController {

  /**
   * Gets all users from database that meet the "name pattern" condition
   *
   * @param namePattern pattern to match user's names
   * @return result of query
   */
//  public TreeMap<String, HashMap<String, String>> getUsers(String namePattern) {
//    TreeMap<String, HashMap<String, String>> users = new TreeMap<>();
//    String selectUsers = String
//        .format("SELECT id, name, startDate FROM USERS WHERE name LIKE '%%%s%%'", namePattern);
//
//    try {
//      users = DatabaseManager.select(DatabaseManager.getConnection(), selectUsers);
//    } catch (SQLException e) {
//      e.printStackTrace();
//    }
//
//    return users;
//  }

  /**
   * Insert new user into the table
   *
   * @param id, name, startDate TODO: add password attribute?
   */
  public void addUser(String id, String name, String startDate) {
    String addUser = String
        .format("insert into user (id, name, start_date) values (%s, %s, %s);", id, name,
            startDate);

    try {
      Connection conn = DatabaseManager.getConnection();
      Statement stmt = conn.createStatement();
      stmt.execute(addUser);
      System.out.println("Insert successful.");
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

}
