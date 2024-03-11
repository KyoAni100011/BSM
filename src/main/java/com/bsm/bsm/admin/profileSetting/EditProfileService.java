package com.bsm.bsm.admin.profileSetting;

import com.bsm.bsm.user.UserModel;
import com.bsm.bsm.user.UserSingleton;
import com.bsm.bsm.utils.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class EditProfileService {

    private final EditProfileDAO editProfileDAO;
    // private final SimpleDateFormat dateFormatter;

    public EditProfileService() {
        this.editProfileDAO = new EditProfileDAO();
    }

    public boolean updateUserProfile(String id, String fullName, String telephone, String dob, String address) throws ParseException {
        String formattedDOB = DateUtils.formatDOB(dob);
        return editProfileDAO.updateProfile(id, fullName, telephone, formattedDOB, address);
    }

}
