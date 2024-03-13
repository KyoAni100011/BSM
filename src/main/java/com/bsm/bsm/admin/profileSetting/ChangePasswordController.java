package com.bsm.bsm.admin.profileSetting;

import com.bsm.bsm.user.UserController;
import com.bsm.bsm.user.UserModel;
import com.bsm.bsm.user.UserService;
import com.bsm.bsm.user.UserSingleton;
import com.bsm.bsm.utils.PasswordUtils;
import com.bsm.bsm.utils.ValidationUtils;
import com.bsm.bsm.utils.AlertUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;

public class ChangePasswordController {

    private final UserModel adminInfo = UserSingleton.getInstance().getUser();

    @FXML
    private Label currentPasswordErrorLabel, newPasswordErrorLabel, confirmPasswordErrorLabel;

    @FXML
    public TextField currentPasswordTextField, confirmPasswordTextField, newPasswordTextField;

    @FXML
    public Button showHideCurrentPasswordButton, showHideNewPasswordButton, showHideConfirmPasswordButton;

    @FXML
    public FontAwesomeIcon eyeIcon1, eyeIcon2, eyeIcon3;

    @FXML
    public PasswordField currentPasswordField, confirmPasswordField, newPasswordField;

    @FXML
    private Button saveChangesButton;

    private UserController userController;

    public ChangePasswordController() {
        userController = new UserService();
    }

    @FXML
    public void initialize() {
        new ChangePasswordController();
        hideTextFields();
        setupShowHidePasswordHandlers();
    }

    private void hideTextFields() {
        confirmPasswordTextField.setVisible(false);
        currentPasswordTextField.setVisible(false);
        newPasswordTextField.setVisible(false);
    }

    private void setupShowHidePasswordHandlers() {
        showHideCurrentPasswordButton.setOnAction(event -> PasswordUtils.handleShowHidePassword(event, currentPasswordField, currentPasswordTextField, eyeIcon1));
        showHideNewPasswordButton.setOnAction(event -> PasswordUtils.handleShowHidePassword(event, newPasswordField, newPasswordTextField, eyeIcon2));
        showHideConfirmPasswordButton.setOnAction(event -> PasswordUtils.handleShowHidePassword(event, confirmPasswordField, confirmPasswordTextField, eyeIcon3));
    }

    @FXML
    private void handleSaveChanges(ActionEvent event) throws Exception {
        clearErrorMessages();

        String currentPassword = getPassword(currentPasswordField, currentPasswordTextField);
        String newPassword = getPassword(newPasswordField, newPasswordTextField);
        String confirmPassword = getPassword(confirmPasswordField, confirmPasswordTextField);

        boolean validCurrentPassword = validateCurrentPassword(currentPassword);
        boolean validNewPassword = validateNewPassword(newPassword, currentPassword);
        boolean validConfirmPassword = validateConfirmPassword(newPassword, confirmPassword);

        if (validCurrentPassword && validNewPassword && validConfirmPassword) {
            if (userController.changePassword(adminInfo.getId(), currentPassword, newPassword)) {
                showAlert("Success", "Password changed successfully", Alert.AlertType.INFORMATION);
                clearInputs();
            } else {
                showAlert("Error", "Password change failed", Alert.AlertType.ERROR);
            }
        }
    }

    private String getPassword(PasswordField passwordField, TextField textField) {
        return passwordField.isVisible() ? passwordField.getText() : textField.getText();
    }

    private void clearErrorMessages() {
        currentPasswordErrorLabel.setText("");
        newPasswordErrorLabel.setText("");
        confirmPasswordErrorLabel.setText("");
    }

    private void clearInputs() {
        clearFields(currentPasswordField, newPasswordField, confirmPasswordField,
                currentPasswordTextField, newPasswordTextField, confirmPasswordTextField);
    }

    private void clearFields(TextField... fields) {
        for (TextField field : fields) {
            field.clear();
        }
    }

    private boolean validateCurrentPassword(String currentPassword) {
        if (currentPassword.isEmpty()) {
            setErrorLabel(currentPasswordErrorLabel, "Current password is required");
            return false;
        }
        return true;
    }

    private boolean validateNewPassword(String newPassword, String currentPassword) {
        if (newPassword.isEmpty()) {
            setErrorLabel(newPasswordErrorLabel, "New password is required");
            return false;
        } else if (newPassword.equals(currentPassword)) {
            setErrorLabel(newPasswordErrorLabel, "New password cannot be the same as the current password");
            return false;
        } else if (ValidationUtils.validatePassword(newPassword) != null) {
            setErrorLabel(newPasswordErrorLabel, ValidationUtils.validatePassword(newPassword));
            return false;
        }
        return true;
    }

    private boolean validateConfirmPassword(String newPassword, String confirmPassword) {
        if (confirmPassword.isEmpty()) {
            setErrorLabel(confirmPasswordErrorLabel, "Please confirm your new password");
            return false;
        }
        if (!newPassword.equals(confirmPassword)) {
            setErrorLabel(confirmPasswordErrorLabel, "Passwords do not match new password");
            return false;
        }
        return true;
    }

    private void setErrorLabel(Label label, String errorMessage) {
        label.setText(errorMessage);
    }

    private void showAlert(String title, String content, Alert.AlertType alertType) {
        AlertUtils.showAlert(title, content, alertType);
    }
}
