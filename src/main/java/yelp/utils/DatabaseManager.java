package yelp.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseManager {
  private static final String connectionString = "jdbc:mysql://159.65.76.14:3306/revidi?autoReconnect=true&useUnicode=true&characterEncoding=UTF-8&allowMultiQueries=true&useSSL=false&relaxAutoCommit=true";
  private static final String username = "root";
  private static final String password = "175d2d6cdeccdc088998b6b16cab40476e5fcbce414b4620";
  private Connection connection = null;
  private static DatabaseManager database;

  private void connect()
      throws ClassNotFoundException, IllegalAccessException, InstantiationException {
    Class.forName("com.mysql.jdbc.Driver").newInstance();

    try {
      connection = DriverManager.getConnection(connectionString, username, password);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static DatabaseManager getDatabase() {
    if (database == null) {
      database = new DatabaseManager();
      try {
        database.connect();
      } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex){
        Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
      }
    }

    return database;
  }

  public void closeConnection() {
    if (connection != null) {
      try {
        connection.close();
      } catch (Exception e) {
        System.out.println(e.toString());
      }
    }
  }

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
