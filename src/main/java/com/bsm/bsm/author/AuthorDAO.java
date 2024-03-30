package com.bsm.bsm.author;

import com.bsm.bsm.database.DatabaseConnection;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class AuthorDAO {
    public boolean update(Author author) {
        // Implement update logic
        return true;
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

    public boolean updateAuthor (String id, String newName, String introduction) {
        String QUERY_AUTHOR = "update author set name = ?, introduction = ? where id = ?";
        int rowsAffected = DatabaseConnection.executeUpdate(QUERY_AUTHOR, newName, introduction, id);
        return rowsAffected > 0;
    }

    public Author getAuthorById(String id) {
        AtomicReference<Author> author = new AtomicReference<>();
        String QUERY_AUTHOR = "select name, introduction, isEnabled from author where id = ?";
        DatabaseConnection.executeQuery(QUERY_AUTHOR, resultSet -> {
            if (resultSet != null && resultSet.next()) {
                String name = resultSet.getString("name");
                String introduction = resultSet.getString("introduction");
                boolean isEnabled = resultSet.getBoolean("isEnabled");
                author.set(new Author(id, name, introduction, isEnabled));
            }
        }, id);

        return author.get();
    }
    public Author getAuthorByName(String name) {
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
