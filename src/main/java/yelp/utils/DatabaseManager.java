package yelp.utils;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    config.setJdbcUrl(connectionString);
    config.setUsername(username);
    config.setPassword(password);

    // Required
    config.addDataSourceProperty("autoReconnect", "true");
    config.addDataSourceProperty("useUnicode", "true");
    //config.addDataSourceProperty("characterEncoding", "UTF-8");
    config.addDataSourceProperty("allowMultiQueries", "true");
    config.addDataSourceProperty("useSSL", "false");

    // Optimization
    config.addDataSourceProperty("cachePrepStmts", "true");
    config.addDataSourceProperty("prepStmtCacheSize", "250");
    config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
    config.addDataSourceProperty("useServerPrepStmts", "true");
    config.addDataSourceProperty("useLocalSessionState", "true");
    config.addDataSourceProperty("rewriteBatchedStatements", "true");
    config.addDataSourceProperty("cacheResultSetMetadata", "true");
    config.addDataSourceProperty("cacheServerConfiguration", "true");
    config.addDataSourceProperty("elideSetAutoCommits", "true");
    config.addDataSourceProperty("maintainTimeStats", "false");

    dataSource = new HikariDataSource(config);
  }

  public static DataSource getDataSource() {
    return dataSource;
  }

  public static Connection getConnection() throws SQLException {
    return dataSource.getConnection();
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
