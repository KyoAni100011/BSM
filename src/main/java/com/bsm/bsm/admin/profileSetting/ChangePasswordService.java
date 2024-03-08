package com.bsm.bsm.admin.profileSetting;

public class ChangePasswordService {

    private final ChangePasswordDAO changePasswordDAO;

    public ChangePasswordService() {
        this.changePasswordDAO = new ChangePasswordDAO();
    }

    public ChangePasswordDAO getChangePasswordDAO() {
        return changePasswordDAO;
    }
}
