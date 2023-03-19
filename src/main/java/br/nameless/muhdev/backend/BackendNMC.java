package br.nameless.muhdev.backend;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;
import java.sql.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class BackendNMC {

  private Connection connection;
  private final ExecutorService executor;
  private final String host;
  private final String port;
  private final String database;
  private final String username;
  private final String password;

	public static BackendNMC getBackend() {
		return backend;
	}

	public static BackendNMC backend;

  public BackendNMC(String host, String port, String database, String username, String password) {

    this.host = host;
    this.port = port;
    this.database = database;
    this.username = username;
    this.password = password;


    this.executor = Executors.newCachedThreadPool();
    openConnection();



  }

  public Connection getConnection() {
    if (!isConnected()) {
      openConnection();

    }

    return connection;
  }

  public void closeConnection() {
    if (isConnected()) {
      try {
        connection.close();
      } catch (SQLException e) {
        System.out.println("Cannot close MySQL connection: " + e);
      }
    }
  }

  public boolean isConnected() {
    try {
      return connection != null && !connection.isClosed() && connection.isValid(5);
    } catch (SQLException e) {
      System.out.println("MySQL error: " + e);
    }

    return false;
  }

  public void openConnection() {
    if (!isConnected()) {
      try {
        boolean bol = connection == null;
        connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database
                        + "?verifyServerCertificate=false&useSSL=false&allowPublicKeyRetrieval=true&useUnicode=yes&characterEncoding=UTF-8",
                username, password);
        if (bol) {
          System.out.print("Conectado");
          return;
        }

        System.out.print("Reconectado com o MySQL!");
      } catch (SQLException e) {
        System.out.println("Cannot open MySQL connection: " + e);
      }
    }
  }

  public void update(String sql, Object... vars) {
    try {
      PreparedStatement ps = prepareStatement(sql, vars);
      ps.execute();
      ps.close();
    } catch (SQLException e) {
      System.out.println("Cannot execute SQL: " + e);
    }
  }

  public void execute(String sql, Object... vars) {
    executor.execute(() -> update(sql, vars));
  }

  public PreparedStatement prepareStatement(String query, Object... vars) {
    try {
      PreparedStatement ps = getConnection().prepareStatement(query);
      for (int i = 0; i < vars.length; i++) {
        ps.setObject(i + 1, vars[i]);
      }
      return ps;
    } catch (SQLException e) {
      System.out.println("Cannot Prepare Statement: " +e);
    }

    return null;
  }

  public CachedRowSet query(String query, Object... vars) {
    CachedRowSet rowSet = null;
    try {
      Future<CachedRowSet> future = executor.submit(() -> {
        try {
          PreparedStatement ps = prepareStatement(query, vars);

          ResultSet rs = ps.executeQuery();
          CachedRowSet crs = RowSetProvider.newFactory().createCachedRowSet();
          crs.populate(rs);
          rs.close();
          ps.close();

          if (crs.next()) {
            return crs;
          }
        } catch (Exception e) {
          System.out.println("Cannot execute Query: " + e);
        }

        return null;
      });

      if (future.get() != null) {
        rowSet = future.get();
      }
    } catch (Exception e) {
      System.out.println("Cannot call FutureTask: " + e);
    }

    return rowSet;
  }
}
