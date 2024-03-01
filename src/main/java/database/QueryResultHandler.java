package main.java.database;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface QueryResultHandler {
    void handleResult(ResultSet resultSet) throws SQLException;
}