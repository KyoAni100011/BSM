package com.bsm.bsm.admin.userAccount;

import com.bsm.bsm.admin.AdminModel;
import com.bsm.bsm.user.UserModel;

import java.util.List;

public class UserAccountService {
    private final UserAccountDAO userAccountDAO;

    public UserAccountService() {
        this.userAccountDAO = new UserAccountDAO();
    }



    public AdminModel getAllUsersInfo(String excludedUserId) {
        List<UserModel> users = userAccountDAO.getAllUsersInfo(excludedUserId);
        return new AdminModel(users);
    }
}
