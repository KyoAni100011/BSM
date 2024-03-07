package com.bsm.bsm.auth;

import com.bsm.bsm.user.UserModel;
import com.bsm.bsm.user.UserSingleton;

public class AuthService {
    public AuthDAO authDAO;

    public AuthService() {
        this.authDAO = new AuthDAO();
    }

    public UserModel authenticateUser(String email, String password) {
        if (authDAO.validateUser(email, password)) {
            return authDAO.getUserInfo(email);
        }
        return null;
    }

    public void logOut(){
        UserSingleton.getInstance().destroyInstance();
    }
}
