package com.bsm.bsm.account;

import com.bsm.bsm.admin.AdminModel;
import com.bsm.bsm.user.UserDAO;
import com.bsm.bsm.user.UserModel;
import com.bsm.bsm.utils.DateUtils;

import java.util.List;

public class AccountService implements AccountController{
    private final AccountDAO accountDAO;
    private final UserDAO userDAO;

    public AccountService() {
        this.accountDAO = new AccountDAO();
        this.userDAO = new UserDAO();
    }


    @Override
    public void add(UserModel account) {

    }

    @Override
    public UserModel search(String accountId) {
        return null;
    }

    @Override
    public AdminModel view(String excludedUserId) {
        List<UserModel> users = accountDAO.getAllUsersInfo(excludedUserId);
        return new AdminModel(users);
    }

    @Override
    public boolean update(String id, String fullName, String telephone, String dob, String address) {
        String formattedDOB = DateUtils.formatDOB(dob);
        return userDAO.updateProfile(id, fullName, telephone, formattedDOB, address);
    }

    @Override
    public void setActive(String accountId, boolean active) {

    }

    @Override
    public void sort() {

    }
}
