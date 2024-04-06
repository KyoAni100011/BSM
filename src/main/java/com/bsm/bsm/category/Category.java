package com.bsm.bsm.category;

public class Category {
    private String id;
    private String name;
    private String description;
    private boolean isEnabled;

    public Category() {
        // Default constructor
    }

    public Category(String id, String name, String description, boolean isEnabled) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.isEnabled = isEnabled;
    }

    public Category(String id, String name, boolean isEnabled) {
        this(id, name, "", isEnabled);
    }

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", isEnabled=" + isEnabled +
                '}';
    }
}
