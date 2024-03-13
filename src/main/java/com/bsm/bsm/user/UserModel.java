package com.bsm.bsm.user;

public class UserModel {
    private String id;
    private String name;
    private String email;
    private String dob;
    private String address;
    private String phone;
    private boolean isEnabled;
    private String lastLogin;

    public UserModel(String id, String name, String email, String dob, String phone, String address, boolean isEnabled, String lastLogin) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.dob = dob;
        this.phone = phone;
        this.address = address;
        this.isEnabled = isEnabled;
        this.lastLogin = lastLogin;
    }

    public UserModel(String id, String name, String email, String dob, String phone, String address, boolean isEnabled) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.dob = dob;
        this.phone = phone;
        this.address = address;
        this.isEnabled = isEnabled;
    }

    // temp code


    public UserModel() {

    }

    public String getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(String s) {
    }

    public String getId() {
        return id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
                ", phone='" + phone + '\'' +
                ", isEnabled=" + isEnabled +
                '}';
    }
}
