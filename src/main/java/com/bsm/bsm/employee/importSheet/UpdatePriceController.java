package com.bsm.bsm.employee.importSheet;

import com.bsm.bsm.book.BookService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class UpdatePriceController {
    private static String bookName;

    @FXML
    public Label bookNameErrorLabel, sellPriceErrorLabel;

    @FXML
    public TextField bookNameField, sellPriceTextField;

    private final BookService bookService = new BookService();

    static void setBookName(String oldBookName) {
        bookName = oldBookName;
        System.out.println("oldBookName = " + oldBookName);
    }
    @FXML
    void initialize() {
        System.out.println("BA:" + bookName);
        bookNameField.setText(bookName);
        sellPriceTextField.setText(bookService.getBookByName(bookName).getSalePrice().toString());
    }


    public void handleSaveChanges(ActionEvent event) {
        clearErrorMessages();
        String bookName = bookNameField.getText();
        String sellPrice = sellPriceTextField.getText();

//        if (validateInputs(bookName, sellPrice)) {
//            if (bookService.updateSellPrice(bookName, sellPrice)) {
//                clearInputs();
//                ImportSheetController.handleAfterUpdatePrice();
//            }
//            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//            stage.close();
//        }
    }

    private void clearErrorMessages() {
        bookNameErrorLabel.setText("");
        sellPriceErrorLabel.setText("");
    }

    private boolean validateInputs(String bookName, String sellPrice) {
        boolean isValid = true;
        if (bookName.isEmpty()) {
            bookNameErrorLabel.setText("Book name is required.");
            isValid = false;
        }
        if (sellPrice.isEmpty()) {
            sellPriceErrorLabel.setText("Sell price is required.");
            isValid = false;
        } else if (!sellPrice.matches("\\d+")) {
            sellPriceErrorLabel.setText("Sell price must be a valid number.");
            isValid = false;
        }
        return isValid;
    }

    private void clearInputs() {
        bookNameField.clear();
        sellPriceTextField.clear();
    }
}