package com.bsm.bsm.auth;

import com.bsm.bsm.database.DatabaseConnection;
import com.bsm.bsm.user.UserModel;
import org.mindrot.jbcrypt.BCrypt;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import static com.bsm.bsm.utils.convertProvider.bytesToHexString;

public class AuthDAO {
    private static final String SELECT_PASSWORD_QUERY = "SELECT PASSWORD FROM user WHERE email = ?";
    private static final String SELECT_USER_QUERY = "SELECT * FROM user WHERE email = ?";

    public boolean validateUser(String email, String password) {
        AtomicBoolean isPasswordValid = new AtomicBoolean(false);

        DatabaseConnection.executeQuery(SELECT_PASSWORD_QUERY, resultSet -> {
            if (resultSet.next()) {
                String storedPass = resultSet.getString("password").trim();
                isPasswordValid.set(BCrypt.checkpw(password, storedPass));
            }
        }, email);

        return isPasswordValid.get();
    }

    public UserModel getUserInfo(String email) {
        AtomicReference<UserModel> userModelRef = new AtomicReference<>();

        DatabaseConnection.executeQuery(SELECT_USER_QUERY, resultSet -> {
            if (resultSet.next()) {
                String id = bytesToHexString(resultSet.getBytes("id"));
                String dob = resultSet.getString("dob");
                String name = resultSet.getString("name");
                boolean isEnabled = resultSet.getBoolean("isEnabled");
                userModelRef.set(new UserModel(id, name, email, dob, isEnabled));
            }
        }, email);

        return userModelRef.get();
    }
}


