package com.bsm.bsm.account;

import com.bsm.bsm.database.DatabaseConnection;
import com.bsm.bsm.user.UserModel;

import java.util.ArrayList;
import java.util.List;

public class AccountDAO {
    private static final String GET_ALL_USERS_INFO_QUERY = "SELECT * FROM user WHERE id != ?";

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
}
