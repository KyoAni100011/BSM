package main.java.auth;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class UserController {
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        // UserService
        if (userService.authenticateUser(username, password)) {
            System.out.println("Login successful!");

        } else {
            System.out.println("Login failed. Please check your credentials.");

        }
    }
}
