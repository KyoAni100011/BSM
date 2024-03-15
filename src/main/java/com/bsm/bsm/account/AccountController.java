package com.bsm.bsm.account;

import com.bsm.bsm.admin.AdminModel;
import com.bsm.bsm.user.UserModel;

import java.util.List;

public interface AccountController {
    public void add(UserModel account);
    public List<UserModel> search(String keyword, String type);
    public AdminModel view(String excludedUserId);
    public boolean update(String id, String fullName, String telephone, String dob, String address);
    public void sort();
}
