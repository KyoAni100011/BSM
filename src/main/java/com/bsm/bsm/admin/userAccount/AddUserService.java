package com.bsm.bsm.admin.userAccount;


import com.bsm.bsm.admin.AdminModel;
import com.bsm.bsm.user.UserModel;
import com.bsm.bsm.user.UserSingleton;

import java.util.List;

public class AddUserService {
    private final AddUserDAO addUserDAO;

    public AddUserService() {
        addUserDAO = new AddUserDAO();
    }

    public List<UserModel> getUsers() {
        AdminModel admin = (AdminModel) UserSingleton.getInstance().getUser();
        return admin.viewUsers();
    }

    public boolean isAdmin(String email) {
        return email.endsWith(".admin@bms.com");
    }

    public boolean hasUserExist(String email) {
        List<UserModel> users = getUsers();
        for (var user: users) {
            if (user.getEmail().equals(email)) return true;
        }
        return addUserDAO.checkEmailExists(email);
    }

    public boolean addUser(String name, String dob, String email, String address,String password) {
        String role = isAdmin(email) ? "admin" : "employee";
        boolean result = addUserDAO.addUser(name, dob, email, address, (password), role);
        if (result) {
            AdminModel admin = (AdminModel) UserSingleton.getInstance().getUser();
            admin.add(getUserInfo(email));
            UserSingleton.getInstance().setUser(admin);
        }
        return result;
    }

    public UserModel getUserInfo(String email) {
        String role = isAdmin(email) ? "admin" : "employee";
        return addUserDAO.getUserInfo(email, role);
    }

    private String hashPassword(String password) {
        return addUserDAO.hashPassword(password);
    }
}
