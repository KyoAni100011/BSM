package com.bsm.bsm.admin.userAccount;

import com.bsm.bsm.database.DatabaseConnection;
import org.mindrot.jbcrypt.BCrypt;

import java.util.concurrent.atomic.AtomicBoolean;

public class AddUserDAO {

    public boolean checkEmailExists(String email) {
        AtomicBoolean hasExisted = new AtomicBoolean(false);
        String QUERY_EMAIL = "select 1 from user where email = ?";

        DatabaseConnection.executeQuery(QUERY_EMAIL, resultSet -> {
            if (resultSet != null && resultSet.next()) {
                hasExisted.set(true);
            }
        }, email);

        return hasExisted.get();
    }

    public String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }

    public boolean addUser(String name, String dob, String email, String password, String role) {
        String PROCEDURE_ADD_USER = """
                CREATE PROCEDURE ADDUSER(
                   IN name varchar(255),
                   IN dob date,
                   IN email varchar(255),
                   IN password varchar(255),
                   IN role varchar(255)
                ) 
                BEGIN
                   INSERT INTO user (name, dob, email, password) VALUES (name, dob, email, password);
                   set @user_id = last_insert_id();
                   if role = 'admin' then
                       INSERT INTO admin (user_id) VALUES (@user_id);
                   else
                       INSERT INTO employee (user_id) VALUES (@user_id);
                   end if;
                END
                """;
        DatabaseConnection.executeProcedure(PROCEDURE_ADD_USER, name, dob, email, password, role);
        return true;
    }
}
