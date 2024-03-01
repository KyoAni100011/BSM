package main.java.auth;

import main.java.database.DatabaseConnection;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicReference;

public class LoginDAO {

    private static final String SELECT_USER_QUERY = "SELECT * FROM user WHERE email = ?";

    public boolean validateUser(String email, String password) {

        String QUERY_PASSWORD = "SELECT PASSWORD FROM user WHERE email='%s'".formatted(email);
        try (ResultSet resultSet_ = DatabaseConnection.getConnection().createStatement().executeQuery(QUERY_PASSWORD)){
            if (resultSet_.next()) {
                String storedPass = resultSet_.getString("password");

                //compare the current password with the password in the database
                if (BCrypt.checkpw(password, storedPass)) {
                    String QUERY_EMAIL = "SELECT EMAIL FROM user WHERE email='%s'".formatted(email);
                    ResultSet resultSet = DatabaseConnection.getConnection().createStatement().executeQuery(QUERY_EMAIL);
                    if (email.equals(resultSet.getString("email"))){
                        return true;
                    }
                    return false;
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
//
//        AtomicBoolean isValidUser = new AtomicBoolean(false);
//
//        DatabaseConnection.executeQuery(SELECT_USER_QUERY, resultSet -> {
//            isValidUser.set(resultSet.next());
//        }, email, password);

//        return isValidUser.get();
    }

    public UserModel getUserInfo(String email, String password) {
        AtomicReference<UserModel> userModelRef = new AtomicReference<>();

        DatabaseConnection.executeQuery(SELECT_USER_QUERY, resultSet -> {
            if (resultSet.next()) {
                String id = resultSet.getBytes("id").toString();
                String dob = resultSet.getString("dob");
                String name = resultSet.getString("name");
                boolean isEnabled = resultSet.getBoolean("isEnabled");
                String password_ = resultSet.getString("password");

                userModelRef.set(new UserModel(id, name, email, password_, dob, isEnabled));
            }
        }, email);

        return userModelRef.get();
    }

    private static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }
}
