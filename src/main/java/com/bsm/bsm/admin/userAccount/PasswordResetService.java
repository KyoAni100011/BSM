package com.bsm.bsm.admin.userAccount;

public class PasswordResetService {

    private final PasswordResetDAO passwordResetDAO;

    public PasswordResetService() {
        passwordResetDAO = new PasswordResetDAO();
    }

    public boolean updatePassword(String adminID, String userID, String password) {
        if (!isAdmin(adminID)) {
            return false;
        }

        if (password.isEmpty()) {
            return updatePasswordUsingDOB(userID);
        }

        return passwordResetDAO.updatePassword(hashPassword(password), userID);
    }

    public boolean isAdmin(String id) {
        return passwordResetDAO.isAdmin(id);
    }

    public boolean hasUserExist(String email) {
        return passwordResetDAO.hasUserExist(email);
    }

    private boolean updatePasswordUsingDOB(String email) {
        return passwordResetDAO.updatePasswordUsingDOB(email);
    }

    private String hashPassword(String password) {
        return passwordResetDAO.hashPassword(password);
    }
}
