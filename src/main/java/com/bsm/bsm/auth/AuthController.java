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
    private TextField idTextField;

    @FXML
    private Text passwordErrorText;

    @FXML
    private PasswordField passwordField;

    private static final String EMPLOYEE_ROLE = "employee";
    private static final String ADMIN_ROLE = "admin";

    @FXML
    public void initialize() {
        btnLoginAsEmployee.setOnAction(event -> {
            try {
                handleLoginButtonClicked(EMPLOYEE_ROLE);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        btnLoginAsAdmin.setOnAction(event -> {
            try {
                handleLoginButtonClicked(ADMIN_ROLE);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void handleLoginButtonClicked(String role) throws IOException {
        String password = passwordField.getText();
        String id = idTextField.getText();
        int passwordLength = password.length();

        passwordErrorText.setVisible(passwordLength < 8 || passwordLength > 255);

        String fxmlPath = "";

        if (ADMIN_ROLE.equals(role)) {
            if (!authService.isAdmin(id)) {
                System.out.println("Invalid username or password.");
                return;
            }
            fxmlPath = "/com/bsm/bsm/view/admin/adminMainScreen.fxml";
        } else if (EMPLOYEE_ROLE.equals(role)) {
            if (!authService.isEmployee(id)) {
                System.out.println("Invalid username or password.");
                return;
            }
            fxmlPath = "/com/bsm/bsm/view/employee/employeeMainScreen.fxml";
        } else {
            System.out.println("Invalid role.");
            return;
        }

        UserModel userInfo = authService.authenticateUser(id, password);
        if (userInfo == null) {
            System.out.println("Invalid username or password.");
            return;
        }

        UserSingleton.getInstance().setUser(userInfo);
        new SceneSwitch(AnchorPaneLogin, fxmlPath);
    }
}
