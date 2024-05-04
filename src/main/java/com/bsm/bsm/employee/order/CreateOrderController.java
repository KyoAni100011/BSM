package com.bsm.bsm.employee.order;

import com.bsm.bsm.book.Book;
import com.bsm.bsm.book.BookService;
import com.bsm.bsm.customer.Customer;
import com.bsm.bsm.order.Order;
import com.bsm.bsm.order.OrderService;
import com.bsm.bsm.utils.AlertUtils;
import com.bsm.bsm.utils.FXMLLoaderHelper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.SQLException;
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
            List<Book> books = bookService.display();

            for (var book : books) {
                if (book.isEnabled() && book.getQuantity() > 0 && book.getSalePrice().compareTo(BigDecimal.ZERO) > 0)
                    bookNames.add(book.getTitle());
            }

            orderItemController = new ArrayList<>();
            currentBookNamesData = bookNames;
            handleNameField.setText("");

            MoneyTextField.textProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null && !newValue.isEmpty()) {
                    BigDecimal moneyInput = new BigDecimal(newValue);
                    BigDecimal totalLabelValue = totalLabel.getText().isEmpty() ? BigDecimal.ZERO : new BigDecimal(totalLabel.getText());
                    BigDecimal moneyReturn = moneyInput.subtract(totalLabelValue);
                    MoneyReturnLabel.setText(moneyReturn.toString());
                } else {
                    MoneyReturnLabel.setText("0");
                }
            });

            handleNameField.textProperty().addListener((observable, oldValue, newValue) -> {
                if ((newValue != null && !newValue.isEmpty()) && (handlePhoneField.getText().length() == 11 || handlePhoneField.getText().length() == 10)) {
                    BigDecimal subtotal = new BigDecimal(subtotalLabel.getText());
                    BigDecimal discount = subtotal.multiply(BigDecimal.valueOf(0.05));
                    BigDecimal total = subtotal.multiply(BigDecimal.valueOf(0.95));

                    discountLabel.setText("(-5%) " + discount);
                    totalLabel.setText(total.toString());
                } else {
                    discountLabel.setText("0%");
                    totalLabel.setText(subtotalLabel.getText());

                }
                if(!MoneyTextField.getText().isEmpty()){
                    BigDecimal totalLabelValue = totalLabel.getText().isEmpty() ? BigDecimal.ZERO : new BigDecimal(totalLabel.getText());
                    BigDecimal moneyReceive =  new BigDecimal(MoneyTextField.getText());
                    BigDecimal moneyReturn = moneyReceive.subtract(totalLabelValue);
                    MoneyReturnLabel.setText(moneyReturn.toString());
                }
            });


            handlePhoneField.textProperty().addListener((observable, oldValue, newValue) -> {
                if ((newValue.length() == 11 || newValue.length() == 10) && !handleNameField.getText().isEmpty()) {
                    BigDecimal subtotal = new BigDecimal(subtotalLabel.getText());
                    BigDecimal discount = subtotal.multiply(BigDecimal.valueOf(0.05));
                    BigDecimal total = subtotal.subtract(discount);

                    discountLabel.setText("(-5%) " + discount);
                    totalLabel.setText(String.valueOf(total));

                } else {
                    totalLabel.setText(subtotalLabel.getText());
                    discountLabel.setText("0%");
                }
                if(!MoneyTextField.getText().isEmpty()){
                    BigDecimal totalLabelValue = totalLabel.getText().isEmpty() ? BigDecimal.ZERO : new BigDecimal(totalLabel.getText());
                    BigDecimal moneyReceive =  new BigDecimal(MoneyTextField.getText());
                    BigDecimal moneyReturn = moneyReceive.subtract(totalLabelValue);
                    MoneyReturnLabel.setText(moneyReturn.toString());
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
        BigDecimal subtotal = BigDecimal.ZERO;
        int totalQuantity = 0;

        for (OrderItemController controller : orderItemController) {
            subtotal = subtotal.add(controller.getSubtotal());
            totalQuantity += controller.getItemQuantity();
        }

        subtotalLabel.setText(subtotal.toString());

        totalQuantityItems.setText("Total: " + totalQuantity + " items");

        if (!discountLabel.getText().equals("0%")) {
            BigDecimal discountAmount = subtotal.multiply(BigDecimal.valueOf(0.05));
            discountLabel.setText("(-5%) " + discountAmount);
            BigDecimal discountedTotal = subtotal.subtract(discountAmount);
            totalLabel.setText(discountedTotal.toString());
        } else {
            totalLabel.setText(subtotal.toString());
        }
        if(!MoneyTextField.getText().isEmpty()){
            BigDecimal totalLabelValue = totalLabel.getText().isEmpty() ? BigDecimal.ZERO : new BigDecimal(totalLabel.getText());
            BigDecimal moneyReceive =  new BigDecimal(MoneyTextField.getText());
            BigDecimal moneyReturn = moneyReceive.subtract(totalLabelValue);
            MoneyReturnLabel.setText(moneyReturn.toString());
        }
    }


    public void handlePayActionButton(MouseEvent mouseEvent) {
        System.out.println("hi");
        List<String> selectedBooks = new ArrayList<>();
        List<Integer> quantities = new ArrayList<>();
        List<BigDecimal> salePrices = new ArrayList<>();
        if (Objects.equals(totalLabel.getText(), "0")) {
            AlertUtils.showAlert("Error", "Please choose book to create order", Alert.AlertType.CONFIRMATION);
            return;
        }

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

        Customer customer = null;
        if ((!handleNameField.getText().isEmpty() && (handlePhoneField.getText().length() == 11 || handlePhoneField.getText().length() == 10) || (handleNameField.getText().isEmpty() && handlePhoneField.getText().isEmpty())) && (!MoneyTextField.getText().isEmpty()) && new BigDecimal(MoneyReturnLabel.getText()).compareTo(BigDecimal.ZERO) >= 0) {
            //get customer information

            boolean isMember = !handleNameField.getText().isEmpty();
            customer = new Customer(handleNameField.getText(), handlePhoneField.getText(), isMember);
            if (!isMember) customer.setName("Anonymous");
            System.out.println("all select" + selectedBooks);
            if (orderService.createOrder(selectedBooks, quantities, salePrices, customer)) {
                try {
                    Order order = orderService.getOrderByCustomer(customer);
                    OrderDetailController.handleTableItemSelection(order.getId(), order);
                    // call screen order detail here with order and orderBooksDetails params
                    FXMLLoaderHelper.loadFXML(new Stage(), "employee/order/orderDetail");
                    cleanData();

                } catch (SQLException e) {
                    System.out.println("get order info: " + e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                AlertUtils.showAlert("Error", "Order creation failed", Alert.AlertType.ERROR);
            }
        } else if (MoneyTextField.getText().isEmpty()) {
            AlertUtils.showAlert("Error", "Please fill in the money received field", Alert.AlertType.CONFIRMATION);
        } else if(new BigDecimal(MoneyReturnLabel.getText()).compareTo(BigDecimal.ZERO) < 0) {
            AlertUtils.showAlert("Error", "Money return should greater than total price", Alert.AlertType.CONFIRMATION);
        } else {
            AlertUtils.showAlert("Error", "Please fill in the user's information completely", Alert.AlertType.CONFIRMATION);

        }
    }

    public Book getBookByTitle(String title) {
        BookService bookService = new BookService();
        List<Book> books = bookService.display();
        for (var book : books) {
            if (book.getTitle().equals(title)) {
                return book;
            }
        }
        return null;
    }

    private void cleanData() {
        currentBookNamesData = FXCollections.observableArrayList(bookNames);
        handleNameField.setText("");
        handlePhoneField.setText("");
        subtotalLabel.setText("0");
        discountLabel.setText("0%");
        totalLabel.setText("0");
        MoneyTextField.setText("");
        MoneyReturnLabel.setText("");
        totalQuantityItems.setText("Total: 0 items");
        orderItemController = new ArrayList<>();
        pnItems.getChildren().clear();
        bookNames.clear();
        BookService bookService = new BookService();
        List<Book> books = bookService.display();

        for (var book : books) {
            if (book.isEnabled() && book.getQuantity() > 0 && book.getSalePrice().compareTo(BigDecimal.ZERO) > 0)
                bookNames.add(book.getTitle());
        }
    }

}
