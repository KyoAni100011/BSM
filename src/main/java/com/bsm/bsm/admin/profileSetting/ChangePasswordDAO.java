package com.bsm.bsm.admin.profileSetting;


import com.bsm.bsm.database.DatabaseConnection;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ChangePasswordDAO {
    private static final Statement statement;

    static {
        try {
            statement = DatabaseConnection.getConnection().createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        String email = "thu.admin@bms.com";
        String currentPassword = "31052003";
        String newPassword = "31052003";

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
            String QUERY_CHANGE_PASSWORD = "UPDATE user SET password='%s' WHERE email='%s'".formatted(hashedNewPassword, email);
            int rowsAffected =  statement.executeUpdate(QUERY_CHANGE_PASSWORD);
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
        String QUERY_PASSWORD = "SELECT PASSWORD FROM user WHERE email='%s'".formatted(email);
        ResultSet resultSet_ = statement.executeQuery(QUERY_PASSWORD);

        if (resultSet_.next()) {
            String storedPass = resultSet_.getString("password");

            //compare the current password with the password in the database
            if (BCrypt.checkpw(currentPassword, storedPass)) {
                return true;
            } else {
                System.out.println("Password is incorrect");
                return false;
            }
        } else {
            System.out.println("User not found");
            return false;
        }
    }
}
