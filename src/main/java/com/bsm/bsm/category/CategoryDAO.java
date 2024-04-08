package com.bsm.bsm.category;

import com.bsm.bsm.database.DatabaseConnection;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class CategoryDAO {

    public Category getCategoryById(String id) {
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

    public Category getCategoryByName(String name) {
        AtomicReference<Category> category = new AtomicReference<>();
        String QUERY_CATEGORY = "select * from category where name = ?";
        DatabaseConnection.executeQuery(QUERY_CATEGORY, resultSet -> {
            if (resultSet != null && resultSet.next()) {
                String id = resultSet.getString("id");
                String description = resultSet.getString("description");
                boolean isEnabled = resultSet.getBoolean("isEnabled");
                category.set(new Category(id, name, description, isEnabled));
            }
        }, name);
        return category.get();
    }


    public boolean updateCategory(String id, String newName, String description) {
        String QUERY_CATEGORY = "update category set name = ?, description = ? where id = ?";
        int rowsAffected = DatabaseConnection.executeUpdate(QUERY_CATEGORY, newName, description, id);
        return rowsAffected > 0;
    }

    public Category search(String keyword) {
        // Implement search logic
        return null;
    }

    public boolean checkCategoryExists(String name, String id) {
        AtomicBoolean hasExisted = new AtomicBoolean(false);
        String QUERY_CHECK_CATEGORY = "select 1 from category where name = ? and id != ?";
        DatabaseConnection.executeQuery(QUERY_CHECK_CATEGORY, resultSet -> {
            if (resultSet != null && resultSet.next()) {
                hasExisted.set(true);
            }
        }, name, id);
        return hasExisted.get();
    }

    public boolean addCategory(String name, String description) {
        String QUERY_ADD_CATEGORY = "insert into category (name, description) values (?, ?)";
        int rowsAffected = DatabaseConnection.executeUpdate(QUERY_ADD_CATEGORY, name, description);
        return rowsAffected > 0;
    }

    public List<Category> getAllCatogories() {
        String QUERY_GET_CATEGORIES = "select * from category";
        List<Category> categories = new ArrayList<>();

        DatabaseConnection.executeQuery(QUERY_GET_CATEGORIES, resultSet -> {
            while (resultSet != null && resultSet.next())  {
                String id = resultSet.getString("id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                boolean isEnabled = resultSet.getBoolean("isEnabled");
                categories.add(new Category(id, name, description, isEnabled));
            }
        });

        return categories;
    }
}
