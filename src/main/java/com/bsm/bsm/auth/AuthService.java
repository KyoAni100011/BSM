package com.bsm.bsm.auth;

import com.bsm.bsm.user.UserModel;
import com.bsm.bsm.user.UserSingleton;

import java.util.Objects;

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
        return !Objects.equals(authDAO.getAdminID(id), "");
    }

    public boolean isEmployee(String id) {
        return !Objects.equals(authDAO.getEmployeeID(id), "");
    }

    public void logOut() {
        UserSingleton.getInstance().destroyInstance();
    }
}
