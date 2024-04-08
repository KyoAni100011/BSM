package com.bsm.bsm.publisher;

public class Publisher {
    private String id;
    private String name;

    private boolean isEnabled;

    private String address;

    public Publisher() {
        // Default constructor
    }

    public Publisher(String id, String name, String address, boolean isEnabled) {
        this.id = id;
        this.name = name;
        this.isEnabled = isEnabled;
        this.address = address;
    }

    public Publisher(String name, String address) {
        this("", name, address, true);
    }

    public Publisher(String id, String name, boolean isEnabled) {
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

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
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

    @Override
    public String toString() {
        return "Publisher{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", isEnabled=" + isEnabled +
                ", address=" + address +
                '}';
    }
}
