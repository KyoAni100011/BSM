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

    private final UpdateAuthorService updateAuthorService = new UpdateAuthorService();
    @FXML
    public void initialize() {
        //set temp id, need to get id from table view
        Author author = updateAuthorService.getAuthor("33331111");
        setAuthorInfo(author);
    }

    private void setAuthorInfo(Author author) {
        fullNameField.setText(author.getName());
        introductionTextField.setText(author.getIntroduction());
    }

    @FXML
    private void handleSaveChanges(ActionEvent event) throws ParseException {
        clearErrorMessages();
        //set temp id, need to get id from table view
        String id = "33331111";
        String fullName = fullNameField.getText();
        String introduction = introductionTextField.getText();
        if (validateInputs(fullName,introduction)) {
            updateAuthorService.updateAuthor(id, fullName, introduction);
            AlertUtils.showAlert("Success", "Author updated successfully.", Alert.AlertType.INFORMATION);
            clearInputs();
            var author = updateAuthorService.getAuthor(fullName);
            setAuthorInfo(author);
        }
    }
    private boolean validateInputs(String fullName, String introduction ) {
        String fullNameError = ValidationUtils.validateFullName(fullName,"author");
        String introductionError = ValidationUtils.validateIntroduction(introduction,"author");

        if (fullNameError != null) {
            fullNameErrorLabel.setText(fullNameError);
        }
        if (introductionError != null) {
            introductionErrorLabel.setText(introductionError);
        }
        return fullNameError == null && introductionError == null;
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
