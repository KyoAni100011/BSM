package com.bsm.bsm.admin.profileSetting;

public class EditProfileService {

    private final EditProfileDAO editProfileDAO;

    public EditProfileService() {
        this.editProfileDAO = new EditProfileDAO();
    }

    public boolean updateUserProfile(String fullName, String telephone, String dob, String address) {
        return editProfileDAO.updateProfile(fullName, telephone, dob, address);
    }
}
