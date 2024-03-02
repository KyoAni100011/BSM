package com.bsm.bsm.auth;

import com.bsm.bsm.user.UserModel;

import com.bsm.bsm.security.JWTProvider;


public class AuthService {
    private AuthDAO loginDAO;
    static JWTProvider jwtProvider = new JWTProvider();

    public static UserModel authenticateUser(String username, String password) {

        UserModel user = AuthDAO.getUserInfo(username, password);

        if (user != null) {
            user.setToken(JWTProvider.generateJwtToken(user.getId()));
        }



        return user;
    }
}

