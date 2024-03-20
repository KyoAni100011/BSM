package com.bsm.bsm.author;

import com.bsm.bsm.utils.AlertUtils;
import com.bsm.bsm.utils.ValidationUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.text.ParseException;


public class UpdateAuthorController {
    @FXML
    private Label fullNameErrorLabel, introductionErrorLabel;
    @FXML
    private TextField fullNameField,introductionTextField;
    @FXML
    public void initialize() {
    }
    @FXML
    private void handleSaveChanges(ActionEvent event) throws ParseException {
        clearErrorMessages();
        String fullName = fullNameField.getText();
        String introduction = introductionTextField.getText();
        if (validateInputs(fullName,introduction)) {
            AlertUtils.showAlert("Success", "Author updated successfully.", Alert.AlertType.INFORMATION);
        }
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
        return fullNameErrorLabel == null ;
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
