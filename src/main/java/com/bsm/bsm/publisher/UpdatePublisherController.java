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
    private final PublisherService publisherService = new PublisherService();

    //set temp id
    String id = "44441111";

    @FXML
    void initialize() {
        Publisher publisher = publisherService.getPublisher(id);
        setPublisherInfo(publisher);
    }
    private void setPublisherInfo(Publisher publisher) {
        fullNameField.setText(publisher.getName());
        addressTextField.setText(publisher.getAddress());
    }

    @FXML
    private void handleSaveChanges(ActionEvent event) throws ParseException {
        clearErrorMessages();
        String fullName = fullNameField.getText();
        String address = addressTextField.getText();
        if (validateInputs(fullName,address)) {
            // check if publisher is disabled
            boolean isEnabled = publisherService.isEnabled(id);
            if (!isEnabled) {
                AlertUtils.showAlert("Error", "Publisher is disabled. Please enable it first.", Alert.AlertType.ERROR);
                return;
            }

            Publisher newPublisher = new Publisher(id, fullName, address, isEnabled);
            publisherService.update(newPublisher);
            AlertUtils.showAlert("Success", "Publisher updated successfully.", Alert.AlertType.INFORMATION);
        }
    }

    private boolean validateInputs(String fullName, String address) {
        String fullNameError = ValidationUtils.validateFullName(fullName, "publisher");
        String addressError = ValidationUtils.validateAddress(address, "publisher");

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
