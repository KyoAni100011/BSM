package com.bsm.bsm.admin.userAccount;

import com.bsm.bsm.database.DatabaseConnection;
import org.mindrot.jbcrypt.BCrypt;
import java.util.concurrent.atomic.AtomicBoolean;

public class PasswordResetDAO {

    private final String QUERY_UPDATE_PASSWORD = "UPDATE user SET password = ? where email = ?";
    private final String QUERY_GET_USER_DOB = "SELECT dob FROM user WHERE email = ?";
    private final String QUERY_GET_ADMIN = "SELECT u.id FROM user u join admin a WHERE u.id = a.userId and u.id = ?";
    private final String QUERY_GET_USER = "SELECT * FROM user u where u.email = ?";

    public boolean updatePassword(String hashedPassword, String email) {
        DatabaseConnection.executeUpdate(QUERY_UPDATE_PASSWORD, hashedPassword, email);
        return true;
    }

    public boolean isAdmin(String id) {
        AtomicBoolean checkAdmin = new AtomicBoolean(false);
        DatabaseConnection.executeQuery(QUERY_GET_ADMIN, resultSet -> {
            if (resultSet != null && resultSet.next()) {
               checkAdmin.set(true);
            }
        }, id);
        return checkAdmin.get();
    }

    public boolean hasUserExist(String email) {
        AtomicBoolean checkUser = new AtomicBoolean(false);
        DatabaseConnection.executeQuery(QUERY_GET_USER, resultSet -> {
            if (resultSet != null && resultSet.next()) {
               checkUser.set(true);
            }
        }, email);
        return checkUser.get();
    }

    public boolean updatePasswordUsingDOB(String email) {
        DatabaseConnection.executeQuery(QUERY_GET_USER_DOB, resultSet -> {
            if (resultSet.next()) {
                String dob = resultSet.getString("dob");
                var parts = dob.split("-");
                String hashedPassword = hashPassword(parts[2] + parts[1] + parts[0]);
                DatabaseConnection.executeUpdate(QUERY_UPDATE_PASSWORD, hashedPassword, email);
            }
        }, email);
        return true;
    }

    public String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }
}
