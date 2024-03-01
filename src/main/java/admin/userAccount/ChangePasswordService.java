package main.java.admin.userAccount;

public class ChangePasswordService {

    private ChangePasswordDAO changePasswordDAO;

    public ChangePasswordService() {
        this.changePasswordDAO = new ChangePasswordDAO();
    }

    public ChangePasswordDAO getChangePasswordDAO() {
        return changePasswordDAO;
    }
}
