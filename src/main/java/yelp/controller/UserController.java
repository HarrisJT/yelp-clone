package yelp.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.TreeMap;
import yelp.utils.DatabaseManager;

public class UserController {
  /**
   * Gets all users from database that meet the "name pattern" condition
   *
   * @param namePattern pattern to match user's names
   * @return result of query
   */
  public TreeMap<String, HashMap<String, String>> getUsers(String namePattern) {
    TreeMap<String, HashMap<String, String>> users = new TreeMap<>();
    String selectUsers = String.format("SELECT id, name, startDate FROM USERS WHERE name LIKE '%%%s%%'", namePattern);

    try {
      users = DatabaseManager.select(DatabaseManager.getConnection(), selectUsers);
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return users;
  }

}
