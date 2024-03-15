package com.bsm.bsm.admin;

import com.bsm.bsm.commonInterface.Searchable;
import com.bsm.bsm.employee.EmployeeModel;
import com.bsm.bsm.user.UserModel;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AdminModel extends UserModel implements Searchable<UserModel> {
    private List<UserModel> users = new ArrayList<>();

    public AdminModel(String id, String name, String email, String dob, String phone, String address, boolean isEnabled, String lastLogin) {
        super(id, name, email, dob, phone, address, isEnabled, lastLogin);
    }

    public AdminModel(List<UserModel> users) {
        this.users = users;
    }

    public AdminModel(AdminModel adminModel, List<UserModel> users) {
        super(adminModel.getId(), adminModel.getName(), adminModel.getEmail(), adminModel.getDob(), adminModel.getPhone(), adminModel.getAddress(), adminModel.isEnabled(), adminModel.getLastLogin());
        this.users = users;
    }

    public AdminModel() {
        super();
    }

    //    View a list of user accounts. Search or sort on the list.
    public List<UserModel> viewUsers() {
        return users;
    }

    @Override
    public List<UserModel> search(String keyword) {
        return null;
    }

    //sort user
    private List<UserModel> sortUsers() {
        //TODO
        return null;
    }

    public void addNewUser(String id, String name, String email, String dob, String phone, String address, boolean isEnabled, String lastLogin) {
        UserModel newUser= new UserModel(id, name, email, dob, phone, address, isEnabled, lastLogin);
        users.add(newUser);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
    }

    public void resetPassword(String email, String newPassword) {
        List<UserModel> foundedUser = search(email);
        foundedUser.getFirst().setPassword(newPassword);
    }

}