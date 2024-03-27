package com.bsm.bsm.category;

import com.bsm.bsm.commonInterface.*;

import java.util.List;

public class CategoryService implements Activable, Searchable<Category>, Sortable<Category>, Addable<Category> {
    private CategoryDAO categoryDAO = null;

    public CategoryService() {
        this.categoryDAO = new CategoryDAO();
    }

    public boolean update(String id, String name, String description) {
        return categoryDAO.updateCategory(id, name, description);
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
    public boolean add(Category item) {
        // Implement add logic
        return true;
    }

    @Override
    public boolean setEnable(boolean state) {

        return state;
    }

    public Category getCategoryById(String id) {
        return categoryDAO.getCategoryById(id);
    }

    public boolean checkCategoryExist(String name) {
        return categoryDAO.checkCategoryExist(name);
    }

    public boolean addCategory(String name, String description) {
        return categoryDAO.addCategory(name, description);
    }
}
