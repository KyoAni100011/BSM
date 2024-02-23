package main.java.auth;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class LoginController {

    @FXML
    private PasswordField passwordField;

    @FXML
    private Text passwordErrorText;

    @FXML
    private TextField emailTextField;

    @FXML
    private Text emailErrorLabel;

    @FXML
    private Button close;

    @FXML
    private void initialize() {
        // You can perform initialization here if needed
    }

    @FXML
    private void close() {
        System.exit(0);
    }

    @FXML
    private boolean validateEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        if (email.matches(emailRegex) && !email.isEmpty() && email.length() <= 255) {
            return true;
        } else {
            return false;
        }
    }

    @FXML
    private void handleLoginButtonClicked() {
        String password = passwordField.getText();
        String email = emailTextField.getText();
        int passwordLength = password.length();
        if (passwordLength < 8 || passwordLength > 255) {
            passwordErrorText.setVisible(true);
        } else {
            passwordErrorText.setVisible(false);
        }
        if (validateEmail(email)) {
            emailErrorLabel.setVisible(false);
        } else {
            emailErrorLabel.setVisible(true);
        }
        System.out.println(validateEmail(email));

    }
}