package com.bsm.bsm.account;

import com.bsm.bsm.admin.AdminModel;
import com.bsm.bsm.user.UserDAO;
import com.bsm.bsm.user.UserModel;
import com.bsm.bsm.user.UserSingleton;
import com.bsm.bsm.utils.DateUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class AccountService implements AccountController{
    private final AccountDAO accountDAO;
    private final UserDAO userDAO;

    public AccountService() {
        this.accountDAO = new AccountDAO();
        this.userDAO = new UserDAO();
    }


    @Override
    public void add(UserModel account) {

    }

    @Override
    public List<UserModel> search(String keyword, String excludedUserId) {
        List <UserModel> listUsers = accountDAO.getAllUsersInfo(excludedUserId);

        List<UserModel> filteredUsers = new ArrayList<>();

        for (UserModel user : listUsers) {
            if (user.getId().contains(keyword) ||
                    user.getName().contains(keyword) ||
                    user.getEmail().contains(keyword)) {
                filteredUsers.add(user);
            }
        }

        return filteredUsers;
    }

    @Override
    public List<UserModel> view(String excludedUserId) {
        return accountDAO.getAllUsersInfo(excludedUserId);
    }

    @Override
    public boolean update(String id, String fullName, String telephone, String dob, String address) {
        String formattedDOB = DateUtils.formatDOB(dob);
        return userDAO.updateProfile(id, fullName, telephone, formattedDOB, address);
    }

    private List<UserModel> getUsers() {
        AdminModel admin = (AdminModel) UserSingleton.getInstance().getUser();
        return admin.viewUsers();
    }


    public List<UserModel> getAllUsersBySortInfo(String excludedUserId, String sortOrder, String column) {
        column = switch(column) {
            case "last login" -> "lastLogin";
            case "enable/disable" -> "isEnabled";
            default -> column;
        };

        List<UserModel> listUsers = accountDAO.getAllUsersInfo(excludedUserId);
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

    public boolean updateUserProfile(String id, String fullName, String telephone, String dob, String address) {
        String formattedDOB = DateUtils.formatDOB(dob);
        return accountDAO.updateProfile(id, fullName, telephone, formattedDOB, address);
    }
    public UserModel getUserProfile(String email) {
        return accountDAO.getUserProfile(email);
    }

    public boolean updatePassword(String adminID, String userEmail, String password) {
        System.out.println(userEmail);
        if (!isAdmin(adminID)) {
            return false;
        }

        if (password.isEmpty()) {
            return updatePasswordUsingDOB(userEmail);
        }

        return accountDAO.updatePassword(hashPassword(password), userEmail);
    }

    public boolean isAdmin(String id) {
        return accountDAO.isAdmin(id);
    }

    public boolean hasUserExist(String email) {
        return accountDAO.hasUserExist(email);
    }

    private boolean updatePasswordUsingDOB(String email) {
        return accountDAO.updatePasswordUsingDOB(email);
    }

    private String hashPassword(String password) {
        return accountDAO.hashPassword(password);
    }

    public boolean enableUser(String userId) {
        try {
            return accountDAO.enableUser(userId);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean disableUser(String userId) {
        try {
            return accountDAO.disableUser(userId);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean hasUserExistA(String email) {
        AdminModel admin = (AdminModel) UserSingleton.getInstance().getUser();
        List<UserModel> users = admin.viewUsers();
        for (var user: users) {
            if (user.getEmail().equals(email)) return true;
        }
        return accountDAO.checkEmailExists(email);
    }

    public boolean addUser(String name, String dob, String email, String address,String password) {
        String role = email.endsWith(".admin@bms.com") ? "admin" : "employee";
        boolean result = accountDAO.addUser(name, dob, email, address, (password), role);
        if (result) {
            AdminModel admin = (AdminModel) UserSingleton.getInstance().getUser();
            admin.add(getUserInfo(email));
            UserSingleton.getInstance().setUser(admin);
        }
        return result;
    }

    public UserModel getUserInfo(String email) {
        String role = email.endsWith(".admin@bms.com") ? "admin" : "employee";
        return accountDAO.getUserInfo(email, role);
    }
}
