package yelp.controller;

import java.sql.Connection;
import java.util.HashMap;
import java.util.TreeMap;
import yelp.utils.DatabaseManager;

public class UserController {
  private DatabaseManager db = new DatabaseManager(); // TODO: where should this be initialized?

  /**
   * Gets all users from database that meet the "name pattern" condition
   *
   * @param namePattern
   * @return result of query
   */
  public TreeMap<String, HashMap<String, String>> getUsers(String namePattern) {
    Connection conn;
    TreeMap<String, HashMap<String, String>> users = new TreeMap<>();
    String selectUsers = String.format("SELECT id, name, startDate FROM USERS WHERE name LIKE '%%%s%%'", namePattern);

    try {
      conn = db.connect();
      users = db.select(conn, selectUsers);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    } finally {
      db.closeConnection();
    }

    return users;
  }

}
