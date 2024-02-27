package main.java.admin.userAccount;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.util.StringConverter;

public class EditProfileController {

    @FXML
    private Label fullNameErrorLabel;
    @FXML
    private Label emailErrorLabel;
    @FXML
    private Label dobErrorLabel;
    @FXML
    private TextField fullNameField;
    @FXML
    private TextField emailTextField;
    @FXML
    private DatePicker dobPicker;

    @FXML
    public void initialize() {
        // Set the prompt text for the DatePicker
        dobPicker.setPromptText("dd/mm/yyyy");

        // Set the date format for the DatePicker
        dobPicker.setConverter(new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        });
    }

    @FXML
    private void handleSaveChanges(ActionEvent event) {
        clearErrorMessages();
        String fullName = fullNameField.getText();
        String email = emailTextField.getText();
        String dob = dobPicker.getEditor().getText();

        if (validateFullName(fullName) & validateEmail(email) & validateDOB(dob)) {
            showAlert("Success", "Profile updated successfully.", Alert.AlertType.INFORMATION);
        }
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

    private boolean validateFullName(String fullName) {
        if (fullName.isEmpty()) {
            fullNameErrorLabel.setText("Please enter your full name.");
            System.out.println("Full name is empty");
            return false;
        }
        return true;
    }

    private boolean validateDOB(String dob) {
        String dobRegex = "^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/\\d{4}$";
        if (dob.isEmpty()) {
            dobErrorLabel.setText("Please enter your date of birth.");
            return false;
        }
        if (!dob.matches(dobRegex)) {
            dobErrorLabel.setText("Invalid date format. Please use dd/mm/yyyy.");
            return false;
        }

        return true;
    }

    private void clearErrorMessages() {
        emailErrorLabel.setText("");
        fullNameErrorLabel.setText("");
        dobErrorLabel.setText("");
    }

    private void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
