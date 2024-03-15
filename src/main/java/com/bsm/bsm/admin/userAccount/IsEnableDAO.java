package com.bsm.bsm.admin.userAccount;

import com.bsm.bsm.database.DatabaseConnection;

public class IsEnableDAO {
    public boolean enableUser(String userId) {
        try {
            String ENABLE_USER_QUERY = "UPDATE user SET isEnabled = 1 WHERE id = ?";
            DatabaseConnection.executeUpdate(ENABLE_USER_QUERY, userId);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean disableUser(String userId) {
        try {
            String DISABLE_USER_QUERY = "UPDATE user SET isEnabled = 0 WHERE id = ?";
            DatabaseConnection.executeUpdate(DISABLE_USER_QUERY, userId);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}