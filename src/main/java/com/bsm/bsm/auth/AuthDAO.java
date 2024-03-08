package com.bsm.bsm.auth;

import com.bsm.bsm.database.DatabaseConnection;
import com.bsm.bsm.user.UserModel;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import static com.bsm.bsm.utils.convertProvider.bytesToHexString;

public class AuthDAO {
    private static final String SELECT_PASSWORD_QUERY = "SELECT PASSWORD FROM user WHERE id = ?";
    private static final String SELECT_USER_QUERY = "SELECT * FROM user WHERE id = ?";
    private static final String SELECT_ADMIN_QUERY = "SELECT userID FROM admin WHERE id = ?";
    private static final String SELECT_EMPLOYEE_QUERY = "SELECT userID FROM employee id = ?";

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
        AtomicReference<UserModel> userModelRef = new AtomicReference<>();

        DatabaseConnection.executeQuery(SELECT_USER_QUERY, resultSet -> {
            if (resultSet.next()) {
                String email = resultSet.getString("email");
                String dob = resultSet.getString("dob");
                String name = resultSet.getString("name");
                boolean isEnabled = resultSet.getBoolean("isEnabled");
                userModelRef.set(new UserModel(id, name, email, dob, isEnabled));
            }
        }, id);

        return userModelRef.get();
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
