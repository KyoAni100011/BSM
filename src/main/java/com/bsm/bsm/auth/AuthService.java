package com.bsm.bsm.auth;

import com.bsm.bsm.user.UserModel;
import com.bsm.bsm.user.UserSingleton;

public class AuthService {
    private final AuthDAO authDAO;

    public AuthService() {
        this.authDAO = new AuthDAO();
    }

    public UserModel authenticateUser(String id, String password) {
        if (authDAO.validateUser(id, password)) {
            return authDAO.getUserInfo(id);
        }
        return null;
    }

    public boolean isAdmin(String id) {
        return authDAO.getAdminID(id) != null;
    }

    public boolean isEmployee(String id) {
        return authDAO.getEmployeeID(id) != null;
    }

    public void logOut() {
        UserSingleton.getInstance().destroyInstance();
    }
}
