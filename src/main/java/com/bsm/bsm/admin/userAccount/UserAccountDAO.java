package com.bsm.bsm.admin.userAccount;

import com.bsm.bsm.admin.AdminModel;
import com.bsm.bsm.database.DatabaseConnection;
import com.bsm.bsm.user.UserModel;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserAccountDAO {
    private static final String GET_ALL_USERS_INFO_QUERY = "SELECT * FROM user";

    
    public AdminModel getAllUsersInfo() {
    AdminModel adminModel = new AdminModel();

        DatabaseConnection.executeQuery(GET_ALL_USERS_INFO_QUERY, resultSet -> {
            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String email = resultSet.getString("email");
                String dob = resultSet.getString("dob");
                String name = resultSet.getString("name");
                String phone = resultSet.getString("telephone");
                String address = resultSet.getString("address");
                boolean isEnabled = resultSet.getBoolean("isEnabled");
                adminModel.addNewUser(id, name, email, dob, phone, address, isEnabled);
            }
        });

        return adminModel;
}
}