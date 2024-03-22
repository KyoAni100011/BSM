package com.bsm.bsm.account;

import com.bsm.bsm.admin.AdminModel;
import com.bsm.bsm.database.DatabaseConnection;
import com.bsm.bsm.employee.EmployeeModel;
import com.bsm.bsm.user.UserModel;
import org.mindrot.jbcrypt.BCrypt;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class AccountDAO {
    private static final String GET_ALL_USERS_INFO_QUERY = "SELECT id, name, email, lastLogin, isEnabled FROM user WHERE id != ?";
    private final String UPDATE_USER_QUERY = "UPDATE user SET name = ?, dob = ?, telephone = ?, address = ? where id = ?";
    private final String GET_USER_QUERY = "SELECT user.id,user.name, user.dob, user.telephone, user.address  FROM user where email = ?";
    private final String QUERY_UPDATE_PASSWORD = "UPDATE user SET password = ? where email = ?";
    private final String QUERY_GET_USER_DOB = "SELECT dob FROM user WHERE email = ?";
    private final String QUERY_GET_ADMIN = "SELECT u.id FROM user u join admin a WHERE u.id = a.userId and u.id = ?";
    private final String QUERY_GET_USER = "SELECT * FROM user u where u.email = ?";

    public List<UserModel> getAllUsersInfo(String excludedUserId) {
        List<UserModel> userModel = new ArrayList<>();

        DatabaseConnection.executeQuery(GET_ALL_USERS_INFO_QUERY, resultSet -> {
            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String email = resultSet.getString("email");
                String name = resultSet.getString("name");
                boolean isEnabled = resultSet.getBoolean("isEnabled");
                String lastLogin = resultSet.getString("lastLogin");

                if (email.endsWith(".admin@bms.com")) {
                    userModel.add(new AdminModel(id, name, email, null, null, null, isEnabled, lastLogin));
                } else {
                    userModel.add(new EmployeeModel(id, name, email, null, null, null, isEnabled, lastLogin));
                }
            }
        }, excludedUserId);

        return userModel;
    }

    public boolean updateProfile(String id, String fullName, String telephone, String dob, String address) {
        int rowsAffected = DatabaseConnection.executeUpdate(UPDATE_USER_QUERY, fullName, dob, telephone, address, id);
        return rowsAffected > 0;
    }
    public UserModel getUserProfile(String email) {
        List<UserModel> userModel = new ArrayList<>();
        System.out.println(email);
        DatabaseConnection.executeQuery(GET_USER_QUERY, resultSet -> {
            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String dob = resultSet.getString("dob");
                String name = resultSet.getString("name");
                String address = resultSet.getString("address");
                String phone = resultSet.getString("telephone");
                userModel.add(new UserModel(id, name, "", dob,phone, address, true, ""));
            }
        },email);
        return userModel.getFirst();
    }

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

    public boolean enableUser(String userId) {
        try {
            String ENABLE_USER_QUERY = "UPDATE user SET isEnabled = 1 WHERE id = ?";
            int rowEffected = DatabaseConnection.executeUpdate(ENABLE_USER_QUERY, userId);
            return rowEffected > 0;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean disableUser(String userId) {
        try {
            String DISABLE_USER_QUERY = "UPDATE user SET isEnabled = 0 WHERE id = ?";
            int rowEffected = DatabaseConnection.executeUpdate(DISABLE_USER_QUERY, userId);
            return rowEffected > 0;
        } catch (Exception e) {
            return false;
        }
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

    public boolean addUser(String name, String dob, String email, String address, String password, String role) {
        int rowsAffected = DatabaseConnection.executeUpdate("CALL ADDUSER(?, ?, ?, ?, ?, ?)", name, dob, email, address, hashPassword(password), role);
        return rowsAffected > 0;
    }
}
