package com.bsm.bsm.admin.userAccount;

import com.bsm.bsm.admin.AdminModel;
import com.bsm.bsm.database.DatabaseConnection;
import com.bsm.bsm.employee.EmployeeModel;
import com.bsm.bsm.user.UserModel;
import org.mindrot.jbcrypt.BCrypt;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

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


    public boolean addUser(String name, String dob, String email, String address, String password, String role) {

        int rowsAffected = DatabaseConnection.executeUpdate("CALL ADDUSER(?, ?, ?, ?, ?, ?)", name, dob, email, address, hashPassword(password), role);
        return rowsAffected > 0;
    }

    public UserModel getUserInfo(String email, String role) {
        String QUERY_USER = "SELECT id, name, telephone, address, dob, lastLogin, isEnabled FROM user WHERE email = ?";
        AtomicReference<UserModel> user = new AtomicReference<>();
        DatabaseConnection.executeQuery(QUERY_USER, resultSet -> {
            if (resultSet != null && resultSet.next()) {
                String id = resultSet.getString("id");
                String name = resultSet.getString("name");
                String phone = resultSet.getString("telephone");
                String address = resultSet.getString("address");
                String dob = resultSet.getString("dob");
                String lastLogin = resultSet.getString("lastLogin");
                boolean isEnabled = resultSet.getBoolean("isEnabled");

                switch (role) {
                    case "admin" -> user.set(new AdminModel(id, name, email, dob, phone, address, isEnabled, lastLogin));
                    case "employee" -> user.set(new EmployeeModel(id, name, email, dob, phone, address, isEnabled, lastLogin));
                };
            }
        }, email);
        return user.get();
    }
}
