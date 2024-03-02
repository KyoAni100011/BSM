package main.java.admin.profileSetting;

public class EditProfileService {

    private EditProfileDAO editProfileDAO;

    public EditProfileService() {
        this.editProfileDAO = new EditProfileDAO();
    }

    public EditProfileDAO getEditProfileDAO() {
        return editProfileDAO;
    }
}
