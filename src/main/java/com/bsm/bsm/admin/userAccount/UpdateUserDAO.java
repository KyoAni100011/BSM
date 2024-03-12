package com.bsm.bsm.admin.userAccount;

import com.bsm.bsm.database.DatabaseConnection;

public class UpdateUserDAO {
    private final String UPDATE_USER_QUERY = "UPDATE user SET name = ?, dob = ?, telephone = ?, address = ? where id = ?";

    public boolean updateProfile(String id, String fullName, String telephone, String dob, String address) {
        int rowsAffected = DatabaseConnection.executeUpdate(UPDATE_USER_QUERY, fullName, dob, telephone, address, id);
        return rowsAffected > 0;
    }
}
