package com.bsm.bsm.admin.profileSetting;

import com.bsm.bsm.database.DatabaseConnection;

import java.io.*;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

public class EditProfileDAO {
    private final String QUERY_UPDATE_USER = "UPDATE user SET name = ?, dob = ?, telephone = ?, address = ? where id = ?";

    public static void main(String[] args) {

//        updateProfile("Minh Thu", "0707070707", "01/05/2003", "123 abc");
    }

    public boolean updateProfile(String fullName, String telephone, String dob, String address) {
//        String idTemp = fetchUserIdFromFile();
//        if (idTemp.isEmpty()) {
//            System.out.println("Cannot read id from file");
//            return false;
//        }
        String idTemp = "11115678";

        try {
            String formattedDate = formatDate(dob);
            if (formattedDate.isEmpty()) {
                System.out.println("Date is invalid");
                return false;
            }

            int rowsAffected = DatabaseConnection.executeUpdate(QUERY_UPDATE_USER, fullName, formattedDate, telephone, address, idTemp);
            if (rowsAffected > 0) {
                System.out.println("Profile updated successfully");
                return true;
            } else {
                System.out.println("Profile update failed");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public String fetchUserIdFromFile() {
        try (DataInputStream dataStream = new DataInputStream(new FileInputStream("src/main/java/com/bsm/bsm/auth/saveEmailTemp.txt"))) {
            return dataStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String formatDate(String dob) {
        try {
            java.util.Date date = new SimpleDateFormat("dd/MM/yyyy").parse(dob);
            return new SimpleDateFormat("yyyy-MM-dd").format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
