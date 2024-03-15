package com.bsm.bsm.user;

public class UserSingleton {
    private static final UserSingleton instance = new UserSingleton();
    private UserModel user;

    private UserSingleton() {

    }
    public static UserSingleton getInstance() {
        return instance;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public void destroyInstance() {
        instance.user = null;
    }
}
