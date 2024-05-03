package com.bsm.bsm.admin.userAccount;

import com.bsm.bsm.auth.AuthService;
import com.bsm.bsm.user.UserModel;
import com.bsm.bsm.utils.NumericValidationUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.bsm.bsm.utils.DateUtils.convertDOBFormat;

public class UserDetailController {
    @FXML
    private static String email; // Variable to store the selected user
    @FXML
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    @FXML
    public Button isEnabledLabel;
    @FXML

    private TextField fullNameField, phoneTextField, addressTextField, lastLoginField, emailField, idField;
    @FXML
    private DatePicker dobPicker;
    private UserModel userInfoDetail = null;

    private AuthService userDetailController = null;

    public UserDetailController() {
        userDetailController = new AuthService();
    }

    static void handleTableItemSelection(String userEmail) {
        email = userEmail;
    }

    @FXML
    public void initialize() {
        new UserDetailController();
        dobPicker.getEditor().setOpacity(1);
        userInfoDetail = userDetailController.getUserByEmail(email);
        // Set the prompt text for the DatePicker
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

    private void setUserInfo() {
        idField.setText(userInfoDetail.getId());
        fullNameField.setText(userInfoDetail.getName());
        phoneTextField.setText(userInfoDetail.getPhone());
        addressTextField.setText(userInfoDetail.getAddress());
        String dob = convertDOBFormat(userInfoDetail.getDob());
        dobPicker.setValue(LocalDate.parse(dob, DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        emailField.setText(userInfoDetail.getEmail());
        isEnabledLabel.setText(userInfoDetail.isEnabled() ? "Enable" : "Disable");
        if (userInfoDetail.isEnabled()) {
            isEnabledLabel.getStyleClass().add("enable-button");
        } else {
            isEnabledLabel.getStyleClass().add("disable-button");
        }
        lastLoginField.setText(userInfoDetail.getLastLogin());

    }

}
