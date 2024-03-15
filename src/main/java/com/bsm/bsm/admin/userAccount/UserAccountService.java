package com.bsm.bsm.admin.userAccount;

import com.bsm.bsm.admin.AdminModel;
import com.bsm.bsm.user.UserModel;
import com.bsm.bsm.user.UserSingleton;

import java.util.List;

public class UserAccountService {
    private final UserAccountDAO userAccountDAO;

    public UserAccountService() {
        this.userAccountDAO = new UserAccountDAO();
    }

    private List<UserModel> getUsers() {
        AdminModel admin = (AdminModel) UserSingleton.getInstance().getUser();
        return admin.viewUsers();
    }

    public List<UserModel> getAllUsersInfo(String excludedUserId, String sortOrder, String column) {
        List<UserModel> listUsers = getUsers();

        if (listUsers.isEmpty()) {
            listUsers = userAccountDAO.getAllUsersInfo(excludedUserId, sortOrder, column);
            AdminModel tempAdmin = new AdminModel((AdminModel)UserSingleton.getInstance().getUser(), listUsers);
            UserSingleton.getInstance().setUser(tempAdmin);
            System.out.println("Successfully fetched users from database");
        }

        return listUsers;
    }
}
