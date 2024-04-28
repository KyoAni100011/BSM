package com.bsm.bsm.employee.order;

import com.bsm.bsm.book.Book;
import com.bsm.bsm.book.BookService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public class CreateOrderController implements Initializable {
    private static List<OrderItemController> orderItemController;
    private final UnaryOperator<TextFormatter.Change> integerFilter = change -> {
        if (Pattern.matches("\\d*", change.getControlNewText())) {
            return change;
        } else {
            return null;
        }
    };

    @FXML
    public TextField handleNameField, handlePhoneField, MoneyTextField;
    @FXML
    public Button handlePayButton, addBookButton, refreshButton;
    @FXML
    public Label subtotalLabel, discountLabel, totalLabel, totalQuantityItems, MoneyReturnLabel;
    @FXML
    public VBox pnItems = new VBox();
    private final ObservableList<String> bookNames = FXCollections.observableArrayList(); // Store filtered category items
    private ObservableList<String> currentBookNamesData = FXCollections.observableArrayList(bookNames);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            MoneyTextField.setTextFormatter(new TextFormatter<>(integerFilter));
            handlePhoneField.setTextFormatter(new TextFormatter<>(integerFilter));

            BookService bookService = new BookService();
            List<Book> books = bookService.getAllBooks();

            for (var book : books) {
                bookNames.add(book.getTitle());
            }

            orderItemController = new ArrayList<>();
            currentBookNamesData = bookNames;

            MoneyTextField.textProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue == null) {
                    MoneyReturnLabel.setText("0");
                } else {
                    MoneyReturnLabel.setText(String.valueOf(Integer.parseInt(newValue) - Integer.parseInt(subtotalLabel.getText())));
                }
            });

            handleNameField.textProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null && (handlePhoneField.getText().length() == 11 || handlePhoneField.getText().length() == 10)) {
                    discountLabel.setText("(-30%)" + (int) (Integer.parseInt(subtotalLabel.getText()) * 0.3));
                    totalLabel.setText(String.valueOf((int) (Integer.parseInt(subtotalLabel.getText()) * 0.7)));

                } else {
                    discountLabel.setText("0%");
                }
            });

            handlePhoneField.textProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue.length() == 11 || newValue.length() == 10 && handleNameField.getText() != null) {
                    discountLabel.setText("(-30%)" + (int) (Integer.parseInt(subtotalLabel.getText()) * 0.3));
                    totalLabel.setText(String.valueOf((int) (Integer.parseInt(subtotalLabel.getText()) * 0.7)));

                } else {
                    discountLabel.setText("0%");
                }
            });
        } catch (Exception e) {
            System.out.println("ini" + e);
        }
    }

    void handleTableItemSelection(int index) {
        try {
            if (index - 1 < 0 || index - 1 >= pnItems.getChildren().size()) {
                return;
            }
            orderItemController.remove(index - 1);
            pnItems.getChildren().remove(index - 1);
            for (int i = 0; i < orderItemController.size(); i++) {
                orderItemController.get(i).setIndex(this, i + 1);

            }
            setAlreadyClick();
            handleCountSubtotalAndQuantity();
        } catch (Exception e) {
            System.out.println("handle" + e);

        }


    }

    public void handleAddBookButton() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/bsm/bsm/view/employee/order/orderItem.fxml"));
            Node item = fxmlLoader.load();
            int size = orderItemController.size();
            pnItems.getChildren().add(item);
            orderItemController.add(fxmlLoader.getController());
            orderItemController.get(size).setIndex(this, size + 1);
            orderItemController.get(size).setListBook(bookNames);
            orderItemController.get(size).setListBook(currentBookNamesData);

        } catch (Exception e) {
            System.out.println("this" + e.getMessage());
        }
    }

    public void setAlreadyClick() {
        try {
            ObservableList<String> tempBookNames = FXCollections.observableArrayList(bookNames);
            currentBookNamesData = tempBookNames;
            for (OrderItemController controller : orderItemController) {
                String selectedBookName = controller.getBookName();
                if (!selectedBookName.isEmpty()) {
                    tempBookNames.remove(selectedBookName);
                }
            }
            for (OrderItemController controller : orderItemController) {
                ObservableList<String> tempEachBookNames = FXCollections.observableArrayList(tempBookNames);
                String selectedBookName = controller.getBookName();
                if (!selectedBookName.isEmpty()) {
                    tempEachBookNames.add(selectedBookName);
                }
                controller.setListBook(tempEachBookNames);
            }
        } catch (Exception e) {
            System.out.println("handle click" + e);
        }
    }

    public void handleCountSubtotalAndQuantity() {
        int Sub = 0, Quan = 0;
        for (OrderItemController controller : orderItemController) {
            Sub += controller.getSubtotal();
            Quan += controller.getItemQuantity();
        }
        subtotalLabel.setText(String.valueOf(Sub));
        if (!Objects.equals(discountLabel.getText(), "0%")) {
            discountLabel.setText("(-30%)" + (int) (Integer.parseInt(subtotalLabel.getText()) * 0.3));
            totalLabel.setText(String.valueOf((int) (Integer.parseInt(subtotalLabel.getText()) * 0.7)));
        } else {
            totalLabel.setText(String.valueOf(Sub));
        }

        totalQuantityItems.setText("Total: " + Quan + " items");

    }

    public void handleRefreshButton(ActionEvent actionEvent) {
    }

}
