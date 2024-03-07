package com.bsm.bsm.auth;

import com.bsm.bsm.user.UserModel;
import com.bsm.bsm.user.UserSingleton;
import com.bsm.bsm.utils.SceneSwitch;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.io.IOException;

import static com.bsm.bsm.utils.ValidationUtils.validateEmailRegex;

public class AuthController {
    private final AuthService authService = new AuthService();

    @FXML
    private AnchorPane AnchorPaneLogin;

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

    private static final String EMPLOYEE_SUFFIX = ".employee@bms.com";
    private static final String ADMIN_SUFFIX = ".admin@bms.com";

    @FXML
    public void initialize() {
        btnLoginAsEmployee.setOnAction(event -> {
            try {
                handleLoginButtonClicked(EMPLOYEE_SUFFIX);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        btnLoginAsAdmin.setOnAction(event -> {
            try {
                handleLoginButtonClicked(ADMIN_SUFFIX);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void handleLoginButtonClicked(String suffix) throws IOException {
        String password = passwordField.getText();
        String email = emailTextField.getText();
        int passwordLength = password.length();

        if (!suffix.equals(getEmailSuffix(email))) {
            System.out.println("Wrong type of user");
            return;
        }

        passwordErrorText.setVisible(passwordLength < 8 || passwordLength > 255);
        emailErrorLabel.setVisible(!validateEmailRegex(email));

        UserModel userInfo = authService.authenticateUser(email, password);
        if (userInfo != null && suffix.equals(getEmailSuffix(userInfo.getEmail()))) {
            System.out.println("Login successful!");
            UserSingleton.getInstance().setUser(userInfo);

            String fxmlPath = (suffix.equals(EMPLOYEE_SUFFIX)) ?
                    "/com/bsm/bsm/view/employee/employeeMainScreen.fxml" :
                    "/com/bsm/bsm/view/admin/adminMainScreen.fxml";

            new SceneSwitch(AnchorPaneLogin, fxmlPath);
        } else {
            System.out.println("Invalid username or password.");
        }
    }

    private String getEmailSuffix(String email) {
        int atIndex = email.indexOf(".");
        return (atIndex != -1) ? email.substring(atIndex) : "";
    }
}
