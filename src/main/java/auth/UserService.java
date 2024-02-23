package main.java.auth;

public class UserService {
    private UserDAO userDao;

    public UserService(UserDAO userDao) {
        this.userDao = userDao;
    }

    public boolean authenticateUser(String username, String password) {
        UserModel user = userDao.getUserByUsername(username);
        return user != null && user.getPassword().equals(password);
    }
}
