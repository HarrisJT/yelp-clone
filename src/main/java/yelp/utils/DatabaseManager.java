package yelp.utils;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.TreeMap;
import javax.sql.DataSource;


public class DatabaseManager {

  // private static final String connectionString = "jdbc:mysql://localhost:3306/revidi?autoReconnect=true&useUnicode=true&characterEncoding=UTF-8&allowMultiQueries=true&useSSL=false&relaxAutoCommit=true";
  private static final String connectionString = "jdbc:mysql://159.65.76.14:3306/revidi";
  private static final String username = "root";
  private static final String password = "175d2d6cdeccdc088998b6b16cab40476e5fcbce414b4620";
  private static DataSource dataSource;


  /*
    Setup the database
   */
  static {
    HikariConfig config = new HikariConfig();
    config.setJdbcUrl("com.mysql.jdbc.Driver");
    config.setJdbcUrl(connectionString);
    config.setUsername(username);
    config.setPassword(password);

    // Required
    config.addDataSourceProperty("autoReconnect", "true");
    config.addDataSourceProperty("useUnicode", "true");
    config.addDataSourceProperty("characterEncoding", "UTF-8");
    config.addDataSourceProperty("allowMultiQueries", "true");
    config.addDataSourceProperty("useSSL", "false");

    // Optimization
    config.addDataSourceProperty("cachePrepStmts", "true");
    config.addDataSourceProperty("prepStmtCacheSize", "250");
    config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

    // config.setConnectionTimeout(8000); // not sure we need yet
    // config.setAutoCommit(true);

    dataSource = new HikariDataSource(config);
  }

  public static DataSource getDataSource() {
    return dataSource;
  }

  public static Connection getConnection() throws SQLException {
    return dataSource.getConnection();
  }

  /**
   * Generic method for SQL "SELECT" operation
   *
   * @param connection the connection to use
   * @param selectQuery the query
   * @return the result of the query operation
   */
  public static TreeMap<String, HashMap<String, String>> select(Connection connection,
      String selectQuery) {
    TreeMap<String, HashMap<String, String>> selectResult = new TreeMap<>();
    String[] attributes = selectQuery.replace(" ", "").replace("SELECT", "").split("FROM")[0]
        .split(",");
    PreparedStatement stmt = null;
    ResultSet rs = null;

    try {
      stmt = connection.prepareStatement(selectQuery);
      rs = stmt.executeQuery();
      while (rs.next()) {
        HashMap<String, String> eachResult = new HashMap<>();
        for (int column = 1; column < attributes.length; column++) {
          eachResult.put(attributes[column], rs.getString(column + 1));
        }
        selectResult.put(rs.getString(1), eachResult);
      }

    } catch (SQLException sqlExc) {
      System.out.println(sqlExc.getMessage());
    } finally {
      try {
        closeAll(stmt, rs, connection);
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }

    return selectResult;
  }

  /**
   * Closes all database connection Should only be called after operations done
   *
   * @param ps PreparedStatement
   * @param rs ResultSet
   * @param conn Connection
   */
  public static void closeAll(PreparedStatement ps, ResultSet rs, Connection conn)
      throws SQLException {
    if (ps != null) {
      ps.close();
    }

    if (rs != null) {
      rs.close();
    }

    conn.close();
  }
}
