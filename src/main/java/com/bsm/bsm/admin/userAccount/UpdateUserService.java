package com.bsm.bsm.admin.userAccount;

import com.bsm.bsm.admin.profileSetting.EditProfileDAO;

import java.text.SimpleDateFormat;
import java.util.Date;

public class UpdateUserService {
    private final UpdateUserDAO updateUserDAO;

    public UpdateUserService() {
        this.updateUserDAO = new UpdateUserDAO();
    }

    public boolean updateUserProfile(String id, String fullName, String telephone, String dob, String address) {
        String formattedDOB = formatDOB(dob);
        return updateUserDAO.updateProfile(id, fullName, telephone, formattedDOB, address);
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
