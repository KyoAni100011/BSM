package com.bsm.bsm.admin.userAccount;

import com.bsm.bsm.account.AccountService;
import com.bsm.bsm.user.UserModel;
import com.bsm.bsm.user.UserSingleton;
import com.bsm.bsm.utils.DateUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.scene.input.KeyEvent;
import javafx.event.EventHandler;

import com.bsm.bsm.utils.ValidationUtils;
import com.bsm.bsm.utils.AlertUtils;
import com.bsm.bsm.utils.NumericValidationUtils;

import static com.bsm.bsm.utils.DateUtils.convertDOBFormat;

public class UpdateUserController {
    @FXML
    public Button saveChangesButton;
    @FXML
    private Label fullNameErrorLabel, dobErrorLabel, phoneErrorLabel, addressErrorLabel;
    @FXML
    private TextField fullNameField, phoneTextField, addressTextField;
    @FXML
    private DatePicker dobPicker;
    @FXML
    private static String email; // Variable to store the selected user
    @FXML
    private UserModel userModel;
    @FXML
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final AccountService accountService = new AccountService();

    @FXML
    public void initialize() {
        // Set the prompt text for the DatePicker
        setupDatePicker();
        setUserInfo();
    }
    static  void handleTableItemSelection(String userEmail) {
        email = userEmail; // Store the selected user
    }

    private void setupDatePicker() {
        dobPicker.setPromptText("dd/mm/yyyy");

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

        dobPicker.getEditor().addEventFilter(KeyEvent.KEY_TYPED, NumericValidationUtils.numericValidation(10));
    }
    private void setUserInfo() {
        userModel = accountService.getUserProfile(email);
        fullNameField.setText(userModel.getName());
        phoneTextField.setText(userModel.getPhone());
        addressTextField.setText(userModel.getAddress());
        String dob = convertDOBFormat(userModel.getDob());
        dobPicker.setValue(LocalDate.parse(dob, DateTimeFormatter.ofPattern("dd/MM/yyyy")));
    }
    private void updateUserInformation(String fullName, String telephone, String dob, String address) throws ParseException {
        String formattedDOB = DateUtils.formatDOB(dob);
        userModel.setName(fullName);
        userModel.setPhone(telephone);
        userModel.setAddress(address);
        userModel.setDob(formattedDOB);
    }
    @FXML
    private void handleSaveChanges(ActionEvent event) throws ParseException {
        clearErrorMessages();
        String fullName = fullNameField.getText();
        String dob = dobPicker.getEditor().getText();
        String phone = phoneTextField.getText();
        String address = addressTextField.getText();

        if (validateInputs(fullName, dob, phone, address)) {
            // need to call ID
            String id = userModel.getId();
            if (accountService.updateUserProfile(id, fullName, phone, dob, address)){
                AlertUtils.showAlert("Success", "Profile updated successfully.", Alert.AlertType.INFORMATION);
                updateUserInformation(fullName, phone, dob, address);
                clearInputs();
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                // Close the stage
                stage.close();
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
        setupDatePicker();
    }

}