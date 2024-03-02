package com.bsm.bsm.admin.profileSetting;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

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

    @FXML
    public void initialize() {
        confirmPasswordTextField.setVisible(false);
        currentPasswordTextField.setVisible(false);
        newPasswordTextField.setVisible(false);

        showHideCurrentPasswordButton.setOnAction(event -> handleShowHidePassword(event, currentPasswordField, currentPasswordTextField, eyeIcon1));
        showHideNewPasswordButton.setOnAction(event -> handleShowHidePassword(event, newPasswordField, newPasswordTextField, eyeIcon2));
        showHideConfirmPasswordButton.setOnAction(event -> handleShowHidePassword(event, confirmPasswordField, confirmPasswordTextField, eyeIcon3));
    }

    private void handleShowHidePassword(ActionEvent event, PasswordField passwordField ,TextField textField, FontAwesomeIcon eyeIcon) {
        if (passwordField.isVisible()) {
            passwordField.setVisible(false);
            textField.setText(passwordField.getText());
            textField.setVisible(true);
            eyeIcon.setGlyphName("EYE");
        } else {
            passwordField.setText(textField.getText());
            passwordField.setVisible(true);
            textField.setVisible(false);
            eyeIcon.setGlyphName("EYE_SLASH");
        }
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

        if (validCurrentPassword && validNewPassword && validConfirmPassword) {
            // Save the changes
            showAlert("Success", "Password changed successfully", Alert.AlertType.INFORMATION);
        }
    }

    private void clearErrorMessages() {
        currentPasswordErrorLabel.setText("");
        newPasswordErrorLabel.setText("");
        confirmPasswordErrorLabel.setText("");
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

    private void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}