package com.bsm.bsm.category;

import com.bsm.bsm.database.DatabaseConnection;

import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;

public class CategoryDAO {
    public void update(Category category) {
        // Implement update logic
    }

    public void add(Category category) {
        // Implement add logic
    }

    public Category search(String keyword) {
        // Implement search logic
        return null;
    }

    public boolean checkCategoryExist(String name) {
        AtomicBoolean hasExisted = new AtomicBoolean(false);
        String QUERY_CHECK_CATEGORY = "select 1 from category where name = ?";
        DatabaseConnection.executeQuery(QUERY_CHECK_CATEGORY, resultSet -> {
            if (resultSet != null && resultSet.next()) {
                hasExisted.set(true);
            }
        }, name);
        return hasExisted.get();
    }

    public boolean addCategory(String name, String description) {
        String QUERY_ADD_CATEGORY = "insert into category (name, description) values (?, ?)";
        int rowsAffected = DatabaseConnection.executeUpdate(QUERY_ADD_CATEGORY, name, description);
        return rowsAffected > 0;
    }
}
