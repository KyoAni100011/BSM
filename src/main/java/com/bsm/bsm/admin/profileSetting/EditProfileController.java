package com.bsm.bsm.admin.profileSetting;

import com.bsm.bsm.user.UserController;
import com.bsm.bsm.user.UserModel;
import com.bsm.bsm.user.UserService;
import com.bsm.bsm.user.UserSingleton;
import com.bsm.bsm.utils.AlertUtils;
import com.bsm.bsm.utils.DateUtils;
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

import static com.bsm.bsm.utils.DateUtils.convertDOBFormat;

public class EditProfileController {
    @FXML
    private Label fullNameErrorLabel, dobErrorLabel, phoneErrorLabel, addressErrorLabel;

    @FXML
    private TextField fullNameField, emailTextField, phoneTextField, addressTextField;

    @FXML
    private DatePicker dobPicker;

    @FXML
    public Button saveChangesButton;

    private final UserModel adminInfo = UserSingleton.getInstance().getUser();

    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private UserController userController;

    public EditProfileController() {
        userController = new UserService();
    }

    @FXML
    public void initialize() {
        new EditProfileController();
        setupDatePicker();
        setUserInfo();
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

    private void updateUserInformation(String fullName, String telephone, String dob, String address) throws ParseException {
        UserModel user = UserSingleton.getInstance().getUser();
        String formattedDOB = DateUtils.formatDOB(dob);
        user.setName(fullName);
        user.setPhone(telephone);
        user.setAddress(address);
        user.setDob(formattedDOB);
    }

    private void setUserInfo() {
        fullNameField.setText(adminInfo.getName());
        emailTextField.setText(adminInfo.getEmail());
        phoneTextField.setText(adminInfo.getPhone());
        addressTextField.setText(adminInfo.getAddress());

        String dob = convertDOBFormat(adminInfo.getDob());
        dobPicker.setValue(LocalDate.parse(dob, DateTimeFormatter.ofPattern("dd/MM/yyyy")));
    }


    @FXML
    private void handleSaveChanges(ActionEvent event) throws Exception {
        clearErrorMessages();

        String fullName = fullNameField.getText();
        String dob = dobPicker.getEditor().getText();
        String phone = phoneTextField.getText();
        String address = addressTextField.getText();
        String id = UserSingleton.getInstance().getUser().getId();

        if (validateInputs(fullName, dob, phone, address)) {
            if (userController.editProfile(id, fullName, phone, dob, address)) {
                AlertUtils.showAlert("Success", "Profile updated successfully.", Alert.AlertType.INFORMATION);
                clearInputs();
                updateUserInformation(fullName, phone, dob, address);
                setUserInfo();
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
        setupDatePicker();
        phoneTextField.clear();
        addressTextField.clear();
    }
}
