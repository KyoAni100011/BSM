package com.bsm.bsm.category;

public class CategorySingleton {
    private static final CategorySingleton instance = new CategorySingleton();
    private Category category;

    private CategorySingleton() {

    }
    public static CategorySingleton getInstance() {
        return instance;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void destroyInstance() {
        instance.category = null;
    }
}
