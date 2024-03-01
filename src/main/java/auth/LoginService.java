package main.java.auth;

public class LoginService {

    private LoginDAO loginDAO;

    public LoginService() {
        this.loginDAO = new LoginDAO();
    }

    public UserModel authenticateUser(String username, String password) {
        return loginDAO.getUserInfo(username, password);
    }
}