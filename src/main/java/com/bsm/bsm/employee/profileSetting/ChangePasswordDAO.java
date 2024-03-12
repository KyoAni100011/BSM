package com.bsm.bsm.employee.profileSetting;


import com.bsm.bsm.database.DatabaseConnection;
import org.mindrot.jbcrypt.BCrypt;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicBoolean;

public class ChangePasswordDAO {

    private final String QUERY_CHANGE_PASSWORD = "UPDATE user SET password = ? WHERE id = ?";
    private final String QUERY_PASSWORD = "SELECT PASSWORD FROM user WHERE id = ?";

    public String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }

    public boolean changePassword(String id, String currentPassword, String newPassword) {
        String hashedNewPassword = hashPassword(newPassword);
        int rowsAffected =  DatabaseConnection.executeUpdate(QUERY_CHANGE_PASSWORD, hashedNewPassword, id);
        return rowsAffected > 0;
    }

    public boolean validatePassword(String id, String currentPassword) throws SQLException {
        AtomicBoolean isValid = new AtomicBoolean(false);

        DatabaseConnection.executeQuery(QUERY_PASSWORD, resultSet -> {
            if (resultSet.next()) {
                String storedPass = resultSet.getString("password").trim();

                //compare the current password with the password in the database
                if (BCrypt.checkpw(currentPassword.trim(), storedPass)) {
                    isValid.set(true);
                }
            }
        }, id);

        return isValid.get();
    }
}
