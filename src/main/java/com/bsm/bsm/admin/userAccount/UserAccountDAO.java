package com.bsm.bsm.admin.userAccount;

import com.bsm.bsm.admin.AdminModel;
import com.bsm.bsm.database.DatabaseConnection;
import com.bsm.bsm.user.UserModel;

import java.sql.SQLException;
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

                userModel.add(new UserModel(id, name, email, isEnabled, lastLogin));
            }
        });

        return userModel;
}
}