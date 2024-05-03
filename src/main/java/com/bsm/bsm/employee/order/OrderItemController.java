package com.bsm.bsm.employee.order;

import com.bsm.bsm.book.Book;
import com.bsm.bsm.utils.AlertUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.util.StringConverter;
import org.controlsfx.control.SearchableComboBox;

import java.math.BigDecimal;
import java.nio.file.SecureDirectoryStream;
import java.util.Objects;

public class OrderItemController {
    CreateOrderController thisParentController;
    @FXML
    private Label pricePerItemLabel, indexLabel, totalPriceLabel;
    @FXML
    private Button minusButton, addButton;
    @FXML
    private Group removeButton;
    @FXML
    private SearchableComboBox bookNameComboBox;
    @FXML
    private TextField QuantityField;
    private BigDecimal subtotal = BigDecimal.ZERO;
    private int itemQuan = 0;
    private String bookName = "";
    private Book thisBook;
    private int quantity;

    public String getBookName() {
        return bookName;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public int getItemQuantity() {
        return itemQuan;
    }

    @FXML
    private void initialize() {
        setupQuantityField();
        minusButton.setDisable(true);
        addButton.setDisable(true);
        QuantityField.setDisable(true);
        QuantityField.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.equals("0")){
                QuantityField.setText(String.valueOf(1));
            }
            else if(!newValue.equals("")){
                try {
                    if (Integer.parseInt(newValue) > quantity) {
                        AlertUtils.showAlert("Error", "There are only " + quantity + " book left", Alert.AlertType.ERROR);
                        QuantityField.setText(String.valueOf(1));
                    }

                    itemQuan = Integer.parseInt(QuantityField.getText());

                    String totalPrice = thisBook.getSalePrice().multiply(BigDecimal.valueOf(itemQuan)).toString();
                    totalPriceLabel.setText(totalPrice);
                    subtotal = new BigDecimal(totalPrice);
                    thisParentController.handleCountSubtotalAndQuantity();
                } catch (NumberFormatException e) {

                    return;
                }
            }
        });

        bookNameComboBox.valueProperty().addListener((obs, oldValue, newValue) -> {
            try {
                if (newValue != null) {
                    // get detail data here
                    Book book = thisParentController.getBookByTitle(String.valueOf(newValue));
                    thisBook = book;
                    quantity = thisBook.getQuantity();
                    setBook(book);
                    thisParentController.handleCountSubtotalAndQuantity();

                    // Your other code here
                    System.out.println("Book here: " + book.getTitle() + " " + book.getQuantity() + " " + book.getSalePrice());
                }
            } catch (Exception e) {
                System.out.println("listener " + e);
            }
        });


    }

    public void setListBook(ObservableList<String> bookNames) {
        if (bookNameComboBox.getItems().isEmpty()) {
            bookNameComboBox.getItems().addAll(bookNames);
        } else {
            // Create a copy of the current items in the ComboBox
            ObservableList<String> listDis = FXCollections.observableArrayList();
            ObservableList<String> currentItems = FXCollections.observableArrayList(bookNameComboBox.getItems());

            for (String item : currentItems) {
                if (!bookNames.contains(item)) {
                    listDis.add(item);
                }
            }

            bookNameComboBox.setCellFactory(lv -> new ListCell<String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setText(null);
                    } else {
                        setText(item);
                        setDisable(listDis.contains(item));
                    }
                }
            });
        }

    }


    public void setIndex(CreateOrderController controller, int index) {
        indexLabel.setText(String.valueOf(index));
        thisParentController = controller;
    }

    public void setBook(Book book) {
        try {
            if (thisBook != null) {
                bookName = thisBook.getTitle();
                pricePerItemLabel.setText(String.valueOf(thisBook.getSalePrice()));
                totalPriceLabel.setText(String.valueOf(thisBook.getSalePrice()));
                QuantityField.setText(String.valueOf(1));
                minusButton.setDisable(false);
                addButton.setDisable(false);
                QuantityField.setDisable(false);
                bookName = (String) bookNameComboBox.getValue();
                subtotal = new BigDecimal(String.valueOf(thisBook.getSalePrice()));
                thisParentController.setAlreadyClick();
            } else {
                System.out.println("That book is not available");
            }
        } catch (Exception e) {
            System.out.println("set book" + e);
        }

    }

    @FXML
    private void handleRemoveButtonClick() {
        thisParentController.handleTableItemSelection(Integer.parseInt(indexLabel.getText()));

    }

    public void handleMinusButton(ActionEvent actionEvent) {
        if (Integer.parseInt(QuantityField.getText()) == 1) {
            String confirmationMessage = "Are you sure you want to delete this book?";
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Confirmation");
            confirmationAlert.setHeaderText(confirmationMessage);
            confirmationAlert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    handleRemoveButtonClick();
                }
            });

        } else {
            QuantityField.setText(String.valueOf(Integer.parseInt(QuantityField.getText()) - 1));

        }

    }

    public void handleAddButton(ActionEvent actionEvent) {
        if (Integer.parseInt(QuantityField.getText()) == quantity) {
            AlertUtils.showAlert("Error", "There are only " + quantity + " book left", Alert.AlertType.ERROR);
        } else {
            QuantityField.setText(String.valueOf(Integer.parseInt(QuantityField.getText()) + 1));

        }
    }

    private void setupQuantityField() {
        // Create a TextFormatter with a filter that allows only numeric input or an empty string
        TextFormatter<Integer> formatter = new TextFormatter<>(new StringConverter<Integer>() {
            @Override
            public String toString(Integer object) {
                return object == null ? "" : object.toString();
            }

            @Override
            public Integer fromString(String string) {
                // Return null if the string is non-numeric, otherwise parse the string into an Integer
                if (!string.matches("\\d*")) {
                    return null;
                }
                // Return 0 for empty strings, otherwise parse the string into an Integer
                return string.isEmpty() ? 0 : Integer.parseInt(string);
            }
        }, null, change -> {
            // Filter out non-numeric characters
            if (change.isContentChange()) {
                if (!change.getControlNewText().matches("\\d*")) {
                    change.setText(change.getControlText());
                }
            }
            return change;
        });

        // Apply the TextFormatter to your TextField for quantity
        QuantityField.setTextFormatter(formatter);
    }


}
