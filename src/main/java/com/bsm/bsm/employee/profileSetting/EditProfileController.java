package com.bsm.bsm.employee.profileSetting;

import com.bsm.bsm.utils.AlertUtils;
import com.bsm.bsm.utils.NumericValidationUtils;
import com.bsm.bsm.utils.ValidationUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.util.StringConverter;
import javafx.scene.input.KeyEvent;
import javafx.event.EventHandler;

public class EditProfileController {
    @FXML
    public Button saveChangesButton;
    @FXML
    private Label fullNameErrorLabel, emailErrorLabel, dobErrorLabel;
    @FXML
    private TextField fullNameField, emailTextField;
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

        // Add event filter to allow only numbers to be entered in the dobPicker
        dobPicker.getEditor().addEventFilter(KeyEvent.KEY_TYPED, NumericValidationUtils.numericValidation(10));
    }

    @FXML
    private void handleSaveChanges(ActionEvent event) {
        clearErrorMessages();
        String fullName = fullNameField.getText();
        String email = emailTextField.getText();
        String dob = dobPicker.getEditor().getText();

        if (validateInputs(fullName, email, dob)) {
            AlertUtils.showAlert("Success", "Profile updated successfully.", Alert.AlertType.INFORMATION);
            clearInputs();
        }
    }

    private boolean validateInputs(String fullName, String email, String dob) {
        String fullNameError = ValidationUtils.validateFullName(fullName);
        String emailError = ValidationUtils.validateEmail(email);
        String dobError = ValidationUtils.validateDOB(dob);

        if (fullNameError != null) {
            fullNameErrorLabel.setText(fullNameError);
        }

        if (emailError != null) {
            emailErrorLabel.setText(emailError);
        }

        if (dobError != null) {
            dobErrorLabel.setText(dobError);
        }

        return fullNameError == null && emailError == null && dobError == null;
    }

    private void clearErrorMessages() {
        emailErrorLabel.setText("");
        fullNameErrorLabel.setText("");
        dobErrorLabel.setText("");
    }

    private void clearInputs() {
        fullNameField.clear();
        emailTextField.clear();
        dobPicker.getEditor().clear();
    }

}
