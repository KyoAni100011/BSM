package com.bsm.bsm.publisher;

import com.bsm.bsm.utils.AlertUtils;
import com.bsm.bsm.utils.ValidationUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.text.ParseException;

public class AddPublishersController {
    @FXML
    private Label fullNameErrorLabel, addressErrorLabel;
    @FXML
    private TextField fullNameField,addressTextField;
    @FXML
    public void initialize() {
    }
    @FXML
    private void handleSaveChanges(ActionEvent event) throws ParseException {
        clearErrorMessages();
        String fullName = fullNameField.getText();
        String address = addressTextField.getText();
        if (validateInputs(fullName,address)) {
            clearInputs();
            AlertUtils.showAlert("Success", "Add publisher successfully.", Alert.AlertType.INFORMATION);
        }
    }
    private boolean validateInputs(String fullName, String address ) {
        String fullNameError = ValidationUtils.validateFullName(fullName);
        String addressError = ValidationUtils.validateIntroduction(address);

        if (fullNameErrorLabel != null) {
            fullNameErrorLabel.setText(fullNameError);
        }
        if (addressErrorLabel != null) {
            addressErrorLabel.setText(addressError);
        }
        return fullNameErrorLabel == null ;
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
