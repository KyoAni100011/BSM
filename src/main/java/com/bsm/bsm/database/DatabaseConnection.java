package com.bsm.bsm.database;

import java.sql.*;

public class DatabaseConnection {

    public interface TransactionCallback {
        void execute(Connection connection) throws SQLException;
    }

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/book_store_db";
    private static final String USERNAME = System.getenv("USERNAME");
    private static final String PASSWORD = System.getenv("PASSWORD");

    private static Connection connection;

    private DatabaseConnection() {
        // Private constructor to prevent instantiation
    }

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
        }
        return connection;
    }

    public static void executeQuery(String query, QueryResultHandler handler, Object... parameters) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            setParameters(preparedStatement, parameters);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                handler.handleResult(resultSet);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //use this method to execute a transaction
    public static void executeQuery(Connection connection, String query, QueryResultHandler handler, Object... parameters) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            setParameters(preparedStatement, parameters);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                handler.handleResult(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public static int executeUpdate(String query, Object... parameters) {
        int linesAffected = 0;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            setParameters(preparedStatement, parameters);
            linesAffected = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return linesAffected;
    }

    // use this method to execute a transaction
    public static int executeUpdate(Connection connection, String query, Object... parameters) {
        int linesAffected = 0;
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            setParameters(preparedStatement, parameters);
            linesAffected = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return linesAffected;
    }

    private static void setParameters(PreparedStatement preparedStatement, Object... parameters) throws SQLException {
        for (int i = 0; i < parameters.length; i++) {
            preparedStatement.setObject(i + 1, parameters[i]);
        }
    }
}
