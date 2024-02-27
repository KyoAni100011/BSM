package main.java;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;

public class PasswordResetController {

    @FXML
    private TextField emailField;
    @FXML
    private TextField customPassword;
    @FXML
    private Button resetButton;
    @FXML
    private Label emailErrorLabel;
    @FXML
    private Label passwordErrorLabel;

    @FXML
    public void initialize() {
        clearErrorMessages();
    }

    @FXML
    private void handleResetButtonAction(ActionEvent event) {
        clearErrorMessages();
        boolean isEmailValid = validateEmail(emailField.getText());
        boolean isPasswordValid = validatePassword(customPassword.getText()).equals("Valid");

        if (isEmailValid && isPasswordValid) {
            // Logic to reset the password here
            System.out.println("Password reset successful for " + emailField.getText().trim());

            // Show success alert
            showAlert("Success", "Password reset successful!", Alert.AlertType.INFORMATION);

            // Reset error messages
            clearErrorMessages();
        }
    }

    private void clearErrorMessages() {
        emailErrorLabel.setText("");
        passwordErrorLabel.setText("");
    }

    private boolean validateEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        if (email.isEmpty()) {
            emailErrorLabel.setText("Please enter your email.");
            return false;
        } else if (!email.matches(emailRegex)) {
            emailErrorLabel.setText("Invalid email format.");
            return false;
        }
        return true;
    }

    private String validatePassword(String password) {
        if (password.isEmpty()) {
            return "Valid";
        }
        if (password.length() < 8 || password.length() > 255) {
            passwordErrorLabel.setText("Your password should be between 8 and 255 characters.");
            return "Invalid";
        }
        if (!password.matches(".*[A-Z].*")) {
            passwordErrorLabel.setText("Your password should include at least one uppercase letter (A-Z).");
            return "Invalid";
        }
        if (!password.matches(".*[a-z].*")) {
            passwordErrorLabel.setText("Your password should include at least one lowercase letter (a-z).");
            return "Invalid";
        }
        if (!password.matches(".*[0-9].*")) {
            passwordErrorLabel.setText("Your password should include at least one number (0-9).");
            return "Invalid";
        }
        if (!password.matches(".*[!@#$%&*()_+=|<>?{}\\[\\]~-].*")) {
            passwordErrorLabel.setText("Your password should include at least one special character.");
            return "Invalid";
        }
        return "Valid";
    }

    private void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
