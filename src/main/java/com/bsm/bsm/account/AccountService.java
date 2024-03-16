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
    public List<UserModel> search(String keyword, String type) {
        return switch (type.toLowerCase().trim()) {
            case "id" -> accountDAO.getAllUsersInfoById(keyword);
            case "name" -> accountDAO.getAllUsersInfoByName(keyword);
            case "email" -> accountDAO.getAllUsersInfoByEmail(keyword);
            default -> null;
        };
    }

    @Override
    public List<UserModel> view(String excludedUserId) {
        return accountDAO.getAllUsersInfo(excludedUserId);
    }

    @Override
    public boolean update(String id, String fullName, String telephone, String dob, String address) {
        String formattedDOB = DateUtils.formatDOB(dob);
        return userDAO.updateProfile(id, fullName, telephone, formattedDOB, address);
    }

    @Override
    public void sort() {

    }
}
