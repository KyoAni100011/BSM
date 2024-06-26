package com.bsm.bsm.admin.userAccount;


import com.bsm.bsm.account.AccountService;
import com.bsm.bsm.user.UserSingleton;
import com.bsm.bsm.utils.AlertUtils;
import com.bsm.bsm.utils.ValidationUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class PasswordResetController {
    private static String userEmail; // Variable to store the selected user
    private final AccountService accountService = new AccountService();
    @FXML
    public Label textNote;
    @FXML
    private TextField emailField, customPassword;
    @FXML
    private Button resetButton;
    @FXML
    private Label emailErrorLabel, passwordErrorLabel;

    static void handleTableItemSelection(String email) {
        userEmail = email; // Store the selected user
    }

    @FXML
    public void initialize() {
        emailField.setText(userEmail);
        clearErrorMessages();
        textNote.setVisible(true);
        customPassword.setOnMouseClicked(event -> textNote.setVisible(false)); // Add event handler to hide textNote when customPassword is clicked
    }

    @FXML
    private void handleResetButtonAction(ActionEvent event) {
        clearErrorMessages();
        String userEmail = emailField.getText();
        String password = customPassword.getText();

        if (validateInputs(userEmail, password)) {
            //check user not admin by email
            String adminEmail = UserSingleton.getInstance().getUser().getEmail();
            if (adminEmail.equals(userEmail)) {
                emailErrorLabel.setText("Admin cannot reset password for themselves.");
                return;
            }

            if (!accountService.hasUserExist(userEmail)) {
                emailErrorLabel.setText("User does not exist.");
                return;
            }

            String adminID = UserSingleton.getInstance().getUser().getId();
            accountService.updatePassword(adminID, userEmail, password);
            AlertUtils.showAlert("Success", "Reset password successfully.", Alert.AlertType.INFORMATION);
            clearInputs();
            clearErrorMessages();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            // Close the stage
            stage.close();
        }
    }

    private boolean validateInputs(String email, String password) {
        String emailValidationMessage = ValidationUtils.validateEmail(email);
        String passwordValidationMessage = ValidationUtils.validatePassword(password, "user");

        if (emailValidationMessage != null) {
            emailErrorLabel.setText(emailValidationMessage);
        }

        if (password.isEmpty()) {
            passwordValidationMessage = null;
        } else if (passwordValidationMessage != null) {
            textNote.setVisible(false);
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