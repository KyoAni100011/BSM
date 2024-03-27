package com.bsm.bsm.employee.bookAuthors;

import com.bsm.bsm.author.Author;
import com.bsm.bsm.author.AuthorService;
import com.bsm.bsm.utils.AlertUtils;
import com.bsm.bsm.utils.ValidationUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.text.ParseException;


public class UpdateAuthorController {
    @FXML
    private Label fullNameErrorLabel, introductionErrorLabel;
    @FXML
    private TextField fullNameField,introductionTextField;

    @FXML
    private static String name;
    String id = "33331111"; //set temp id, need to get id from table view

    private final AuthorService authorService = new AuthorService();

    public static void handleTableItemSelection(String name) {
        name = name;
    }

    @FXML
    public void initialize() {
        //set temp oldName, need to get oldName from table view
        Author author = authorService.getAuthor(id);
        setAuthorInfo(author);
    }

    private void setAuthorInfo(Author author) {
        fullNameField.setText(author.getName());
        introductionTextField.setText(author.getIntroduction());
    }

    @FXML
    private void handleSaveChanges(ActionEvent event) throws ParseException {
        clearErrorMessages();

        String fullName = fullNameField.getText();
        String introduction = introductionTextField.getText();
        if (validateInputs(fullName,introduction)) {
            //check if author is enabled
            boolean isEnabled = authorService.isEnabled(id);
            if (!isEnabled) {
                AlertUtils.showAlert("Error", "Author is disabled. Please enable it first.", Alert.AlertType.ERROR);
                return;
            }

            //check if author already exists
            if (authorService.checkAuthorExists(fullName)) {
                fullNameErrorLabel.setText("Author already exists.");
                return;
            }

            Author newAuthor = new Author(id, fullName, introduction, isEnabled);
            if (authorService.updateAuthor(newAuthor)) {
                AlertUtils.showAlert("Success", "Profile updated successfully.", Alert.AlertType.INFORMATION);
            } else {
                AlertUtils.showAlert("Error", "Profile update failed.", Alert.AlertType.ERROR);
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
