package com.bsm.bsm.employee.book;

import com.bsm.bsm.utils.NumericValidationUtils;
import com.bsm.bsm.utils.ValidationUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.util.StringConverter;
import org.controlsfx.control.CheckComboBox;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class UpdateBookController {
    @FXML
    private DatePicker releaseDatePicker;

    @FXML
    private CheckComboBox<String> checkComboBox;
    @FXML
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @FXML
    public void initialize() {
        ObservableList<String> items = FXCollections.observableArrayList(
                "Item 1", "Item 2", "Item 3", "Item 4", "Item 5"
        );

        // Add the items to the CheckComboBox
        checkComboBox.getItems().addAll(items);
    }
    private void setupDatePicker() {
        releaseDatePicker.setPromptText("dd/mm/yyyy");

        releaseDatePicker.setConverter(new StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate date) {
                return date != null ? dateFormatter.format(date) : "";
            }

            @Override
            public LocalDate fromString(String string) {
                return string != null && !string.isEmpty() ? LocalDate.parse(string, dateFormatter) : null;
            }
        });

        releaseDatePicker.getEditor().addEventFilter(KeyEvent.KEY_TYPED, NumericValidationUtils.numericValidation(10));
    }

    private boolean validateInputs(String fullName, String dob, String phone, String address) {
//        String fullNameError = ValidationUtils.validateFullName(fullName, "user");
//        String dobError = ValidationUtils.validateDOB(dob,"user");
//        String phoneError = ValidationUtils.validatePhone(phone,"user");
//        String addressError = ValidationUtils.validateAddress(address,"user");
//
//        if (fullNameError != null) {
//            fullNameErrorLabel.setText(fullNameError);
//        }
//
//
//        if (dobError != null) {
//            dobErrorLabel.setText(dobError);
//        }
//
//        if (phoneError != null) {
//            phoneErrorLabel.setText(phoneError);
//        }
//
//        if (addressError != null) {
//            addressErrorLabel.setText(addressError);
//        }
//
//        return fullNameError == null && dobError == null && phoneError == null && addressError == null;
        return false;
    }
    @FXML
    private void handleSaveChanges(ActionEvent event) {
    }

    private boolean validateInputs(String isbn, String title, String publisherId, String authorId, String publishingDate, String languages, String isEnabled, String quantity, String salePrice) {
        return true;
    }

    private void clearErrorMessages() {

    }

    private void clearInputs() {

    }
}
