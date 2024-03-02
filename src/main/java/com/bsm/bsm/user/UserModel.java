package com.bsm.bsm.user;

public class UserModel {

    private String id;
    private String name;
    private String email;
    private String password;
    private String dob;
    private boolean isEnabled;
    private String token;

    public UserModel(String id, String name, String email, String password, String dob, boolean isEnabled) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public UserModel getProfile() {
        return new UserModel(id, name, email, password, dob, isEnabled);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String tokenValue) {
        this.token = tokenValue;
    }

    public void setProfile(String name, String email, String password, String dob) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.dob = dob;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", dob='" + dob + '\'' +
                ", isEnabled=" + isEnabled +
                ", token='" + token + '\'' +
                '}';
    }
}
