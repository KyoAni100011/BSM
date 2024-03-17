package com.bsm.bsm.account;

import com.bsm.bsm.admin.AdminModel;
import com.bsm.bsm.database.DatabaseConnection;
import com.bsm.bsm.employee.EmployeeModel;
import com.bsm.bsm.user.UserModel;

import java.util.ArrayList;
import java.util.List;

public class AccountDAO {
    private static final String GET_ALL_USERS_INFO_QUERY = "SELECT id, name, email, lastLogin, isEnabled FROM user WHERE id != ?";
    private static final String GET_ALL_USERS_INFO_BY_ID_QUERY = "SELECT id, name, email, lastLogin, isEnabled FROM user WHERE id LIKE ?";
    private static final String GET_ALL_USERS_INFO_BY_EMAIL_QUERY = "SELECT id, name, email, lastLogin, isEnabled FROM user WHERE email LIKE ?";
    private static final String GET_ALL_USERS_INFO_BY_NAME_QUERY = "SELECT id, name, email, lastLogin, isEnabled FROM user WHERE name LIKE ?";

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

    public List<UserModel> getAllUsersInfoByName(String name)
    {
        List<UserModel> userModel = new ArrayList<>();

        DatabaseConnection.executeQuery(GET_ALL_USERS_INFO_BY_NAME_QUERY, resultSet -> {
            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String email = resultSet.getString("email");
                boolean isEnabled = resultSet.getBoolean("isEnabled");
                String lastLogin = resultSet.getString("lastLogin");

                if (email.endsWith(".admin@bms.com")) {
                    userModel.add(new AdminModel(id, name, email, null, null, null, isEnabled, lastLogin));
                } else {
                    userModel.add(new EmployeeModel(id, name, email, null, null, null, isEnabled, lastLogin));
                }
            }
        }, "%" + name + "%");

        return userModel;
    }

    public List<UserModel> getAllUsersInfoById(String id)
    {
        List<UserModel> userModel = new ArrayList<>();

        DatabaseConnection.executeQuery(GET_ALL_USERS_INFO_BY_ID_QUERY, resultSet -> {
            while (resultSet.next()) {
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
        }, "%" + id + "%");

        return userModel;
    }

    public List<UserModel> getAllUsersInfoByEmail(String email)
    {
        List<UserModel> userModel = new ArrayList<>();

        DatabaseConnection.executeQuery(GET_ALL_USERS_INFO_BY_EMAIL_QUERY, resultSet -> {
            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String name = resultSet.getString("name");
                boolean isEnabled = resultSet.getBoolean("isEnabled");
                String lastLogin = resultSet.getString("lastLogin");

                if (email.endsWith(".admin@bms.com")) {
                    userModel.add(new AdminModel(id, name, email, null, null, null, isEnabled, lastLogin));
                } else {
                    userModel.add(new EmployeeModel(id, name, email, null, null, null, isEnabled, lastLogin));
                }
            }
        }, "%" + email + "%");

        return userModel;
    }
}
