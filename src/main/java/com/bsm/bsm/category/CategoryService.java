package com.bsm.bsm.category;

import com.bsm.bsm.commonInterface.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class CategoryService implements Activable, Searchable<Category>, Sortable<Category>, Addable<Category>, Displayable<Category>, Updatable<Category> {
    private final CategoryDAO categoryDAO;

    public CategoryService() {
        this.categoryDAO = new CategoryDAO();
    }
    
    @Override
    public List<Category> display() {
        return categoryDAO.getAllCatogories();
    }

    @Override
    public List<Category> sort(List<Category> categories, boolean isAscending, String column) {
        String finalColumn = column.toLowerCase();

        List<Category> sortedCategories = new ArrayList<>(categories);
        Comparator<Category> comparator = (category1, category2) -> {
            switch (finalColumn) {
                case "id" -> {
                    int categoryId1 = Integer.parseInt(category1.getId());
                    int categoryId2 = Integer.parseInt(category2.getId());
                    return Integer.compare(categoryId1, categoryId2);
                }
                case "name" -> {
                    return Comparator.comparing(Category::getName).compare(category1, category2);
                }
                case "description" -> {
                    return Comparator.comparing(Category::getDescription).compare(category1, category2);
                }
                case "enable/disable" -> {
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
    public boolean update(Category item) {
        return categoryDAO.updateCategory(item.getId(), item.getName(), item.getDescription());
    }

    @Override
    public List<Category> search(String keyword) {
        String finalKeyword = keyword.toLowerCase();
        List<Category> categories = display();

        return categories.stream()
                .filter(category ->
                        category.getId().contains(finalKeyword) ||
                        category.getName().toLowerCase().contains(finalKeyword) ||
                        category.getDescription().toLowerCase().contains(finalKeyword))
                .collect(Collectors.toList());
    }

    @Override
    public boolean add(Category item) {
        return categoryDAO.addCategory(item.getName(), item.getDescription());
    }

    @Override
    public boolean setEnable(String id, boolean state) {
        return categoryDAO.setEnable(id, state);
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


    public Category getCategory(String id) {
        return categoryDAO.getCategoryById(id);
    }

    public Category getCategoryByName(String name) {
        return categoryDAO.getCategoryByName(name);
    }

    public boolean isEnabled(String id) {
        return getCategory(id).isEnabled();
    }

}
