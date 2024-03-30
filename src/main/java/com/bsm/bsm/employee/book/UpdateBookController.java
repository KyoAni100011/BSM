package com.bsm.bsm.employee.book;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class UpdateBookController {
    @FXML
    private Label output;

    String isbn = "1234567890123";

    @FXML
    public void initialize() {

    }

    @FXML
    private void handleSaveChanges(ActionEvent event) {
        clearErrorMessages();
        String title = "";
        String publishingDate = "";
        String quantity = "";
        String salePrice = "";
        String languages = "";

        String authorId = "";
        String publisherId = "";
        String categoryId = "";

        // Dummy data
        title = "The Great Gatsby";
        publishingDate = "24/04/2003";
        quantity = "100";
        salePrice = "80000";
        languages = "English";

        authorId = "F. Scott Fitzgerald";
        publisherId = "Scribner";
        categoryId = "Classic Literature";

    }

    private boolean validateInputs(String isbn, String title, String publisherId, String authorId, String publishingDate, String languages, String isEnabled, String quantity, String salePrice) {
        return true;
    }

    private void clearErrorMessages() {

    }

    private void clearInputs() {

    }
}
