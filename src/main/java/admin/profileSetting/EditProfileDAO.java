package main.java.admin.profileSetting;

import main.java.database.DatabaseConnection;

import java.sql.SQLException;
import java.sql.Statement;

public class EditProfileDAO {

    private static final Statement statement;

    static {
        try {
            statement = DatabaseConnection.getConnection().createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String... args) {

    }

    public static boolean updateProfile(String name, String mail, String dob) {

        return true;
    }

}
