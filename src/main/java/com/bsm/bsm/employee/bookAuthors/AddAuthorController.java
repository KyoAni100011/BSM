package com.bsm.bsm.employee.bookAuthors;

import com.bsm.bsm.author.Author;
import com.bsm.bsm.author.AuthorService;
import com.bsm.bsm.utils.AlertUtils;
import com.bsm.bsm.utils.ValidationUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import java.text.ParseException;

public class AddAuthorController {
    @FXML
    private Label fullNameErrorLabel, introductionErrorLabel;
    @FXML
    private TextField fullNameField;
    @FXML
    private TextArea introductionTextField;


    private final AuthorService authorService = new AuthorService();

    @FXML
    public void initialize() {
    }
    @FXML
    private void handleSaveChanges(ActionEvent event) throws ParseException {
        clearErrorMessages();
        String fullName = fullNameField.getText();
        String introduction = introductionTextField.getText();

        if (validateInputs(fullName,introduction)) {
            if (authorService.checkAuthorExists(fullName)) {
                fullNameErrorLabel.setText("Author already exists.");
            } else {
                Author newAuthor = new Author(fullName, introduction);
                if (authorService.add(newAuthor)) {
                    AlertUtils.showAlert("Success", "Add author successfully.", Alert.AlertType.INFORMATION);
                    clearInputs();
                } else {
                    AlertUtils.showAlert("Error", "An error occurred while adding the author.", Alert.AlertType.ERROR);
                }
            }
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
