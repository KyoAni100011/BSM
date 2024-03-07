package com.bsm.bsm.auth;

import com.bsm.bsm.user.UserModel;


public class AuthService {
    private AuthDAO loginDAO;

    public static UserModel authenticateUser(String username, String password) {

        UserModel user = AuthDAO.getUserInfo(username, password);

        return user;
    }
}

