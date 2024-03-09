package com.bsm.bsm.admin.profileSetting;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EditProfileService {

    private final EditProfileDAO editProfileDAO;
    private final SimpleDateFormat dateFormatter;

    public EditProfileService() {
        this.editProfileDAO = new EditProfileDAO();
        this.dateFormatter = new SimpleDateFormat("yyyy/MM/dd");
    }

    public boolean updateUserProfile(String id, String fullName, String telephone, String dob, String address) throws ParseException {
        String formattedDOB = formatDOB(dob);
        return editProfileDAO.updateProfile(id, fullName, telephone, formattedDOB, address);
    }

    public String formatDOB(String dob) throws ParseException {
            Date parsedDate = dateFormatter.parse(dob);
            return dateFormatter.format(parsedDate);

    }


}
