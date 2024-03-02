package com.bsm.bsm.auth;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import com.bsm.bsm.database.DatabaseConnection;
import com.bsm.bsm.user.UserModel;

import static com.bsm.bsm.security.JWTProvider.decodeJwtToken;
import static com.bsm.bsm.utils.convertProvider.bytesToHexString;
import static com.bsm.bsm.utils.convertProvider.reverseHexString;

public class AuthDAO {
    private static final String SELECT_USER_QUERY = "SELECT * FROM user WHERE email = ? AND password = ?";

    public static boolean isAdmin(UserModel userInfo) {
        final String SELECT_USER_QUERY = "SELECT * FROM admin WHERE userID = ?";
        AtomicBoolean isAdmin = new AtomicBoolean(false);

        DatabaseConnection.executeQuery(SELECT_USER_QUERY, resultSet -> isAdmin.set(resultSet.next()),
                (Object) reverseHexString(decodeJwtToken(userInfo.getToken())));

        return isAdmin.get();
    }

    public static boolean isEmployee(UserModel userInfo) {
        final String SELECT_USER_QUERY = "SELECT * FROM employee WHERE userID = ?";
        AtomicBoolean isEmployee = new AtomicBoolean(false);

        DatabaseConnection.executeQuery(SELECT_USER_QUERY, resultSet -> isEmployee.set(resultSet.next()),
                (Object) reverseHexString(decodeJwtToken(userInfo.getToken())));

        return isEmployee.get();
    }

    public boolean validateUser(String email, String password) {
        AtomicBoolean isValidUser = new AtomicBoolean(false);

        DatabaseConnection.executeQuery(SELECT_USER_QUERY, resultSet -> {
            isValidUser.set(resultSet.next());
        }, email, password);

        return isValidUser.get();
    }

    public static UserModel getUserInfo(String email, String password) {
        AtomicReference<UserModel> userModelRef = new AtomicReference<>();

        DatabaseConnection.executeQuery(SELECT_USER_QUERY, resultSet -> {
            if (resultSet.next()) {
                byte[] binaryValue = resultSet.getBytes("id");
                String id = bytesToHexString(binaryValue);
                String dob = resultSet.getString("dob");
                String name = resultSet.getString("name");
                boolean isEnabled = resultSet.getBoolean("isEnabled");

                userModelRef.set(new UserModel(id, name, email, password, dob, isEnabled));
            }
        }, email, password);

        return userModelRef.get();
    }
}


