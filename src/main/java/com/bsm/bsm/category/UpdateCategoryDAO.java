package com.bsm.bsm.category;

import com.bsm.bsm.database.DatabaseConnection;

import java.util.concurrent.atomic.AtomicReference;

public class UpdateCategoryDAO {
    public boolean updateCategory(String id, String newName, String description) {
        String QUERY_CATEGORY = "update category set name = ?, description = ? where id = ?";
        int rowsAffected = DatabaseConnection.executeUpdate(QUERY_CATEGORY, newName, description, id);
        System.out.println("Rows affected: " + rowsAffected);
        return rowsAffected > 0;
    }

    public Category getCategory(String id) {
        AtomicReference<Category> category = new AtomicReference<>();
        String QUERY_CATEGORY = "select * from category where id = ?";
        DatabaseConnection.executeQuery(QUERY_CATEGORY, resultSet -> {
            if (resultSet != null && resultSet.next()) {
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                boolean isEnabled = resultSet.getBoolean("isEnabled");
                category.set(new Category(id, name, description, isEnabled));
            }
        }, id);

        return category.get();
    }
}
