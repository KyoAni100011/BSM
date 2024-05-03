package com.bsm.bsm.employee.book;

import com.bsm.bsm.book.BookService;
import com.bsm.bsm.admin.userAccount.ToggleSwitch;
import com.bsm.bsm.book.Book;
import com.bsm.bsm.utils.AlertUtils;
import com.bsm.bsm.utils.FXMLLoaderHelper;
import javafx.beans.property.BooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class TableItemController {

    public Label isbnLabel, priceLabel, bookNameLabel, quantityLabel;

    private final BookService bookService = new BookService();
    @FXML
    public ToggleButton toogleButton;
    @FXML
    public ToggleSwitch isOn;
    @FXML
    private String isbn;
    private ToggleGroup toggle;
    @FXML
    private Book book;


    @FXML
    private void initialize() {
        bookController.handleTableItemSelection(null);
    }
    @FXML
    private void handleRadioButtonClick(){
        if(!toogleButton.isSelected()){
            bookController.handleTableItemSelection(null);
        } else {
            bookController.handleTableItemSelection(isbn);
            System.out.println("state: " + book.isEnabled());
        }
    }

    @FXML
    private void handleToggleSwitchClick() {
        BooleanProperty oldState = isOn.switchedProperty();
        String isEnabledState = oldState.get() ? "disable" : "enable";
        String confirmationMessage = "Are you sure you want to " + isEnabledState + " this book?";
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation");
        confirmationAlert.setHeaderText(confirmationMessage);
        confirmationAlert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                if (!oldState.get()) {
                    //check if author/publisher/category disable, book can't be enabled
                    System.out.println("enabled");
                    if (!bookService.checkIfBookCanBeEnabled(isbn)) {
                        AlertUtils.showAlert("Error", "Can't enable book, because author/publisher/category is disabled", Alert.AlertType.ERROR);
                        isOn.setSwitchedProperty(oldState.get());
                        return;
                    }
                }
                if (bookService.setEnable(isbn, !oldState.get())) {
                    isOn.setSwitchedProperty(!oldState.get());
                    book.setEnabled(!oldState.get());
                    AlertUtils.showAlert("Success", "Book has been " + isEnabledState, Alert.AlertType.INFORMATION);
                } else {
                    AlertUtils.showAlert("Error", "Failed to " + isEnabledState + " author", Alert.AlertType.ERROR);
                }
            } else {
                isOn.setSwitchedProperty(oldState.get());
            }
        });
    }

    public void setToggleGroup(ToggleGroup toggleGroup) {
        toogleButton.setToggleGroup(toggleGroup);
    }
    @FXML
    private void handleTableItemDoubleClick(MouseEvent event) throws IOException {
        if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
            if (isbn != null) {
                if (!bookService.isEnable(isbn)) {
                    AlertUtils.showAlert("Error", "Need to enable book to view details", Alert.AlertType.ERROR);
                    return;
                }
                BookDetailController.handleTableItemSelection(isbn);
                FXMLLoaderHelper.loadFXML(new Stage(), "employee/book/bookDetail", "Book Detail");
            } else {
                AlertUtils.showAlert("Error", "Can't find book", Alert.AlertType.ERROR);

            }
        }
    }

    public void setBook(Book thisBook) {
        book = thisBook;
        isbn = thisBook.getIsbn();
        isbnLabel.setText(thisBook.getIsbn());
        bookNameLabel.setText(thisBook.getTitle());
        priceLabel.setText(String.valueOf(thisBook.getSalePrice()));
        quantityLabel.setText(String.valueOf(thisBook.getQuantity()));
        isOn.setSwitchedProperty(book.isEnabled());
    }
}