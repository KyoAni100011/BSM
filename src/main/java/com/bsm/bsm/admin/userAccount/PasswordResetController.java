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
        String email = emailField.getText();
        String password = customPassword.getText();

        if (validateInputs(email, password)) {
            AlertUtils.showAlert("Success", "Profile updated successfully.", Alert.AlertType.INFORMATION);
            clearInputs();
            clearErrorMessages();
        }
    }

    private boolean validateInputs(String email, String password) {
        String emailValidationMessage = ValidationUtils.validateEmail(email);
        String passwordValidationMessage = ValidationUtils.validatePassword(password);

        if (emailValidationMessage != null) {
            emailErrorLabel.setText(emailValidationMessage);
        }

        if (password.isEmpty()) {
            passwordValidationMessage = null;
        }
        else if (passwordValidationMessage != null) {
            passwordErrorLabel.setText(passwordValidationMessage);
        }

        return emailValidationMessage == null && passwordValidationMessage == null;
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