package com.bsm.bsm.admin.profileSetting;

import com.bsm.bsm.user.UserModel;
import com.bsm.bsm.user.UserSingleton;
import com.bsm.bsm.utils.AlertUtils;
import com.bsm.bsm.utils.NumericValidationUtils;
import com.bsm.bsm.utils.ValidationUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.util.StringConverter;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class EditProfileController {

    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private final EditProfileService editProfileService = new EditProfileService();
    @FXML
    public Button saveChangesButton;
    private final UserModel adminInfo = UserSingleton.getInstance().getUser();
    @FXML
    private Label fullNameErrorLabel, emailErrorLabel, dobErrorLabel, phoneErrorLabel, addressErrorLabel;
    @FXML
    private TextField fullNameField, emailTextField, phoneTextField, addressTextField;
    @FXML
    private DatePicker dobPicker;

    @FXML
    public void initialize() {
        // Set the prompt text for the DatePicker
        dobPicker.setPromptText("dd/mm/yyyy");

        // Set the date format for the DatePicker
        dobPicker.setConverter(new StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate date) {
                return date != null ? dateFormatter.format(date) : "";
            }

            @Override
            public LocalDate fromString(String string) {
                return string != null && !string.isEmpty() ? LocalDate.parse(string, dateFormatter) : null;
            }
        });

        // Add event filter to allow only numbers to be entered in the dobPicker with a delay of 500 milliseconds
        dobPicker.getEditor().addEventFilter(KeyEvent.KEY_TYPED, NumericValidationUtils.numericValidation(10));

        // Set initial values for other fields
        fullNameField.setText(adminInfo.getName());
        emailTextField.setText(adminInfo.getEmail());
        phoneTextField.setText(adminInfo.getPhone());
        addressTextField.setText(adminInfo.getAddress());

        // Set the initial value for the DatePicker using the admin's DOB
        try {
            LocalDate adminDOB = LocalDate.parse(adminInfo.getDob(), dateFormatter);
            dobPicker.setValue(adminDOB);
        } catch (DateTimeParseException e) {
            // Handle the exception if parsing the admin's DOB fails
            e.printStackTrace(); // Replace with appropriate error handling
        }
    }


    @FXML
    private void handleSaveChanges(ActionEvent event) throws ParseException {
        clearErrorMessages();
        String fullName = fullNameField.getText();
        String email = emailTextField.getText();
        String dob = dobPicker.getEditor().getText();
        String phone = phoneTextField.getText();
        String address = addressTextField.getText();

        if (validateInputs(fullName, email, dob, phone, address)) {
            String id = UserSingleton.getInstance().getUser().getId();
            if (editProfileService.updateUserProfile(id, fullName, phone, dob, address)) {
                AlertUtils.showAlert("Success", "Profile updated successfully.", Alert.AlertType.INFORMATION);
                clearInputs();
            } else {
                AlertUtils.showAlert("Error", "Profile update failed.", Alert.AlertType.ERROR);
            }
        }
    }

    private boolean validateInputs(String fullName, String email, String dob, String phone, String address) {
        String fullNameError = ValidationUtils.validateFullName(fullName);
        String emailError = ValidationUtils.validateEmail(email);
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

        return fullNameError == null && emailError == null && dobError == null && phoneError == null && addressError == null;
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
