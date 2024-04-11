package com.bsm.bsm.employee.book;

import com.bsm.bsm.book.BookService;
import com.bsm.bsm.admin.userAccount.ToggleSwitch;
import com.bsm.bsm.employee.book.bookController;
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
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
        }
    }

    @FXML
    private void handleToggleSwitchClick() {
//        isOn.setUserId(id); // Pass the idLabel data to ToggleSwitch
        BooleanProperty oldState = isOn.switchedProperty();
        String confirmationMessage = "Are you sure you want to " + (!oldState.get() ? "enable" : "disable") + " this author?";
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation");
        confirmationAlert.setHeaderText(confirmationMessage);
        confirmationAlert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
//                if (oldState.get()) {
//                    if (!authorService.disableAuthor(id)) {
//                        AlertUtils.showAlert("Error", "Failed to disable author", Alert.AlertType.ERROR);
//                        return;
//                    }
//                } else {
//                    if (!authorService.enableAuthor(id)) {
//                        AlertUtils.showAlert("Error", "Failed to enable author", Alert.AlertType.ERROR);
//                        return;
//                    }
//                }
                AlertUtils.showAlert("Success", "Author has been " + (!oldState.get() ? "enabled" : "disabled"), Alert.AlertType.INFORMATION);
            } else {
                oldState.setValue(!oldState.get());
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
//                BookDetailController.handleTableItemSelection(isbn);
//                FXMLLoaderHelper.loadFXML(new Stage(), "employee/book/bookDetail");
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
        // Set the last login label with the time elapsed
        isOn.setSwitchedProperty(book.isEnabled());
    }
}
