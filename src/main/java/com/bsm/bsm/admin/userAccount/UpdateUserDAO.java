package com.bsm.bsm.admin.userAccount;

import com.bsm.bsm.database.DatabaseConnection;
import com.bsm.bsm.user.UserModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UpdateUserDAO {
    private final String UPDATE_USER_QUERY = "UPDATE user SET name = ?, dob = ?, telephone = ?, address = ? where id = ?";
    private final String GET_USER_QUERY = "SELECT user.id,user.name, user.dob, user.telephone, user.address  FROM user where email = ?";

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
}
