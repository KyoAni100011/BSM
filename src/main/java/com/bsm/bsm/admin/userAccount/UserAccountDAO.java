package com.bsm.bsm.admin.userAccount;

import com.bsm.bsm.database.DatabaseConnection;
import com.bsm.bsm.user.UserModel;

import java.util.ArrayList;
import java.util.List;

public class UserAccountDAO {

    public List<UserModel> getAllUsersInfo(String excludedUserId) {
        List<UserModel> userModel = new ArrayList<>();
        String GET_ALL_USERS_INFO_QUERY = "SELECT * FROM user WHERE id != " + excludedUserId;

        DatabaseConnection.executeQuery(GET_ALL_USERS_INFO_QUERY, resultSet -> {
            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String lastLogin = resultSet.getString("lastLogin");
                boolean isEnabled = resultSet.getBoolean("isEnabled");

                userModel.add(new UserModel(id, name, email, null, null, null, isEnabled, lastLogin));
            }
        });

        return userModel;
    }

    public List<UserModel> getAllUsersInfo(String excludedUserId, String sortOrder, String column) {

        List<UserModel> userModel = new ArrayList<>();

        String GET_ALL_USERS_INFO_QUERY = "SELECT * FROM user WHERE id != " + excludedUserId + " ORDER BY " + column + " " + sortOrder;

        DatabaseConnection.executeQuery(GET_ALL_USERS_INFO_QUERY, resultSet -> {
            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String lastLogin = resultSet.getString("lastLogin");
                boolean isEnabled = resultSet.getBoolean("isEnabled");

                userModel.add(new UserModel(id, name, email, null, null, null, isEnabled, lastLogin));
            }
        });

        return userModel;
    }
}