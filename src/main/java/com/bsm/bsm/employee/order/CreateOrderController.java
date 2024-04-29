package com.bsm.bsm.employee.order;

import com.bsm.bsm.book.Book;
import com.bsm.bsm.book.BookService;
import com.bsm.bsm.customer.Customer;
import com.bsm.bsm.order.OrderService;
import com.bsm.bsm.utils.AlertUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.math.BigDecimal;
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
    private final ObservableList<String> bookNames = FXCollections.observableArrayList(); // Store filtered category items
    private final OrderService orderService = new OrderService();
    @FXML
    public TextField handleNameField, handlePhoneField, MoneyTextField;
    @FXML
    public Button handlePayButton, addBookButton, refreshButton;
    @FXML
    public Label subtotalLabel, discountLabel, totalLabel, totalQuantityItems, MoneyReturnLabel;
    @FXML
    public VBox pnItems = new VBox();
    private ObservableList<String> currentBookNamesData = FXCollections.observableArrayList(bookNames);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            MoneyTextField.setTextFormatter(new TextFormatter<>(integerFilter));
            handlePhoneField.setTextFormatter(new TextFormatter<>(integerFilter));

            BookService bookService = new BookService();
            List<Book> books = bookService.getAllBooks();

            for (var book : books) {
                if (book.isEnabled())
                    bookNames.add(book.getTitle());
            }

            orderItemController = new ArrayList<>();
            currentBookNamesData = bookNames;
            handleNameField.setText("Anonymous");

            MoneyTextField.textProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null && !newValue.isEmpty()) {
                    MoneyReturnLabel.setText(String.valueOf(Integer.parseInt(newValue) - Integer.parseInt(totalLabel.getText().isEmpty() ? "0" : totalLabel.getText())));
                } else {
                    MoneyReturnLabel.setText("0");

                }
            });

            handleNameField.textProperty().addListener((observable, oldValue, newValue) -> {
                if ((newValue != null && !newValue.isEmpty()) && (handlePhoneField.getText().length() == 11 || handlePhoneField.getText().length() == 10)) {
                    discountLabel.setText("(-5%) " + (int) (Integer.parseInt(subtotalLabel.getText()) * 0.05));
                    totalLabel.setText(String.valueOf((int) (Integer.parseInt(subtotalLabel.getText()) * 0.95)));
                } else {
                    discountLabel.setText("0%");
                }
            });

            handlePhoneField.textProperty().addListener((observable, oldValue, newValue) -> {
                if ((newValue.length() == 11 || newValue.length() == 10) && !handleNameField.getText().isEmpty()) {
                    System.out.println("get in phone" + handleNameField.getText());
                    discountLabel.setText("(-5%) " + (int) (Integer.parseInt(subtotalLabel.getText()) * 0.05));
                    totalLabel.setText(String.valueOf((int) (Integer.parseInt(subtotalLabel.getText()) * 0.95)));
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
            Book selectedBook = new Book("", null, "", "", true, 0, BigDecimal.valueOf(0), null, null);
            System.out.println("selectedBook: " + selectedBook);
            orderItemController.get(size).setBook(selectedBook);

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
                if (selectedBookName != null && !selectedBookName.isEmpty()) {
                    tempBookNames.remove(selectedBookName);
                }
            }
            for (OrderItemController controller : orderItemController) {
                ObservableList<String> tempEachBookNames = FXCollections.observableArrayList(tempBookNames);
                String selectedBookName = controller.getBookName();
                if (selectedBookName != null && !selectedBookName.isEmpty()) {
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

        totalQuantityItems.setText("Total: " + Quan + " items");

        if (!Objects.equals(discountLabel.getText(), "0%")) {
            discountLabel.setText("(-5%) " + (int) (Integer.parseInt(subtotalLabel.getText()) * 0.05));
            totalLabel.setText(String.valueOf((int) (Integer.parseInt(subtotalLabel.getText()) * 0.95)));
        } else {
            totalLabel.setText(String.valueOf(Sub));
        }
    }

    public void handleRefreshButton(ActionEvent actionEvent) {
    }

    public void handlePayActionButton(MouseEvent mouseEvent) {
        List<String> selectedBooks = new ArrayList<>();
        List<Integer> quantities = new ArrayList<>();
        List<BigDecimal> salePrices = new ArrayList<>();

        for (OrderItemController controller : orderItemController) {
            String bookName = controller.getBookName();
            int quantity = controller.getItemQuantity();
            for (var book : bookNames) {
                if (book.equalsIgnoreCase(bookName)) {
                    Book selectedBook = getBookByTitle(book);
                    salePrices.add(selectedBook.getSalePrice());
                }
            }
            if (!bookName.isEmpty() && quantity > 0) {
                selectedBooks.add(bookName);
                quantities.add(quantity);
            }
        }

        for (int i = 0; i < selectedBooks.size(); i++) {
            System.out.println("Book: " + selectedBooks.get(i) + ", Quantity: " + quantities.get(i) + ", Sale Price: " + salePrices.get(i));
        }

        Customer customer = null;
        if (handleNameField.equals("Anonymous") || (handleNameField.getText() != null && handlePhoneField != null)) {
            //get customer information
            boolean isMember = handleNameField.getText().equalsIgnoreCase("Anonymous");
            customer = new Customer(handleNameField.getText(), handlePhoneField.getText(), isMember);
            System.out.println(customer);

            if(orderService.createOrder(selectedBooks, quantities, salePrices, customer)) {
                AlertUtils.showAlert("Success", "Order created successfully", Alert.AlertType.CONFIRMATION);
            } else {
                AlertUtils.showAlert("Error", "Order creation failed", Alert.AlertType.ERROR);
            }
        } else {
            System.out.println("Please fill in the customer information");
        }
    }

    public Book getBookByTitle(String title) {
        BookService bookService = new BookService();
        List<Book> books = bookService.getAllBooks();
        for (var book : books) {
            if (book.getTitle().equals(title)) {
                return book;
            }
        }
        return null;
    }
}
