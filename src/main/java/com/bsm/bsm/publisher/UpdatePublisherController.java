package com.bsm.bsm.publisher;

import com.bsm.bsm.publisher.Publisher;
import com.bsm.bsm.publisher.UpdatePublisherService;
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
    private final UpdatePublisherService updatePublisherService = new UpdatePublisherService();

    @FXML
    void initialize() {
        Publisher publisher = updatePublisherService.getPublisher("44441111");
        setPublisherInfo(publisher);
    }
    private void setPublisherInfo(Publisher publisher) {
        fullNameField.setText(publisher.getName());
        addressTextField.setText(publisher.getAddress());
    }
    @FXML
    private void handleSaveChanges(ActionEvent event) throws ParseException {
        String id = "44441111";
        clearErrorMessages();
        String fullName = fullNameField.getText();
        String address = addressTextField.getText();
        if (validateInputs(fullName,address)) {
            updatePublisherService.updatePublisher(id, fullName, address);
            AlertUtils.showAlert("Success", "Publisher updated successfully.", Alert.AlertType.INFORMATION);
            clearInputs();
            var publisher = updatePublisherService.getPublisher(fullName);
            setPublisherInfo(publisher);
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
