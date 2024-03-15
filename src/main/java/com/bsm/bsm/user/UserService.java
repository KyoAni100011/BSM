package com.bsm.bsm.user;

import com.bsm.bsm.utils.DateUtils;

import java.sql.SQLException;

public class UserService implements UserController {

    private final UserDAO userDAO;

    public UserService() {
        this.userDAO = new UserDAO();
    }

    @Override
    public boolean verifyUser(String id, String password) {
        return userDAO.validateUser(id, password);
    }

    @Override
    public void login(String id, String password) {
        if (verifyUser(id, password)) {
            UserModel infoUser = userDAO.getUserInfo(id);
            UserSingleton.getInstance().setUser(infoUser);
        }
    }
    @Override
    public void logout() {
        UserSingleton.getInstance().destroyInstance();
    }

    @Override
    public boolean changePassword(String id, String currPass, String newPass) throws SQLException {
        if (verifyPassword(id, currPass)) {
            return userDAO.changePassword(id, newPass);
        }
        return false;
    }

    @Override
    public boolean editProfile(String id, String fullName, String telephone, String dob, String address) {
        String formattedDOB = DateUtils.formatDOB(dob);
        return userDAO.updateProfile(id, fullName, telephone, formattedDOB, address);
    }

    public boolean verifyPassword(String id, String currentPassword) throws SQLException {
        return userDAO.validatePassword(id, currentPassword);
    }
}
