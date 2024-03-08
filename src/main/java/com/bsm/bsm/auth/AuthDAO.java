package com.bsm.bsm.auth;

import com.bsm.bsm.database.DatabaseConnection;
import com.bsm.bsm.user.UserModel;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

public class AuthDAO {
    private static final String SELECT_USER_QUERY = "SELECT * FROM user WHERE email = ?";

    public static boolean validateUser(String email, String password) {
        String QUERY_PASSWORD = "SELECT PASSWORD FROM user WHERE email='%s'".formatted(email);

        try (ResultSet resultSet_ = DatabaseConnection.getConnection().createStatement().executeQuery(QUERY_PASSWORD)) {
            if (resultSet_.next()) {
                String storedPass = resultSet_.getString("password").trim();

                if (BCrypt.checkpw(password, storedPass)) {
                    String QUERY_EMAIL = "SELECT EMAIL FROM user WHERE email='%s'".formatted(email);

                    try (Statement secondStatement = DatabaseConnection.getConnection().createStatement();
                         ResultSet resultSet = secondStatement.executeQuery(QUERY_EMAIL)) {

                        if (resultSet.next()) {
                            if (email.equals(resultSet.getString("email"))) {
                                return true;
                            }
                        } else {
                            System.out.println("Email not found");
                            return false;
                        }
                    }
                } else {
                    System.out.println("Password is incorrect");
                    return false;
                }
            } else {
                System.out.println("User not found");
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
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


