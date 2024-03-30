package com.bsm.bsm.employee.bookPublishers;

import com.bsm.bsm.category.Category;
import com.bsm.bsm.publisher.Publisher;
import com.bsm.bsm.publisher.PublisherService;
import com.bsm.bsm.utils.AlertUtils;
import com.bsm.bsm.utils.ValidationUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.text.ParseException;

public class AddPublisherController {
    @FXML
    private Label fullNameErrorLabel, addressErrorLabel;

    @FXML
    private TextField fullNameField;

    @FXML
    private TextArea addressTextField;

    private final PublisherService publisherService = new PublisherService();

    @FXML
    public void initialize() {
    }
    @FXML
    private void handleSaveChanges(ActionEvent event) throws ParseException {
        clearErrorMessages();
        String fullName = fullNameField.getText();
        String address = addressTextField.getText();

        if (validateInputs(fullName,address)) {
            if (publisherService.checkPublisherExists(fullName)) {
                fullNameErrorLabel.setText("Publisher already exists.");
            } else {
                Publisher newPublisher = new Publisher(fullName, address);

                if (publisherService.add(newPublisher)) {
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
