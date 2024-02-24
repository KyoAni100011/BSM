package main.java.auth;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import main.java.database.DatabaseConnection;

public class LoginDAO {

    private static final String SELECT_USER_QUERY = "SELECT * FROM user WHERE email = ? AND password = ?";
    private static final String SELECT_USER_INFO_QUERY = "SELECT ID, EMAIL, DOB, NAME FROM user WHERE email = ? AND password = ?";

    public boolean validateUser(String email, String password) {
        AtomicBoolean isValidUser = new AtomicBoolean(false);

        DatabaseConnection.executeQuery(SELECT_USER_QUERY, resultSet -> {
            isValidUser.set(resultSet.next());
        }, email, password);

        return isValidUser.get();
    }

    public UserModel getUserInfo(String username, String password) {
        AtomicReference<UserModel> userModelRef = new AtomicReference<>();

        DatabaseConnection.executeQuery(SELECT_USER_INFO_QUERY, resultSet -> {
            if (resultSet.next()) {
                byte[] id = resultSet.getBytes("id");
                String email = resultSet.getString("email");
                String dob = resultSet.getString("dob");
                String name = resultSet.getString("name");

                userModelRef.set(new UserModel(id, name, email, dob));
            }
        }, username, password);

        return userModelRef.get();
    }
}
