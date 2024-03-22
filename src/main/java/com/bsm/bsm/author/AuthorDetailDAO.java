package com.bsm.bsm.author;

import com.bsm.bsm.database.DatabaseConnection;

import java.util.concurrent.atomic.AtomicReference;

public class AuthorDetailDAO {
    public Author getAuthor(String id) {
        AtomicReference<Author> author = new AtomicReference<>();
        String QUERY_AUTHOR = "select * from author where id = ?";
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
}
