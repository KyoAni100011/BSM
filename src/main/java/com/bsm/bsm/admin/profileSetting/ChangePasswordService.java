package com.bsm.bsm.admin.profileSetting;

public class ChangePasswordService {

    private final ChangePasswordDAO changePasswordDAO;

    public ChangePasswordService() {
        this.changePasswordDAO = new ChangePasswordDAO();
    }

    public boolean changeUserPassword(String id, String currentPassword, String newPassword) {
        if (validateCurrentPassword(id, currentPassword)) {
            return changePasswordDAO.changePassword(id, currentPassword, newPassword);
        }
        return false;
    }

    public boolean validateCurrentPassword(String id, String currentPassword) {
        try {
            return changePasswordDAO.validatePassword(id, currentPassword);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
