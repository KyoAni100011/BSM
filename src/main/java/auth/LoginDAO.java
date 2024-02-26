package main.java.auth;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import main.java.database.DatabaseConnection;

public class LoginDAO {

    private static final String SELECT_USER_QUERY = "SELECT * FROM user WHERE email = ? AND password = ?";

    public boolean validateUser(String email, String password) {
        AtomicBoolean isValidUser = new AtomicBoolean(false);

        DatabaseConnection.executeQuery(SELECT_USER_QUERY, resultSet -> {
            isValidUser.set(resultSet.next());
        }, email, password);

        return isValidUser.get();
    }

    public UserModel getUserInfo(String email, String password) {
        AtomicReference<UserModel> userModelRef = new AtomicReference<>();

        DatabaseConnection.executeQuery(SELECT_USER_QUERY, resultSet -> {
            if (resultSet.next()) {
                String id = resultSet.getBytes("id").toString();
                String dob = resultSet.getString("dob");
                String name = resultSet.getString("name");
                Boolean isEnabled = resultSet.getBoolean("isEnabled");

                userModelRef.set(new UserModel(id, name, email, password, dob, isEnabled));
            }
        }, email, password);

        return userModelRef.get();
    }
}
