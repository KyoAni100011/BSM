package com.bsm.bsm.author;

public class Author {
    private String id;
    private String name;
    private String introduction;
    private boolean isEnabled;

    public Author() {
        // Default constructor
    }

    public Author(String id, String name, String introduction, boolean isEnabled) {
        this.id = id;
        this.name = name;
        this.introduction = introduction;
        this.isEnabled = isEnabled;
    }

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

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", isEnabled=" + isEnabled +
                '}';
    }
}
