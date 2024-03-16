package com.bsm.bsm.category;

import com.bsm.bsm.commonInterface.*;
import java.util.List;

public class CategoryService implements Activable, Searchable<Category>, Sortable<Category>, Updatable<Category>, Addable<Category> {
    private CategoryDAO categoryDAO = null;

    public CategoryService() {
        this.categoryDAO = new CategoryDAO();
    }

    @Override
    public void update(Category item) {
        categoryDAO.update(item);
    }

    @Override
    public List<Category> sort(List<Category> categories, boolean isAscending, String column) {
        // Implement sorting logic
        return null;
    }

    @Override
    public List<Category> search(String keyword) {
        // Implement search logic
        return null;
    }

    @Override
    public void add(Category item) {
        // Implement add logic
    }

    @Override
    public void setEnable(boolean state) {

    }
}
