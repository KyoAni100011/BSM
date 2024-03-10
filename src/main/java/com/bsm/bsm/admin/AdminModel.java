package com.bsm.bsm.admin;

import com.bsm.bsm.user.UserModel;

import java.util.ArrayList;
import java.util.List;

public class AdminModel extends UserModel {

    private List<UserModel> users = new ArrayList<>();

    public AdminModel(String id, String name, String email, String dob, String phone, String address, boolean isEnabled) {
        super(id, name, email, dob, phone, address, isEnabled);
    }

    public AdminModel() {
        super();
    }

    //    View a list of user accounts. Search or sort on the list.
    public List<UserModel> viewUsers() {
        return users;
    }

    public boolean searchUser(String name) {
        //TODO
        return true;
    }

    //sort user
    private List<UserModel> sortUsers() {
        //TODO
        return null;
    }

    public UserModel addNewUser(String id, String name, String email, String dob, String phone, String address, boolean isEnabled) {
        UserModel newUser= new UserModel(id, name, email, dob, phone, address, isEnabled);
        users.add(newUser);
        return newUser;
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
    }


}