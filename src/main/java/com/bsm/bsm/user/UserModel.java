package com.bsm.bsm.user;

public class UserModel {

    private String id;
    private String name;
    private String email;
    private String dob;
    private boolean isEnabled;

    public UserModel(String id, String name, String email, String dob, boolean isEnabled) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.dob = dob;
        this.isEnabled = isEnabled;
    }

    // Getters and setters

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public void setDisabled() {
        isEnabled = false;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", dob='" + dob + '\'' +
                ", isEnabled=" + isEnabled +
                '}';
    }
}
