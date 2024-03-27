package com.bsm.bsm.auth;

import com.bsm.bsm.admin.AdminModel;
import com.bsm.bsm.database.DatabaseConnection;
import com.bsm.bsm.employee.EmployeeModel;
import com.bsm.bsm.user.UserModel;
import org.mindrot.jbcrypt.BCrypt;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import static com.bsm.bsm.utils.convertProvider.bytesToHexString;

public class AuthDAO {
    private static final String SELECT_PASSWORD_QUERY = "SELECT PASSWORD FROM user WHERE id = ?";
    private static final String UPDATE_USER_LAST_LOGIN = "UPDATE user SET lastLogin = ? WHERE id = ?";
    private static final String SELECT_USER_QUERY = "SELECT * FROM user WHERE id = ?";
    private static final String SELECT_USER_BY_EMAIL_QUERY = "SELECT * FROM user WHERE email = ?";
    private static final String SELECT_ADMIN_QUERY = "SELECT * FROM admin WHERE userID = ?";
    private static final String SELECT_EMPLOYEE_QUERY = "SELECT * FROM employee WHERE userID = ?";

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

                if (email.endsWith(".admin@bms.com")) {
                    userModel.set(new AdminModel(id, name, email, dob, phone, address, isEnabled, lastLogin));
                } else {
                    userModel.set(new EmployeeModel(id, name, email, dob, phone, address, isEnabled, lastLogin));
                }

                //update last login
                DatabaseConnection.executeUpdate(UPDATE_USER_LAST_LOGIN, new java.sql.Timestamp(System.currentTimeMillis()), id);
            }
        }, id);

        return userModel.get();
    }


    public UserModel getUserInfoByEmail(String email) {
        AtomicReference<UserModel> userModel = new AtomicReference<>(new UserModel());

        DatabaseConnection.executeQuery(SELECT_USER_BY_EMAIL_QUERY, resultSet -> {
            if (resultSet != null && resultSet.next()) {
                String id = resultSet.getString("id");
                String dob = resultSet.getString("dob");
                String name = resultSet.getString("name");
                String phone = resultSet.getString("telephone");
                String address = resultSet.getString("address");
                boolean isEnabled = resultSet.getBoolean("isEnabled");
                String lastLogin = String.valueOf(new java.sql.Timestamp(System.currentTimeMillis()));


                userModel.set(new EmployeeModel(id, name, email, dob, phone, address, isEnabled, lastLogin));
                DatabaseConnection.executeUpdate(UPDATE_USER_LAST_LOGIN, new java.sql.Timestamp(System.currentTimeMillis()), id);
            }
        }, email);

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
}
