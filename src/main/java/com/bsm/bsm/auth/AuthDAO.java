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

public class AuthDAO {
    private static final String SELECT_USER_QUERY = "SELECT * FROM user WHERE email = ?";

    public static boolean validateUser(String email, String password) {
        AtomicBoolean isValid = new AtomicBoolean(false);
        String QUERY_PASSWORD = "SELECT PASSWORD FROM user WHERE email='%s'".formatted(email);

        DatabaseConnection.executeQuery(QUERY_PASSWORD, resultSet -> {
            if (resultSet.next()) {
                String storedPass = resultSet.getString("password").trim();

                if (BCrypt.checkpw(password, storedPass)) {
                    isValid.set(true);
            }}});
        return isValid.get();
    }

    public static UserModel getUserInfo(String email, String password) {
        AtomicReference<UserModel> userModelRef = new AtomicReference<>();

        DatabaseConnection.executeQuery(SELECT_USER_QUERY, resultSet -> {
            if (resultSet.next()) {
                String id = Arrays.toString(resultSet.getBytes("id"));
                String dob = resultSet.getString("dob");
                String name = resultSet.getString("name");
                boolean isEnabled = resultSet.getBoolean("isEnabled");
                String password_ = resultSet.getString("password");

                userModelRef.set(new UserModel(id, name, email, password_, dob, isEnabled));
            }
        }, email);

        return userModelRef.get();
    }
}


