package com.bsm.bsm.admin.userAccount;

public class UpdateUserService {
    private final UpdateUserDAO updateUserDAO;

    public UpdateUserService(){this.updateUserDAO = new UpdateUserDAO();}

    public UpdateUserDAO getUpdateUserDAO(){return updateUserDAO;}
}
