package com.bsm.bsm.admin.userAccount;

import com.bsm.bsm.database.DatabaseConnection;
import org.mindrot.jbcrypt.BCrypt;

import java.util.concurrent.atomic.AtomicBoolean;

public class AddUserDAO {


    public boolean checkEmailExists(String email) {
        AtomicBoolean hasExisted = new AtomicBoolean(false);
        String QUERY_EMAIL = "select 1 from user where email = ?";

        DatabaseConnection.executeQuery(QUERY_EMAIL, resultSet -> {
            if (resultSet != null && resultSet.next()) {
                hasExisted.set(true);
            }
        }, email);

        return hasExisted.get();
    }

    public String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }

    public boolean addUser(String name, String dob, String email, String password, String role) {
        int rowsAffected = DatabaseConnection.executeUpdate("CALL ADDUSER(?, ?, ?, ?, ?)", name, dob, email, password, role);
        return rowsAffected > 0;
    }
}
