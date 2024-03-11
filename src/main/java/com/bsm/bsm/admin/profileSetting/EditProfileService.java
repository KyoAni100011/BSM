package com.bsm.bsm.admin.profileSetting;

import com.bsm.bsm.user.UserModel;
import com.bsm.bsm.user.UserSingleton;

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
        String formattedDOB = formatDOB(dob);
        updateUserInformation(fullName, telephone, formattedDOB, address);
        return editProfileDAO.updateProfile(id, fullName, telephone, formattedDOB, address);
    }

    private void updateUserInformation(String fullName, String telephone, String dob, String address) throws ParseException {
        UserModel user = UserSingleton.getInstance().getUser();
        user.setName(fullName);
        user.setPhone(telephone);
        user.setAddress(address);
        user.setDob(dob);
    }

    public String convertDOBFormat(String dob) {
        return LocalDate.parse(dob, DateTimeFormatter.ofPattern("yyyy-MM-dd")).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    private String formatDOB(String dob) {
        return LocalDate.parse(dob, DateTimeFormatter.ofPattern("dd/MM/yyyy")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}
