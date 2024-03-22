package com.bsm.bsm.publisher;

import com.bsm.bsm.utils.AlertUtils;
import com.bsm.bsm.utils.ValidationUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.text.ParseException;

public class AddPublisherController {
    @FXML
    private Label fullNameErrorLabel, addressErrorLabel;
    @FXML
    private TextField fullNameField,addressTextField;

    private AddPublisherService addPublisherService = new AddPublisherService();
    @FXML
    public void initialize() {
    }
    @FXML
    private void handleSaveChanges(ActionEvent event) throws ParseException {
        clearErrorMessages();
        String fullName = fullNameField.getText();
        String address = addressTextField.getText();

        if (validateInputs(fullName,address)) {
            if (addPublisherService.checkPublisherExists(fullName)) {
                fullNameErrorLabel.setText("Publisher already exists.");
            } else {
                if (addPublisherService.addPublisher(fullName, address)) {
                    AlertUtils.showAlert("Success", "Add publisher successfully.", Alert.AlertType.INFORMATION);
                    clearInputs();
                } else {
                    AlertUtils.showAlert("Error", "An error occurred while adding the publisher.", Alert.AlertType.ERROR);
                }
            }
        }
    }
    private boolean validateInputs(String fullName, String address) {
        String fullNameError = ValidationUtils.validateFullName(fullName,"publisher");
        String addressError = ValidationUtils.validateIntroduction(address,"publisher");

        if (fullNameError != null) {
            fullNameErrorLabel.setText(fullNameError);
        }
        if (addressError != null) {
            addressErrorLabel.setText(addressError);
        }
        return fullNameError == null && addressError == null;
    }

    private void clearErrorMessages() {
        fullNameErrorLabel.setText("");
        addressErrorLabel.setText("");

    }

    private void clearInputs() {
        fullNameField.clear();
        addressTextField.clear();
    }
}
