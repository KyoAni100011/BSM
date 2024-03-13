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

    private final UpdateUserService updateUserService = new UpdateUserService();

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
//
//        fullNameField.setText(email.getName());
//        phoneTextField.setText(email.getPhone());
//        addressTextField.setText(email.getAddress());
//        String dob = convertDOBFormat(email.getDob());
//        dobPicker.setValue(LocalDate.parse(dob, DateTimeFormatter.ofPattern("dd/MM/yyyy")));
//        emailField.setText(email.getEmail());
//        isEnabledLabel.setText(email.isEnabled() ? "Enable" : "Disable");
//        if(email.isEnabled()){
//            isEnabledLabel.getStyleClass().add("enable-button");
//        }else {
//            isEnabledLabel.getStyleClass().add("disable-button");
//        }
//        LocalDateTime lastLoginDateTime = LocalDateTime.parse(email.getLastLogin(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
//        LocalDateTime now = LocalDateTime.now();
//        Duration duration = Duration.between(lastLoginDateTime, now);
//        LocalDate lastLoginDate = lastLoginDateTime.toLocalDate();
//        long days = duration.toDays();
//        long hours = duration.toHours() % 24;
//        long minutes = duration.toMinutes() % 60;
//        // Construct the string representing the time elapsed since last login
//        String timeElapsed = "";
//        if (days > 0) {
//            timeElapsed += lastLoginDate;
//        }else {
//            if (hours > 0) {
//                timeElapsed += hours + "h" + " ago";
//            }else {
//                if (minutes > 0) {
//                    timeElapsed += minutes + "m" + " ago";
//                }
//            }
//        }
//        lastLoginField.setText(timeElapsed);

    }

}
