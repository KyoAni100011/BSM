package com.bsm.bsm.admin.profileSetting;

import com.bsm.bsm.user.UserSingleton;
import com.bsm.bsm.utils.PasswordUtils;
import com.bsm.bsm.utils.ValidationUtils;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.DataInputStream;
import java.io.FileInputStream;

import com.bsm.bsm.utils.AlertUtils;

public class ChangePasswordController {
    @FXML
    private Label currentPasswordErrorLabel, newPasswordErrorLabel , confirmPasswordErrorLabel;

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

    private final ChangePasswordService changePasswordService = new ChangePasswordService();

    @FXML
    public void initialize() {
        confirmPasswordTextField.setVisible(false);
        currentPasswordTextField.setVisible(false);
        newPasswordTextField.setVisible(false);

        showHideCurrentPasswordButton.setOnAction(event -> PasswordUtils.handleShowHidePassword(event, currentPasswordField, currentPasswordTextField, eyeIcon1));
        showHideNewPasswordButton.setOnAction(event -> PasswordUtils.handleShowHidePassword(event, newPasswordField, newPasswordTextField, eyeIcon2));
        showHideConfirmPasswordButton.setOnAction(event -> PasswordUtils.handleShowHidePassword(event, confirmPasswordField, confirmPasswordTextField, eyeIcon3));
    }

    @FXML
    private void handleSaveChanges(ActionEvent event) {
        clearErrorMessages();

        String currentPassword = currentPasswordField.isVisible() ? currentPasswordField.getText() : currentPasswordTextField.getText();
        String newPassword = newPasswordField.isVisible() ? newPasswordField.getText() : newPasswordTextField.getText();
        String confirmPassword = confirmPasswordField.isVisible() ? confirmPasswordField.getText() : confirmPasswordTextField.getText();

        boolean validCurrentPassword = validateCurrentPassword(currentPassword);
        boolean validNewPassword = validateNewPassword(newPassword, currentPassword);
        boolean validConfirmPassword = validateConfirmPassword(newPassword, confirmPassword);

//        need call id!!!
        String id = "11115678";

        if (validCurrentPassword && validNewPassword && validConfirmPassword) {
            if (changePasswordService.changeUserPassword(id, currentPassword, newPassword)) {
                AlertUtils.showAlert("Success", "Password changed successfully", Alert.AlertType.INFORMATION);
                clearInputs();
            } else {
                AlertUtils.showAlert("Error", "Password change failed", Alert.AlertType.ERROR);
            }
        }
    }

    private void clearErrorMessages() {
        currentPasswordErrorLabel.setText("");
        newPasswordErrorLabel.setText("");
        confirmPasswordErrorLabel.setText("");
    }

    private void clearInputs() {
        currentPasswordField.clear();
        newPasswordField.clear();
        confirmPasswordField.clear();
        currentPasswordTextField.clear();
        newPasswordTextField.clear();
        confirmPasswordTextField.clear();
    }

    private boolean validateCurrentPassword(String currentPassword) {
        if (currentPassword.isEmpty()) {
            currentPasswordErrorLabel.setText("Current password is required");
            return false;
        }
        return true;
    }

    private boolean validateNewPassword(String newPassword, String currentPassword) {
        if (newPassword.isEmpty()) {
            newPasswordErrorLabel.setText("New password is required");
            return false;
        }
        else if (newPassword.equals(currentPassword)) {
            newPasswordErrorLabel.setText("New password cannot be the same as the current password");
            return false;
        }
        else if (ValidationUtils.validatePassword(newPassword) != null) {
            newPasswordErrorLabel.setText(ValidationUtils.validatePassword(newPassword));
            return false;
        }
        return true;
    }

    private boolean validateConfirmPassword(String newPassword, String confirmPassword) {
        if (confirmPassword.isEmpty()) {
            confirmPasswordErrorLabel.setText("Please confirm your new password");
            return false;
        }
        if (!newPassword.equals(confirmPassword)) {
            confirmPasswordErrorLabel.setText("Passwords do not match new password");
            return false;
        }
        return true;
    }
}