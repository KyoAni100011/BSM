package com.bsm.bsm.admin.userAccount;

public class PasswordResetService {

    private final PasswordResetDAO passwordResetDAO;

    public PasswordResetService() {
        passwordResetDAO = new PasswordResetDAO();
    }

    public void updatePassword(String adminID, String userEmail, String password) {
        System.out.println(userEmail);
        if (!isAdmin(adminID)) {
            return;
        }

        if (password.isEmpty()) {
            updatePasswordUsingDOB(userEmail);
            return;
        }

        passwordResetDAO.updatePassword(hashPassword(password), userEmail);
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
