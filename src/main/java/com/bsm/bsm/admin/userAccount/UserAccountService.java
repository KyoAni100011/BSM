package com.bsm.bsm.admin.userAccount;

import com.bsm.bsm.admin.AdminModel;
import com.bsm.bsm.commonInterface.Sortable;
import com.bsm.bsm.user.UserModel;
import com.bsm.bsm.user.UserSingleton;

import java.util.Comparator;
import java.util.List;

public class UserAccountService implements Sortable<UserModel> {
    private final UserAccountDAO userAccountDAO;

    public UserAccountService() {
        this.userAccountDAO = new UserAccountDAO();
    }

    private List<UserModel> getUsers() {
        AdminModel admin = (AdminModel) UserSingleton.getInstance().getUser();
        return admin.viewUsers();
    }


    public List<UserModel> getAllUsersInfo(String excludedUserId, String sortOrder, String column) {
        column = switch(column) {
            case "Last login" -> "lastLogin";
            case "Action" -> "isEnabled";
            default -> column;
        };

        List<UserModel> listUsers = getUsers();
        if (listUsers.isEmpty()) {
           listUsers = userAccountDAO.getAllUsersInfo(excludedUserId);
        }

        listUsers = sort(listUsers, sortOrder.equalsIgnoreCase("asc"), column);
        return listUsers;
    }

    @Override
    public List<UserModel> sort(List<UserModel> users, boolean isAscending, String column) {
        Comparator<UserModel> comparator = null;

        switch (column.toLowerCase()) {
            case "id" -> {
                comparator = Comparator.comparing(UserModel::getId);
            }
            case "name" -> {
                comparator = Comparator.comparing(UserModel::getName);
            }
            case "email" -> {
                comparator = Comparator.comparing(UserModel::getEmail);
            }
            case "lastlogin" -> {
                comparator = Comparator.comparing(UserModel::getLastLogin);
            }
            case "isenabled" -> {
                comparator = Comparator.comparing(UserModel::isEnabled);
            }
        }

        if (!isAscending) {
            assert comparator != null;
            comparator = comparator.reversed();
        }

        users.sort(comparator);
        return users;
    }
}
