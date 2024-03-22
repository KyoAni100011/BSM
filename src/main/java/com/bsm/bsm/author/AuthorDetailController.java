package com.bsm.bsm.author;


import javafx.fxml.FXML;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;


public class AuthorDetailController {
    @FXML
    private TextField fullNameField,introductionTextField,idField;
    @FXML
    private final UpdateAuthorService updateAuthorService = new UpdateAuthorService();


    @FXML
    public void initialize() {
        Author author = updateAuthorService.getAuthor("33331111");
        setAuthorInfo(author);
    }
    private void setAuthorInfo(Author author) {

        fullNameField.setText(author.getName());
        introductionTextField.setText(author.getIntroduction());
        idField.setText(author.getId());
    }
    private void clearInputs() {
        fullNameField.clear();
        introductionTextField.clear();

    }
}
