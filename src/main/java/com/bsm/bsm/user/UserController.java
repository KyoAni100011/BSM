package com.bsm.bsm.user;

public interface UserController {
    default boolean verifyUser(String email, String password) throws Exception {
        throw new Exception();
    }

    default void login(String id, String password) throws Exception {
        throw new Exception();
    }

    void logout();

    default boolean changePassword(String id, String currPass, String newPass) throws Exception {
        throw new Exception();
    }

    default boolean editProfile(String id, String fullName, String telephone, String dob, String address) throws Exception {
        throw new Exception();
    }
}
