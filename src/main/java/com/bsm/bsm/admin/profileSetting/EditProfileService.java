package com.bsm.bsm.admin.profileSetting;

public class EditProfileService {

    private final EditProfileDAO editProfileDAO;

    public EditProfileService() {
        this.editProfileDAO = new EditProfileDAO();
    }

    public EditProfileDAO getEditProfileDAO() {
        return editProfileDAO;
    }
}
