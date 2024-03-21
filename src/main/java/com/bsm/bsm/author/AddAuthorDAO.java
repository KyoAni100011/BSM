package com.bsm.bsm.author;

import com.bsm.bsm.database.DatabaseConnection;

import java.util.concurrent.atomic.AtomicBoolean;

public class AddAuthorDAO {

    public boolean checkAuthorExists(String name) {
        String QUERY_AUTHOR = "select 1 from author where name = ?";
        AtomicBoolean hasExisted = new AtomicBoolean(false);

        DatabaseConnection.executeQuery(QUERY_AUTHOR, resultSet -> {
            if (resultSet != null && resultSet.next()) {
                hasExisted.set(true);
            }
        }, name);

        return hasExisted.get();
    }

    public boolean addAuthor (String name, String introduction) {
        String QUERY_AUTHOR = "insert into author (name, introduction) values (?, ?)";
        int rowsAffected = DatabaseConnection.executeUpdate(QUERY_AUTHOR, name, introduction);
        return rowsAffected > 0;
    }
}
