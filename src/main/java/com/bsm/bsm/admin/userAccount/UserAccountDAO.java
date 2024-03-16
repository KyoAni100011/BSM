package com.bsm.bsm.admin.userAccount;

import com.bsm.bsm.admin.AdminModel;
import com.bsm.bsm.database.DatabaseConnection;
import com.bsm.bsm.employee.EmployeeModel;
import com.bsm.bsm.user.UserModel;
import com.bsm.bsm.user.UserSingleton;

import java.util.ArrayList;
import java.util.List;

public class UserAccountDAO {

    public List<UserModel> getAllUsersInfo(String excludedUserId, String sortOrder, String column) {

        List<UserModel> listUsers = new ArrayList<>();
        String GET_ALL_USERS_INFO_QUERY = String.format("SELECT * FROM user WHERE id != '%s' ORDER BY %s %s", excludedUserId, column, sortOrder);

        DatabaseConnection.executeQuery(GET_ALL_USERS_INFO_QUERY, resultSet -> {
            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String lastLogin = resultSet.getString("lastLogin");
                boolean isEnabled = resultSet.getBoolean("isEnabled");

                if (email.endsWith("admin@bms.com")) {
                    listUsers.add(new AdminModel(id, name, email, null, null, null, isEnabled, lastLogin));
                } else {
                    listUsers.add(new EmployeeModel(id, name, email, null, null, null, isEnabled, lastLogin));
                }
            }
        });

        return listUsers;
    }
}