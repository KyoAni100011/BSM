package com.bsm.bsm.admin.profileSetting;


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
        try  {
            //Change password
            String hashedNewPassword = hashPassword(newPassword);
            int rowsAffected =  DatabaseConnection.executeUpdate(QUERY_CHANGE_PASSWORD, hashedNewPassword, id);
            if (rowsAffected > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean validatePassword(String id, String currentPassword) throws SQLException {
        AtomicBoolean isValid = new AtomicBoolean(false);

        DatabaseConnection.executeQuery(QUERY_PASSWORD, resultSet -> {
            if (resultSet.next()) {
                String storedPass = resultSet.getString("password").trim();

                //compare the current password with the password in the database
                if (BCrypt.checkpw(currentPassword, storedPass)) {
                    isValid.set(true);
                }
            }
        }, id);

        return isValid.get();
    }
}
