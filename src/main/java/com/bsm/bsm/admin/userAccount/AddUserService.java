package com.bsm.bsm.admin.userAccount;


public class AddUserService {
    private final AddUserDAO addUserDAO;

    public AddUserService() {
        addUserDAO = new AddUserDAO();
    }

    public boolean isAdmin(String email) {
        return email.equals("admin@bms.com");
    }

    public boolean hasUserExist(String email) {
        return addUserDAO.checkEmailExists(email);
    }

    public boolean addUser(String name, String dob, String email, String password) {
        String role = isAdmin(email) ? "admin" : "employee";
        return addUserDAO.addUser(name, dob, email, hashPassword(password), role);
    }

    private String hashPassword(String password) {
        return addUserDAO.hashPassword(password);
    }
}
