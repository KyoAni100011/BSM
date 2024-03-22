package com.bsm.bsm.publisher;

import com.bsm.bsm.utils.AlertUtils;
import com.bsm.bsm.utils.ValidationUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.text.ParseException;

public class UpdatePublisherController {
    @FXML
    Label fullNameErrorLabel, addressErrorLabel;
    @FXML
    TextField fullNameField, addressTextField;
    @FXML
    void initialize() {

    }

    @FXML
    private void handleSaveChanges(ActionEvent event) throws ParseException {
        clearErrorMessages();
        String fullName = fullNameField.getText();
        String address = addressTextField.getText();
        if (validateInputs(fullName, address)) {
            AlertUtils.showAlert("Success", "Profile updated successfully.", Alert.AlertType.INFORMATION);
        }
    }

    private boolean validateInputs(String fullName, String address) {
        String fullNameError = ValidationUtils.validateFullName(fullName, "publisher");
        String addresError = ValidationUtils.validateAddress(address, "publisher");

        if (fullNameError != null) {
            fullNameErrorLabel.setText(fullNameError);
        }

        if (addresError != null) {
            addressErrorLabel.setText(addresError);
        }

        return fullNameError == null && addresError == null;
    }

    private void clearErrorMessages() {
        fullNameErrorLabel.setText("");
        addressErrorLabel.setText("");
    }
}
