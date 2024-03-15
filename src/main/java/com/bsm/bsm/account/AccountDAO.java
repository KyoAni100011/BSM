package com.bsm.bsm.account;

import com.bsm.bsm.database.DatabaseConnection;
import com.bsm.bsm.user.UserModel;

import java.util.ArrayList;
import java.util.List;

public class AccountDAO {
    private static final String GET_ALL_USERS_INFO_QUERY = "SELECT * FROM user WHERE id != ?";
    private static final String GET_ALL_USERS_INFO_BY_ID_QUERY = "SELECT * FROM user WHERE id LIKE ?";
    private static final String GET_ALL_USERS_INFO_BY_EMAIL_QUERY = "SELECT * FROM user WHERE email LIKE ?";
    private static final String GET_ALL_USERS_INFO_BY_NAME_QUERY = "SELECT * FROM user WHERE name LIKE ?";

    public List<UserModel> getAllUsersInfo(String excludedUserId) {
        List<UserModel> userModel = new ArrayList<>();

        DatabaseConnection.executeQuery(GET_ALL_USERS_INFO_QUERY, resultSet -> {
            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String email = resultSet.getString("email");
                String dob = resultSet.getString("dob");
                String name = resultSet.getString("name");
                String phone = resultSet.getString("telephone");
                String address = resultSet.getString("address");
                boolean isEnabled = resultSet.getBoolean("isEnabled");
                String lastLogin = resultSet.getString("lastLogin");
                userModel.add(new UserModel(id, name, email, dob, phone, address, isEnabled, lastLogin));
            }
        }, excludedUserId);

        return userModel;
    }

    public List<UserModel> getAllUsersInfoByName(String name)
    {
        List<UserModel> userModel = new ArrayList<>();

        DatabaseConnection.executeQuery(GET_ALL_USERS_INFO_BY_NAME_QUERY, resultSet -> {
            while (resultSet.next()) {

                String email = resultSet.getString("email");
                String nameGet = resultSet.getString("name");
                String dob = resultSet.getString("dob");
                String id = resultSet.getString("id");
                String phone = resultSet.getString("telephone");
                String address = resultSet.getString("address");
                boolean isEnabled = resultSet.getBoolean("isEnabled");
                String lastLogin = resultSet.getString("lastLogin");
                userModel.add(new UserModel(id, nameGet, email, dob, phone, address, isEnabled, lastLogin));
            }
        }, "%" + name + "%");

        return userModel;
    }

    public List<UserModel> getAllUsersInfoById(String id)
    {
        List<UserModel> userModel = new ArrayList<>();

        DatabaseConnection.executeQuery(GET_ALL_USERS_INFO_BY_ID_QUERY, resultSet -> {
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String idGet = resultSet.getString("id");
                String dob = resultSet.getString("dob");
                String email = resultSet.getString("email");
                String phone = resultSet.getString("telephone");
                String address = resultSet.getString("address");
                boolean isEnabled = resultSet.getBoolean("isEnabled");
                String lastLogin = resultSet.getString("lastLogin");
                userModel.add(new UserModel(idGet, name, email, dob, phone, address, isEnabled, lastLogin));
            }
        }, "%" + id + "%");

        return userModel;
    }

    public List<UserModel> getAllUsersInfoByEmail(String email)
    {
        List<UserModel> userModel = new ArrayList<>();

        DatabaseConnection.executeQuery(GET_ALL_USERS_INFO_BY_EMAIL_QUERY, resultSet -> {
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String dob = resultSet.getString("dob");
                String emailGet = resultSet.getString("email");
                String id = resultSet.getString("id");
                String phone = resultSet.getString("telephone");
                String address = resultSet.getString("address");
                boolean isEnabled = resultSet.getBoolean("isEnabled");
                String lastLogin = resultSet.getString("lastLogin");
                userModel.add(new UserModel(id, name, emailGet, dob, phone, address, isEnabled, lastLogin));
            }
        }, "%" + email + "%");

        return userModel;
    }
}
