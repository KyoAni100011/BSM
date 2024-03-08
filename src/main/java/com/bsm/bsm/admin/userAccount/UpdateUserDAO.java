package com.bsm.bsm.admin.userAccount;

import com.bsm.bsm.database.DatabaseConnection;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;

public class UpdateUserDAO {
    private static final Statement statement;

    static {
        try {
            statement = DatabaseConnection.getConnection().createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {

//        updateProfile("thu.admin@bms.com", "Thu", "01/05/2003");
    }

    public static boolean updateProfile(String email, String fullName, String dob) {
        if (!validateEmail(email)) {
            System.out.println("Email is invalid, error formatting email");
            return false;
        }

        String emailTemp = getEmailFromFile();
        if (emailTemp == null) {
            System.out.println("Cannot read email from file");
            return false;
        }

        try {
            String formattedDate = formatDate(dob);
            if (formattedDate == null) {
                System.out.println("Date is invalid");
                return false;
            }

            String QUERY_UPDATE_USER = "UPDATE user SET EMAIL = '%s', NAME = '%s', DOB='%s' WHERE EMAIL='%s'".formatted(email, fullName, formattedDate, emailTemp);

            int rowsAffected = statement.executeUpdate(QUERY_UPDATE_USER);
            if (rowsAffected > 0) {
                System.out.println("Profile updated successfully");
                printUserDetails(email);
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

    private static boolean validateEmail(String email) {
        String adminEmailSuffix = "admin@bms.com";
        String employeeEmailSuffix = "employee@bms.com";

        if (email.isEmpty()) {
            System.out.println("Email is empty");
            return false;
        } else if (email.endsWith(adminEmailSuffix) || email.endsWith(employeeEmailSuffix)) {
            System.out.println("Email is valid");
            return true;
        } else {
            return false;
        }
    }

    private static String getEmailFromFile() {
        try (DataInputStream objStream = new DataInputStream(new FileInputStream("src/main/java/com/bsm/bsm/auth/saveEmailTemp.txt"))) {
            return objStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String formatDate(String dob) {
        try {
            java.util.Date date = new SimpleDateFormat("dd/MM/yyyy").parse(dob);
            return new SimpleDateFormat("yyyy-MM-dd").format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    private static void printUserDetails(String email) throws SQLException {
        String QUERY_SELECT_USER = "SELECT * FROM user WHERE email='%s'".formatted(email);
        ResultSet resultSet = statement.executeQuery(QUERY_SELECT_USER);
        if (resultSet.next()) {
            for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                System.out.println(resultSet.getMetaData().getColumnName(i) + ": " + resultSet.getString(i));
            }
        }
    }
}
