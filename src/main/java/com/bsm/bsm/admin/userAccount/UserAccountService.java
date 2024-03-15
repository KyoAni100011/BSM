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
        if (column.equals("Last login")) {
            column = "lastLogin";
        }
        if (column.equals("Action")) {
            column = "isEnabled";
        }

        List<UserModel> listUsers = userAccountDAO.getAllUsersInfo(excludedUserId, sortOrder, column);
            AdminModel tempAdmin = new AdminModel((AdminModel)UserSingleton.getInstance().getUser(), listUsers);
            UserSingleton.getInstance().setUser(tempAdmin);
        return listUsers;
    }
}
