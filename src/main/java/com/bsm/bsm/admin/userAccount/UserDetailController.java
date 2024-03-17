package com.bsm.bsm.admin.userAccount;

import com.bsm.bsm.auth.AuthService;
import com.bsm.bsm.user.UserController;
import com.bsm.bsm.user.UserModel;
import com.bsm.bsm.user.UserService;
import com.bsm.bsm.utils.AlertUtils;
import com.bsm.bsm.utils.NumericValidationUtils;
import com.bsm.bsm.utils.ValidationUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.util.StringConverter;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
    private static String email; // Variable to store the selected user

    @FXML
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private UserModel userInfoDetail = null;

    private AuthService userDetailController = null;

    public UserDetailController()
    {
        userDetailController = new AuthService();
    }

    @FXML
    public void initialize() {
        new UserDetailController();
        userInfoDetail = userDetailController.getUserByEmail(email);
        // Set the prompt text for the DatePicker
        setupDatePicker();
        setUserInfo();
    }
    static  void handleTableItemSelection(String userEmail) {
        email = userEmail;
        System.out.println("userEmail = " + userEmail);// Store the selected user
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
        fullNameField.setText(userInfoDetail.getName());
        phoneTextField.setText(userInfoDetail.getPhone());
        addressTextField.setText(userInfoDetail.getAddress());
        String dob = convertDOBFormat(userInfoDetail.getDob());
        dobPicker.setValue(LocalDate.parse(dob, DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        emailField.setText(userInfoDetail.getEmail());
        isEnabledLabel.setText(userInfoDetail.isEnabled() ? "Enable" : "Disable");
        if(userInfoDetail.isEnabled()){
            isEnabledLabel.getStyleClass().add("enable-button");
        }else {
            isEnabledLabel.getStyleClass().add("disable-button");
        }
        lastLoginField.setText(userInfoDetail.getLastLogin());

    }

}
