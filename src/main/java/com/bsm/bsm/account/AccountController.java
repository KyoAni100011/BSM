package com.bsm.bsm.account;

import com.bsm.bsm.admin.AdminModel;
import com.bsm.bsm.user.UserModel;

public interface AccountController {
    public void add(UserModel account);
    public UserModel search(String accountId);
    public AdminModel view(String excludedUserId);
    public boolean update(String id, String fullName, String telephone, String dob, String address);
    public void sort();
}
