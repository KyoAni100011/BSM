package com.bsm.bsm.auth;

import com.bsm.bsm.user.UserModel;
import com.bsm.bsm.utils.FXMLLoaderHelper;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class AuthController {
    @FXML
    private Button btnLoginAsAdmin;

    @FXML
    private Button btnLoginAsEmployee;


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
                handleLoginAsEmployeeButtonClicked();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        btnLoginAsAdmin.setOnAction(event -> {
            try {
                handleLoginAsAdminButtonClicked();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private boolean validateEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return email.matches(emailRegex) && !email.isEmpty() && email.length() <= 255;
    }

    private void handleLoginAsEmployeeButtonClicked() throws IOException {
        String password = passwordField.getText();
        String email = emailTextField.getText();
        int passwordLength = password.length();

        if (!(".employee@bms.com".equals(getEmailSuffix(email)))) {
            System.out.println("Wrong type of user");
            return;
        }

        passwordErrorText.setVisible(passwordLength < 8 || passwordLength > 255);
        emailErrorLabel.setVisible(!validateEmail(email));


        UserModel userInfo = AuthService.authenticateUser(email, password);
        if (userInfo != null) {
            if (!(".employee@bms.com".equals(getEmailSuffix(userInfo.getEmail())))) {
                System.out.println("Wrong type of user");
                return;
            }

            System.out.println("Login successful!");

            //save email from saveEmailTemp.txt

            try (DataOutputStream dataStream = new DataOutputStream(new FileOutputStream("src/main/java/com/bsm/bsm/auth/saveEmailTemp.txt"))) {
                dataStream.writeUTF(email);
                System.out.println("Saved successfully!");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            FXMLLoaderHelper.loadFXML((Stage) btnLoginAsEmployee.getScene().getWindow(), "employee/employeeMainScreen");
        } else {
            System.out.println("Invalid username or password.");
        }
    }

    private void handleLoginAsAdminButtonClicked() throws IOException {
        String password = passwordField.getText();
        String email = emailTextField.getText();
        int passwordLength = password.length();

        if (!(".admin@bms.com".equals(getEmailSuffix(email)))) {
            System.out.println("Wrong type of user");
            return;
        }

        passwordErrorText.setVisible(passwordLength < 8 || passwordLength > 255);
        emailErrorLabel.setVisible(!validateEmail(email));


        UserModel userInfo = AuthService.authenticateUser(email, password);
        if (userInfo != null) {
            if (!(".admin@bms.com".equals(getEmailSuffix(userInfo.getEmail())))) {
                System.out.println("Wrong type of user");
                return;
            }

            System.out.println("Login successful!");

            //save email from saveEmailTemp.txt

            try (DataOutputStream dataStream = new DataOutputStream(new FileOutputStream("src/main/java/com/bsm/bsm/auth/saveEmailTemp.txt"))) {
                dataStream.writeUTF(email);
                System.out.println("Saved successfully!");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            FXMLLoaderHelper.loadFXML((Stage) btnLoginAsEmployee.getScene().getWindow(), "admin/adminMainScreen");
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
