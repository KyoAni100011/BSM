package com.bsm.bsm.category;

import com.bsm.bsm.commonInterface.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class CategoryService implements Activable, Searchable<Category>, Sortable<Category>, Addable<Category> {
    private CategoryDAO categoryDAO = null;

    public CategoryService() {
        this.categoryDAO = new CategoryDAO();
    }

    public boolean update(String id, String name, String description) {
        return categoryDAO.updateCategory(id, name, description);
    }

    public Category getCategory(String id) {
        return categoryDAO.getCategoryById(id);
    }

    public Category getCategoryByName(String name) {
        return categoryDAO.getCategoryByName(name);
    }

    public boolean isEnabled(String id) {
        return getCategory(id).isEnabled();
    }

    @Override
    public List<Category> sort(List<Category> categories, boolean isAscending, String column) {
        String finalColumn = column.toLowerCase();

        List<Category> sortedCategories = new ArrayList<>(categories);
        Comparator<Category> comparator = (category1, category2) -> {
            switch (finalColumn) {
                case "id" -> {
                    return Comparator.comparing(Category::getId).compare(category1, category2);
                }
                case "name" -> {
                    return Comparator.comparing(Category::getName).compare(category1, category2);
                }
                case "description" -> {
                    return Comparator.comparing(Category::getDescription).compare(category1, category2);
                }
                case "action" -> {
                    return Comparator.comparing(Category::isEnabled).compare(category1, category2);
                }
                default -> {
                    return 0;
                }
            }
        };

        if (!isAscending) {
            comparator = comparator.reversed();
        }

        return sortedCategories.stream().sorted(comparator).collect(Collectors.toList());
    }

    @Override
    public List<Category> search(String keyword) {
        String finalKeyword = keyword.toLowerCase();
        List<Category> categories = getAllCategories();

        return categories.stream()
                .filter(category ->
                        category.getId().contains(finalKeyword) ||
                        category.getName().toLowerCase().contains(finalKeyword) ||
                        category.getDescription().toLowerCase().contains(finalKeyword))
                .collect(Collectors.toList());
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

    public boolean checkCategoryExists(String name, String id) {
        return categoryDAO.checkCategoryExists(name, id);
    }

    public boolean checkCategoryExists(String name) {
        return checkCategoryExists(name, "");
    }

    public boolean addCategory(String name, String description) {
        return categoryDAO.addCategory(name, description);
    }

    public List<Category> getAllCategories() {
        return categoryDAO.getAllCatogories();
    }
    public boolean disableCategory(String categoryId) {
        try {
            return categoryDAO.disableCategory(categoryId);
        } catch (Exception e) {
            return false;
        }
    }
    public boolean enableCategory(String categoryId) {
        try {
            return categoryDAO.enableCategory(categoryId);
        } catch (Exception e) {
            return false;
        }
    }
}
