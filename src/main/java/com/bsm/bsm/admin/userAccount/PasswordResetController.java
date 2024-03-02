package com.bsm.bsm.admin.userAccount;

import com.bsm.bsm.utils.AlertUtils;
import com.bsm.bsm.utils.ValidationUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;

public class PasswordResetController {
    @FXML
    private TextField emailField, customPassword;
    @FXML
    private Button resetButton;
    @FXML
    private Label emailErrorLabel, passwordErrorLabel;

    @FXML
    public void initialize() {
        clearErrorMessages();
    }

    @FXML
    private void handleResetButtonAction(ActionEvent event) {
        clearErrorMessages();
        String email = emailField.getText().trim();
        String password = customPassword.getText();

        String emailValidationMessage = ValidationUtils.validateEmail(email);
        String passwordValidationMessage = ValidationUtils.validatePassword(password);

        if (emailValidationMessage == null && passwordValidationMessage == null) {
            // Logic to reset the password here
            System.out.println("Password reset successful for " + email);

            // Show success alert
            AlertUtils.showAlert("Success", "Password reset successful!", Alert.AlertType.INFORMATION);

            // Reset error messages
            clearErrorMessages();
            clearInputs();
        } else {
            if (emailValidationMessage != null) {
                emailErrorLabel.setText(emailValidationMessage);
            }
            if (passwordValidationMessage != null) {
                passwordErrorLabel.setText(passwordValidationMessage);
            }
        }
    }

    private void clearErrorMessages() {
        emailErrorLabel.setText("");
        passwordErrorLabel.setText("");
    }

    private void clearInputs() {
        emailField.clear();
        customPassword.clear();
    }
}
