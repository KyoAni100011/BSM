package com.bsm.bsm.user;

import com.bsm.bsm.admin.AdminModel;
import com.bsm.bsm.database.DatabaseConnection;
import com.bsm.bsm.employee.EmployeeModel;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import static com.bsm.bsm.utils.convertProvider.bytesToHexString;

public class UserDAO {
    private static final String SELECT_PASSWORD_QUERY = "SELECT PASSWORD FROM user WHERE id = ?";
    private static final String UPDATE_USER_LAST_LOGIN = "UPDATE user SET lastLogin = ? WHERE id = ?";
    private static final String SELECT_USER_QUERY = "SELECT * FROM user WHERE id = ?";
    private static final String SELECT_ADMIN_QUERY = "SELECT * FROM admin WHERE userID = ?";
    private static final String SELECT_EMPLOYEE_QUERY = "SELECT * FROM employee WHERE userID = ?";
    private static final String QUERY_CHANGE_PASSWORD = "UPDATE user SET password = ? WHERE id = ?";
    private static final String QUERY_PASSWORD = "SELECT PASSWORD FROM user WHERE id = ?";
    private static final String UPDATE_USER_QUERY = "UPDATE user SET name = ?, dob = ?, telephone = ?, address = ? WHERE id = ?";

    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }

    public boolean validateUser(String id, String password) {
        AtomicBoolean isPasswordValid = new AtomicBoolean(false);

        DatabaseConnection.executeQuery(SELECT_PASSWORD_QUERY, resultSet -> {
            if (resultSet.next()) {
                String storedPass = resultSet.getString("password").trim();
                isPasswordValid.set(BCrypt.checkpw(password, storedPass));
            }
        }, id);

        return isPasswordValid.get();
    }

    public UserModel getUserInfo(String id) {
        AtomicReference<UserModel> userModel = new AtomicReference<>(new UserModel());

        DatabaseConnection.executeQuery(SELECT_USER_QUERY, resultSet -> {
            if (resultSet != null && resultSet.next()) {
                String email = resultSet.getString("email");
                String dob = resultSet.getString("dob");
                String name = resultSet.getString("name");
                String phone = resultSet.getString("telephone");
                String address = resultSet.getString("address");
                boolean isEnabled = resultSet.getBoolean("isEnabled");
                String lastLogin = String.valueOf(new java.sql.Timestamp(System.currentTimeMillis()));

                if (!getAdminID(id).isEmpty()) {
                    userModel.set(new AdminModel(id, name, email, dob, phone, address, isEnabled, lastLogin));
                } else {
                    userModel.set(new EmployeeModel(id, name, email, dob, phone, address, isEnabled, lastLogin));
                }

                DatabaseConnection.executeUpdate(UPDATE_USER_LAST_LOGIN, new java.sql.Timestamp(System.currentTimeMillis()), id);
            }
        }, id);

        return userModel.get();
    }

    private String getUserID(String query, String ID) {
        AtomicReference<String> userID = new AtomicReference<>("");
        DatabaseConnection.executeQuery(query, resultSet -> {
            if (resultSet.next()) {
                userID.set(bytesToHexString(resultSet.getBytes("userID")));
            }
        }, ID);

        return userID.get();
    }

    public String getAdminID(String ID) {
        return getUserID(SELECT_ADMIN_QUERY, ID);
    }

    public String getEmployeeID(String ID) {
        return getUserID(SELECT_EMPLOYEE_QUERY, ID);
    }

    public boolean changePassword(String id, String newPassword) {
        String hashedNewPassword = hashPassword(newPassword);
        int rowsAffected = DatabaseConnection.executeUpdate(QUERY_CHANGE_PASSWORD, hashedNewPassword, id);
        return rowsAffected > 0;
    }

    public boolean validatePassword(String id, String currentPassword) throws SQLException {
        AtomicBoolean isValid = new AtomicBoolean(false);

        DatabaseConnection.executeQuery(QUERY_PASSWORD, resultSet -> {
            if (resultSet.next()) {
                String storedPass = resultSet.getString("password").trim();

                if (BCrypt.checkpw(currentPassword.trim(), storedPass)) {
                    isValid.set(true);
                }
            }
        }, id);

        return isValid.get();
    }

    public boolean updateProfile(String id, String fullName, String telephone, String dob, String address) {
        int rowsAffected = DatabaseConnection.executeUpdate(UPDATE_USER_QUERY, fullName, dob, telephone, address, id);
        return rowsAffected > 0;
    }
}
