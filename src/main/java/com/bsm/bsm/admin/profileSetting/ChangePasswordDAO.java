package com.bsm.bsm.admin.profileSetting;


import com.bsm.bsm.database.DatabaseConnection;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.atomic.AtomicBoolean;

public class ChangePasswordDAO {

    public static void main(String[] args) throws SQLException {
        String email = "thu.admin@bms.com";
        String currentPassword = "01052003";
        String newPassword = "01062003";

        changePassword(email, currentPassword, newPassword);
    }

    private static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }

    public static boolean changePassword(String email, String currentPassword, String newPassword) {
        try  {
            //Validate the current password
            if (!validatePassword(email, currentPassword)) {
                return false;
            }

            //Change password
            String hashedNewPassword = hashPassword(newPassword);
            String QUERY_CHANGE_PASSWORD = "UPDATE user SET password = ? WHERE email= ?";
            int rowsAffected =  DatabaseConnection.executeUpdate(QUERY_CHANGE_PASSWORD, hashedNewPassword, email);
            if (rowsAffected > 0) {
                System.out.println("Password changed successfully");
                return true;
            } else {
                System.out.println("Password change failed");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    private static boolean validatePassword(String email, String currentPassword) throws SQLException {
        AtomicBoolean isValid = new AtomicBoolean(false);
        String QUERY_PASSWORD = "SELECT PASSWORD FROM user WHERE email = ?";

        DatabaseConnection.executeQuery(QUERY_PASSWORD, resultSet -> {
            if (resultSet.next()) {
                String storedPass = resultSet.getString("password").trim();

                //compare the current password with the password in the database
                if (BCrypt.checkpw(currentPassword, storedPass)) {
                    isValid.set(true);
                } else {
                    System.out.println("Password is incorrect");
                }
            } else {
                System.out.println("User not found");
            }
        }, email);

        return isValid.get();
    }
}
