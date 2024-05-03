package com.bsm.bsm.auth;

import com.bsm.bsm.user.UserModel;
import com.bsm.bsm.user.UserSingleton;
import com.bsm.bsm.utils.AlertUtils;
import com.bsm.bsm.utils.PasswordUtils;
import com.bsm.bsm.utils.SceneSwitch;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class AuthController {
    private static final String EMPLOYEE_ROLE = "employee";
    private static final String ADMIN_ROLE = "admin";
    private final AuthService authService = new AuthService();
    private final SceneSwitch sceneSwitch = new SceneSwitch();
    public TextField currentPasswordTextField;
    public Button showHideCurrentPasswordButton;
    public FontAwesomeIcon eyeIcon1;
    @FXML
    private AnchorPane AnchorPaneLogin;
    @FXML
    private Button btnLoginAsAdmin;
    @FXML
    private Button btnLoginAsEmployee;
    @FXML
    private TextField idTextField;
    @FXML
    private PasswordField passwordField;

    @FXML
    public void initialize() {
        currentPasswordTextField.setVisible(false);
        showHideCurrentPasswordButton.setOnAction(event -> PasswordUtils.handleShowHidePassword(event, passwordField, currentPasswordTextField, eyeIcon1));

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
        String password = currentPasswordTextField.isVisible() ? currentPasswordTextField.getText() : passwordField.getText();
        String id = idTextField.getText();
        boolean checkWrongField = password.isEmpty() || id.isEmpty();
        String fxmlPath = "";

        if (ADMIN_ROLE.equals(role)) {
            if (!authService.isAdmin(id)) {
                if (!checkWrongField) {
                    AlertUtils.showAlert("Error", "Invalid username or password.", Alert.AlertType.ERROR);
                }
                return;
            }
            fxmlPath = "/com/bsm/bsm/view/admin/adminMainScreen.fxml";
        } else if (EMPLOYEE_ROLE.equals(role)) {
            if (!authService.isEmployee(id)) {
                if (!checkWrongField) {
                    AlertUtils.showAlert("Error", "Invalid username or password.", Alert.AlertType.ERROR);
                }
                System.out.println("Invalid username or password.");
                return;
            }
            fxmlPath = "/com/bsm/bsm/view/employee/employeeMainScreen.fxml";
        } else {
            if (!checkWrongField) {
                AlertUtils.showAlert("Error", "Invalid username or password.", Alert.AlertType.ERROR);
            }
            return;
        }

        UserModel userInfo = authService.authenticateUser(id, password);
        if (userInfo == null) {
            if (!checkWrongField) {
                AlertUtils.showAlert("Error", "Invalid username or password.", Alert.AlertType.ERROR);
            }
            return;
        }

        System.out.println(userInfo.isEnabled());

        if (!userInfo.isEnabled()) {
            AlertUtils.showAlert("Error", "Your account is disable.", Alert.AlertType.ERROR);
            return;
        }

        UserSingleton.getInstance().setUser(userInfo);
        sceneSwitch.SceneSwitchDifferSize(AnchorPaneLogin, fxmlPath);
        clearInputs();
    }

    private void clearInputs() {
        idTextField.clear();
        passwordField.clear();
        currentPasswordTextField.clear();
    }
}