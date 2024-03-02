package com.bsm.bsm.auth;

import com.bsm.bsm.database.DatabaseConnection;
import com.bsm.bsm.user.UserModel;
import com.bsm.bsm.utils.FXMLLoaderHelper;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Arrays;

import static com.bsm.bsm.security.JWTProvider.decodeJwtToken;
import static com.bsm.bsm.utils.convertProvider.bytesToHexString;
import static com.bsm.bsm.utils.convertProvider.reverseHexString;

public class AuthController {
    @FXML
    private Button btnLoginAsAdmin;

    @FXML
    private Button btnLoginAsEmployee;

    @FXML
    private Button close;

    @FXML
    private Text emailErrorLabel;

    @FXML
    private TextField emailTextField;

    @FXML
    private Text passwordErrorText;

    @FXML
    private PasswordField passwordField;

    @FXML
    public void initialize() {
        btnLoginAsEmployee.setOnAction(event -> {
            try {
                handleLoginButtonClicked();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        btnLoginAsAdmin.setOnAction(event -> {
            try {
                handleLoginButtonClicked();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private boolean validateEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return email.matches(emailRegex) && !email.isEmpty() && email.length() <= 255;
    }


    private void handleLoginButtonClicked() throws IOException {
        String password = passwordField.getText();
        String email = emailTextField.getText();
        int passwordLength = password.length();

        passwordErrorText.setVisible(passwordLength < 8 || passwordLength > 255);
        emailErrorLabel.setVisible(!validateEmail(email));

        UserModel userInfo = AuthService.authenticateUser(email, password);
        if (userInfo != null) {
            if (".admin@bms.com".equals(getEmailSuffix(userInfo.getEmail()))) {
                FXMLLoaderHelper.loadFXML((Stage) close.getScene().getWindow(), "admin/adminMainScreen");
            } else if (".employee@bms.com".equals(getEmailSuffix(userInfo.getEmail()))) {
                FXMLLoaderHelper.loadFXML((Stage) close.getScene().getWindow(), "employee/employeeMainScreen");
            } else {
                System.out.println("Unknown user type.");
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
