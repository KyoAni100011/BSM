package com.bsm.bsm.admin.userAccount;

import com.bsm.bsm.user.UserModel;

import java.util.List;

public class UserAccountService {
    private final UserAccountDAO userAccountDAO;

    public UserAccountService() {
        this.userAccountDAO = new UserAccountDAO();
    }

    public List<UserModel> getAllUsersInfo() {
        return userAccountDAO.getAllUsersInfo();
    }
}
