package yelp.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.TreeMap;

public class DatabaseManager {
  private static final String connectionString = "jdbc:mysql://159.65.76.14:3306/revidi?autoReconnect=true&useUnicode=true&characterEncoding=UTF-8&allowMultiQueries=true&useSSL=false&relaxAutoCommit=true";
  private static final String username = "root";
  private static final String password = "175d2d6cdeccdc088998b6b16cab40476e5fcbce414b4620";
  private Connection connection = null;

  /**
   * Establish a connection to the database
   *
   * @return newly created connection
   * @throws ClassNotFoundException
   * @throws IllegalAccessException
   * @throws InstantiationException
   */
  public Connection connect()
      throws ClassNotFoundException, IllegalAccessException, InstantiationException {
    Class.forName("com.mysql.jdbc.Driver").newInstance();

    try {
      connection = DriverManager.getConnection(connectionString, username, password);
    } catch (Exception e) {
      e.printStackTrace();
    }

    return connection;
  }

  /**
   * Closes the connection if it exists.
   *
   * (note) always close the connection after use.
   */
  public void closeConnection() {
    if (connection != null) {
      try {
        connection.close();
      } catch (Exception e) {
        System.out.println(e.toString());
      }
    }
  }

  /**
   * Generic method for SQL "SELECT" operation
   *
   * @param connection the connection to use
   * @param selectQuery the query
   * @return the result of the query operation
   */
  public TreeMap<String, HashMap<String, String>> select(Connection connection, String selectQuery) {
    TreeMap<String, HashMap<String, String>> selectResult = new TreeMap<>();
    String[] keys = selectQuery.replace(" ", "").replace("SELECT", "").split("FROM")[0].split(",");

    try {
      PreparedStatement stmt = connection.prepareStatement(selectQuery);
      ResultSet rs = stmt.executeQuery();
      while (rs.next()) {
        HashMap<String, String> eachResult = new HashMap<>();
        for (int i = 1; i < keys.length; i++) {
          eachResult.put(keys[i], rs.getString(i + 1));
        }
        selectResult.put(rs.getString(1), eachResult);
      }

    } catch (SQLException sqlExc) {
      System.out.println(sqlExc.getMessage());
    }

    return selectResult;
  }


}
