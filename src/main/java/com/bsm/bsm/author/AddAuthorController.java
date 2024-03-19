package com.bsm.bsm.author;

import com.bsm.bsm.utils.AlertUtils;
import com.bsm.bsm.utils.ValidationUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.text.ParseException;

public class AddAuthorController {
    @FXML
    private Label fullNameErrorLabel, introductionErrorLabel;
    @FXML
    private TextField fullNameField,introductionTextField;

    private final AddAuthorService addAuthorService = new AddAuthorService();

    @FXML
    public void initialize() {
    }
    @FXML
    private void handleSaveChanges(ActionEvent event) throws ParseException {
        clearErrorMessages();
        String fullName = fullNameField.getText();
        String introduction = introductionTextField.getText();
        System.out.println("Full Name: " + fullName);
        System.out.println("Introduction: " + introduction);
//        if (validateInputs(fullName,introduction)) {
            if (addAuthorService.checkAuthorExists(fullName)) {
                fullNameErrorLabel.setText("Author already exists.");
            } else {
                if (addAuthorService.addAuthor(fullName, introduction)) {
                    AlertUtils.showAlert("Success", "Profile updated successfully.", Alert.AlertType.INFORMATION);
                    clearInputs();
                } else {
                    AlertUtils.showAlert("Error", "An error occurred while updating the profile.", Alert.AlertType.ERROR);
                }
            }
//        }
//         else System.out.println("Invalid input.");
    }
    private boolean validateInputs(String fullName, String introduction ) {
        String fullNameError = ValidationUtils.validateFullName(fullName);
        String introductionError = ValidationUtils.validateIntroduction(introduction);

        if (fullNameErrorLabel != null) {
            fullNameErrorLabel.setText(fullNameError);
        }

        if (introductionErrorLabel != null) {
            introductionErrorLabel.setText(introductionError);
        }
        return fullNameErrorLabel == null;
    }


    private void clearErrorMessages() {
        fullNameErrorLabel.setText("");
        introductionErrorLabel.setText("");
    }

    private void clearInputs() {
        fullNameField.clear();
        introductionTextField.clear();
    }
}
