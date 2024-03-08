package com.bsm.bsm.admin.profileSetting;

public class ChangePasswordService {

    private final ChangePasswordDAO changePasswordDAO;

    public ChangePasswordService() {
        this.changePasswordDAO = new ChangePasswordDAO();
    }

    public boolean changeUserPassword(String email, String currentPassword, String newPassword) {
        return changePasswordDAO.changePassword(email, currentPassword, newPassword);
    }
}
