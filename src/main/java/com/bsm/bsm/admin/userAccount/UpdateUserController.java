package com.bsm.bsm.admin.userAccount;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.util.StringConverter;
import javafx.scene.input.KeyEvent;
import javafx.event.EventHandler;

import com.bsm.bsm.utils.ValidationUtils;
import com.bsm.bsm.utils.AlertUtils;
import com.bsm.bsm.utils.NumericValidationUtils;

public class UpdateUserController {
    @FXML
    public Button saveChangesButton;
    @FXML
    private Label fullNameErrorLabel, dobErrorLabel, phoneErrorLabel, addressErrorLabel;
    @FXML
    private TextField fullNameField, phoneTextField, addressTextField;
    @FXML
    private DatePicker dobPicker;

    private final UpdateUserService updateUserService = new UpdateUserService();

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
        String dob = dobPicker.getEditor().getText();
        String phone = phoneTextField.getText();
        String address = addressTextField.getText();

        if (validateInputs(fullName, dob, phone, address)) {
            // need to call ID
            String id = "11115678";
            if (updateUserService.updateUserProfile(id, fullName, phone, dob, address)){
                AlertUtils.showAlert("Success", "Profile updated successfully.", Alert.AlertType.INFORMATION);

                clearInputs();
            } else {
                AlertUtils.showAlert("Error", "Profile update failed.", Alert.AlertType.ERROR);
            }
        }
    }

    private boolean validateInputs(String fullName, String dob, String phone, String address) {
        String fullNameError = ValidationUtils.validateFullName(fullName);
        String dobError = ValidationUtils.validateDOB(dob);
        String phoneError = ValidationUtils.validatePhone(phone);
        String addressError = ValidationUtils.validateAddress(address);

        if (fullNameError != null) {
            fullNameErrorLabel.setText(fullNameError);
        }


        if (dobError != null) {
            dobErrorLabel.setText(dobError);
        }

        if (phoneError != null) {
            phoneErrorLabel.setText(phoneError);
        }

        if (addressError != null) {
            addressErrorLabel.setText(addressError);
        }

        return fullNameError == null && dobError == null && phoneError == null && addressError == null;
    }

    private void clearErrorMessages() {
        fullNameErrorLabel.setText("");
        dobErrorLabel.setText("");
        phoneErrorLabel.setText("");
        addressErrorLabel.setText("");
    }

    private void clearInputs() {
        fullNameField.clear();
        dobPicker.getEditor().clear();
        phoneTextField.clear();
        addressTextField.clear();
    }
}