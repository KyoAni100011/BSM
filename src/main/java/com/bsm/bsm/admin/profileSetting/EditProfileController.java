package com.bsm.bsm.admin.profileSetting;

import com.bsm.bsm.user.UserModel;
import com.bsm.bsm.user.UserSingleton;
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

public class EditProfileController {
    public UserModel adminInfo = UserSingleton.getInstance().getUser();
    @FXML
    public Button saveChangesButton;
    @FXML
    private Label fullNameErrorLabel, emailErrorLabel, dobErrorLabel, phoneErrorLabel, addressErrorLabel;
    @FXML
    private TextField fullNameField, emailTextField, phoneTextField, addressTextField;
    @FXML
    private DatePicker dobPicker;

    private final EditProfileService editProfileService = new EditProfileService();

    @FXML
    public void initialize() {
        fullNameField.setText(adminInfo.getName());
        emailTextField.setText(adminInfo.getEmail());
        phoneErrorLabel.setText(adminInfo.getPhone());

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
        String phone = phoneTextField.getText();
        String address = addressTextField.getText();

        if (validateInputs(fullName, email, dob, phone, address)) {
            // need to call ID
            String id = "11115678";
            if (editProfileService.updateUserProfile(id, fullName, phone, dob, address)){
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

        if (emailError != null) {
            emailErrorLabel.setText(emailError);
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
        emailErrorLabel.setText("");
        fullNameErrorLabel.setText("");
        dobErrorLabel.setText("");
        phoneErrorLabel.setText("");
        addressErrorLabel.setText("");
    }

    private void clearInputs() {
        fullNameField.clear();
        emailTextField.clear();
        dobPicker.getEditor().clear();
        phoneTextField.clear();
        addressTextField.clear();
    }
}