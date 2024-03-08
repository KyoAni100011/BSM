package com.bsm.bsm.admin.profileSetting;

import java.text.SimpleDateFormat;
import java.util.Date;

public class EditProfileService {

    private final EditProfileDAO editProfileDAO;

    public EditProfileService() {
        this.editProfileDAO = new EditProfileDAO();
    }

    public boolean updateUserProfile(String id, String fullName, String telephone, String dob, String address) {
        String formattedDOB = formatDOB(dob);
        return editProfileDAO.updateProfile(id, fullName, telephone, formattedDOB, address);
    }

    public String formatDOB(String dob) {
        try {
            SimpleDateFormat formatter =  new SimpleDateFormat("yyyy/MM/dd");
            return formatter.format(new Date());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
