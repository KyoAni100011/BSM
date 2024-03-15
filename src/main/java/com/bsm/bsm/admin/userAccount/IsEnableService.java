package com.bsm.bsm.admin.userAccount;

public class IsEnableService {
    private static IsEnableDAO isEnableDAO = new IsEnableDAO();

    public IsEnableService() {
        isEnableDAO = new IsEnableDAO();
    }

    public static boolean enableUser(String userId) {
        try {
            return isEnableDAO.enableUser(userId);
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean disableUser(String userId) {
        try {
            return isEnableDAO.disableUser(userId);
        } catch (Exception e) {
            return false;
        }
    }
}
