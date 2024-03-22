package com.bsm.bsm.author;

import com.bsm.bsm.database.DatabaseConnection;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class AuthorDAO {
    public void update(Author author) {
        // Implement update logic
    }

    public void add(Author author) {
        // Implement add logic
    }

    public Author search(String keyword) {
        // Implement search logic
        return null;
    }

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

    public boolean updateAuthor (String oldName, String newName, String introduction) {
        String QUERY_AUTHOR = "update author set name = ?, introduction = ? where name = ?";
        int rowsAffected = DatabaseConnection.executeUpdate(QUERY_AUTHOR, newName, introduction, oldName);
        return rowsAffected > 0;
    }

    public Author getAuthor(String name) {
        AtomicReference<Author> author = new AtomicReference<>();
        String QUERY_AUTHOR = "select * from author where name = ?";
        DatabaseConnection.executeQuery(QUERY_AUTHOR, resultSet -> {
            if (resultSet != null && resultSet.next()) {
                String id = resultSet.getString("id");
                String introduction = resultSet.getString("introduction");
                boolean isEnabled = resultSet.getBoolean("isEnabled");
                author.set(new Author(id, name, introduction, isEnabled));
            }
        }, name);

        return author.get();
    }
}
