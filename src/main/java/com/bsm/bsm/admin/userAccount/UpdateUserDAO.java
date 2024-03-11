package com.bsm.bsm.admin.userAccount;

import com.bsm.bsm.database.DatabaseConnection;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;

public class UpdateUserDAO {
    private final String QUERY_UPDATE_USER = "UPDATE user SET name = ?, dob = ?, telephone = ?, address = ? where id = ?";

    public static void main(String[] args) {

//        updateProfile("Minh Thu", "0707070707", "01/05/2003", "123 abc");
    }

    public boolean updateProfile(String id, String fullName, String telephone, String dob, String address) {
        try {
            int rowsAffected = DatabaseConnection.executeUpdate(QUERY_UPDATE_USER, fullName, dob, telephone, address, id);
            if (rowsAffected > 0) {
                System.out.println("User profile updated successfully");
                return true;
            } else {
                System.out.println("User profile update failed");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
