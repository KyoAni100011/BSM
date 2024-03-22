package com.bsm.bsm.author;

import com.bsm.bsm.database.DatabaseConnection;

import java.util.concurrent.atomic.AtomicReference;

public class UpdateAuthorDAO {

    public boolean updateAuthor (String id, String newName, String introduction) {
        String QUERY_AUTHOR = "update author set name = ?, introduction = ? where id = ?";
        int rowsAffected = DatabaseConnection.executeUpdate(QUERY_AUTHOR, newName, introduction, id);
        return rowsAffected > 0;
    }

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
