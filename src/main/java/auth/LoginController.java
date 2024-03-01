package main.java.auth;

import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.java.utils.FXMLLoaderHelper;

import java.io.IOException;

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

    private final LoginService loginService = new LoginService();

    @FXML
    private void initialize() {

    }

    @FXML
    private void close() {
        System.exit(0);
    }

    @FXML
    private boolean validateEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return email.matches(emailRegex) && !email.isEmpty() && email.length() <= 255;
    }

    @FXML
    private void handleLoginButtonClicked() throws IOException {
        String password = passwordField.getText();
        String email = emailTextField.getText();
        int passwordLength = password.length();

        passwordErrorText.setVisible(passwordLength < 8 || passwordLength > 255);
        emailErrorLabel.setVisible(!validateEmail(email));

        System.out.println(validateEmail(email));

        UserModel userInfo = loginService.authenticateUser(email, password);
        if (userInfo != null) {
            System.out.println("Login successful!");
            System.out.println(userInfo.toString());
            System.out.println(getEmailSuffix(userInfo.getEmail()));
            if (".admin@bms.com".equals(getEmailSuffix(userInfo.getEmail()))) {
                FXMLLoaderHelper.loadFXML((Stage) close.getScene().getWindow(), "mainScreen");
            } else if (".employee@bms.com".equals(getEmailSuffix(userInfo.getEmail()))) {
                FXMLLoaderHelper.loadFXML((Stage) close.getScene().getWindow(), "employeeScreen");
            } else {
                System.out.println("Unknown user type.");
                return;
            }
        } else {
            System.out.println("Invalid username or password.");
        }
    }

    private String getEmailSuffix(String email) {
        int atIndex = email.indexOf(".");
        if (atIndex != -1) {
            return email.substring(atIndex);
        } else {
            return "";
        }
    }
}
