package com.bsm.bsm.admin.profileSetting;

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
        System.out.println("id = " + id + ", fullName = " + fullName + ", telephone = " + telephone + ", dob = " + dob + ", address = " + address);
        String formattedDOB = formatDOB(dob);
        return editProfileDAO.updateProfile(id, fullName, telephone, formattedDOB, address);
    }

    public String convertDOBFormat(String dob) {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date = LocalDate.parse(dob, inputFormatter);
        return outputFormatter.format(date);
    }

    public String formatDOB(String dob) throws ParseException {
        SimpleDateFormat inputFormatter = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat outputFormatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = inputFormatter.parse(dob);
        return outputFormatter.format(date);
    }


}
