package com.bsm.bsm.admin.profileSetting;

import com.bsm.bsm.database.DatabaseConnection;

public class EditProfileDAO {
    private final String QUERY_UPDATE_USER = "UPDATE user SET name = ?, dob = ?, telephone = ?, address = ? where id = ?";

    public boolean updateProfile(String id, String fullName, String telephone, String dob, String address) {
        try {
            int rowsAffected = DatabaseConnection.executeUpdate(QUERY_UPDATE_USER, fullName, dob, telephone, address, id);
            if (rowsAffected > 0) {
                System.out.println("Profile updated successfully");
                return true;
            } else {
                System.out.println("Profile update failed");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
