package com.bsm.bsm.employee.profileSetting;

import com.bsm.bsm.database.DatabaseConnection;

public class EditProfileDAO {
    private static final String UPDATE_USER_QUERY = "UPDATE user SET name = ?, dob = ?, telephone = ?, address = ? WHERE id = ?";

    public boolean updateProfile(String id, String fullName, String telephone, String dob, String address) {
        int rowsAffected = DatabaseConnection.executeUpdate(UPDATE_USER_QUERY, fullName, dob, telephone, address, id);
        return rowsAffected > 0;
    }
}
