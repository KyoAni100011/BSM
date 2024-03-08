package com.bsm.bsm.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/book_store_db";
    private static final String USERNAME = System.getenv("USERNAME");
    private static final String PASSWORD = System.getenv("PASSWORD");

    // Singleton instance
    private static Connection connection;

    // Private constructor to prevent instantiation
    private DatabaseConnection() {
    }

    // Method to get the connection instance
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {

            connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
        }
        return connection;
    }

    // Method to execute a query
    public static void executeQuery(String query, QueryResultHandler handler, Object... parameters) {
        try (Connection connection = getConnection()) {
            System.out.println("Connected to the database!");

            try (var preparedStatement = connection.prepareStatement(query)) {
                // Set parameters for prepared statement
                for (int i = 0; i < parameters.length; i++) {
                    preparedStatement.setObject(i + 1, parameters[i]);
                }

                try (var resultSet = preparedStatement.executeQuery()) {
                    handler.handleResult(resultSet);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to execute an update
    public static int executeUpdate(String query, Object... parameters) {
        int linesAffected = 0;
        try (Connection connection = getConnection()) {
            System.out.println("Connected to the database!");

            try (var preparedStatement = connection.prepareStatement(query)) {
                // Set parameters for prepared statement
                for (int i = 0; i < parameters.length; i++) {
                    preparedStatement.setObject(i + 1, parameters[i]);
                }

                linesAffected = preparedStatement.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return linesAffected;
    }
}
