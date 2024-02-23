package main.java.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/book_store_db";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "123456";

    // Singleton instance
    private static Connection connection;

    // Private constructor to prevent instantiation
    private DatabaseConnection() {
    }

    // Method to get the connection instance
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            // Create a new connection if it doesn't exist or is closed
            connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
        }
        return connection;
    }

    // Method to execute a query
    public static void executeQuery(String query, QueryResultHandler handler) {
        try (Connection connection = getConnection()) {
            System.out.println("Connected to the database!");

            try (var statement = connection.createStatement();
                    var resultSet = statement.executeQuery(query)) {

                handler.handleResult(resultSet);

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
