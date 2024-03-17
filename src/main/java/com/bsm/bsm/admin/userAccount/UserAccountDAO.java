package com.bsm.bsm.admin.userAccount;

import com.bsm.bsm.admin.AdminModel;
import com.bsm.bsm.database.DatabaseConnection;
import com.bsm.bsm.employee.EmployeeModel;
import com.bsm.bsm.user.UserModel;

import java.util.ArrayList;
import java.util.List;

public class UserAccountDAO {

    public List<UserModel> getAllUsersInfo(String excludedUserId) {

        List<UserModel> listUsers = new ArrayList<>();
        String GET_ALL_USERS_INFO_QUERY = "SELECT id, name, email, lastLogin, isEnabled FROM user WHERE id != ?";

        DatabaseConnection.executeQuery(GET_ALL_USERS_INFO_QUERY, resultSet -> {
            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String lastLogin = resultSet.getString("lastLogin");
                boolean isEnabled = resultSet.getBoolean("isEnabled");

                if (email.trim().endsWith("admin@bms.com")) {
                    listUsers.add(new AdminModel(id, name, email, null, null, null, isEnabled, lastLogin));
                } else {
                    listUsers.add(new EmployeeModel(id, name, email, null, null, null, isEnabled, lastLogin));
                }
            }
        }, excludedUserId);

        return listUsers;
    }
}