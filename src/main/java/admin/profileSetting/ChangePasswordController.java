package main.java.admin.profileSetting;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ChangePasswordController {
    @FXML
    private Label currentPasswordErrorLabel;
    @FXML
    private Label newPasswordErrorLabel;
    @FXML
    private Label confirmPasswordErrorLabel;
    @FXML
    private PasswordField currentPasswordField;
    @FXML
    private PasswordField newPasswordField;
    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private Button saveChangesButton;

    @FXML
    public void initialize() {

    }

    @FXML
    private void handleSaveChanges(ActionEvent event) {
        clearErrorMessages();
        String currentPassword = currentPasswordField.getText();
        String newPassword = newPasswordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        boolean validCurrentPassword = validateCurrentPassword(currentPassword);
        boolean validNewPassword = validateNewPassword(newPassword);
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

    private boolean validateNewPassword(String newPassword) {
        if (newPassword.isEmpty()) {
            newPasswordErrorLabel.setText("New password is required");
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
            confirmPasswordErrorLabel.setText("Passwords do not match");
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