package com.bsm.bsm.admin.userAccount;

import com.bsm.bsm.user.UserModel;
import com.bsm.bsm.utils.AlertUtils;
import com.bsm.bsm.utils.NumericValidationUtils;
import com.bsm.bsm.utils.ValidationUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.bsm.bsm.utils.DateUtils.convertDOBFormat;

public class UserDetailController {
    @FXML
    public Button isEnabledLabel;
    @FXML

    private TextField fullNameField, phoneTextField, addressTextField, lastLoginField,emailField;
    @FXML
    private DatePicker dobPicker;
    @FXML
    private static UserModel selectedUser; // Variable to store the selected user

    @FXML
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final UpdateUserService updateUserService = new UpdateUserService();

    @FXML
    public void initialize() {
        // Set the prompt text for the DatePicker
        setupDatePicker();
        setUserInfo();
    }
    static  void handleTableItemSelection(UserModel user) {
        selectedUser = user; // Store the selected user
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
        fullNameField.setText(selectedUser.getName());
        phoneTextField.setText(selectedUser.getPhone());
        addressTextField.setText(selectedUser.getAddress());
        String dob = convertDOBFormat(selectedUser.getDob());
        dobPicker.setValue(LocalDate.parse(dob, DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        lastLoginField.setText(selectedUser.getLastLogin());
        emailField.setText(selectedUser.getEmail());
        isEnabledLabel.setText(selectedUser.isEnabled() ? "Enable" : "Disable");
        if(selectedUser.isEnabled()){
            isEnabledLabel.getStyleClass().add("enable-button");
        }else {
            isEnabledLabel.getStyleClass().add("disable-button");
        }
    }

}
