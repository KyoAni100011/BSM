package com.bsm.bsm.employee.order;

import com.bsm.bsm.order.Order;
import com.bsm.bsm.employee.EmployeeModel;
import com.bsm.bsm.user.UserSingleton;

import com.bsm.bsm.utils.NumericValidationUtils;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class OrderController implements Initializable  {
    private static String id;
    private final ToggleGroup toggleGroup = new ToggleGroup();
//    private final Orderservice Orderservice = new Orderservice();
    @FXML
    public Button customerLabel,employeeLabel,orderLabel,priceLabel,idLabel;
    @FXML
    public SVGPath priceSortLabel,CustomerSortLabel,orderSortLabel,employeeSortLabel,idSortLabel;
    @FXML
    public DatePicker fromDateField, toDateField;
    @FXML
    private TextField inputSearch;
    @FXML
    private VBox pnItems;
    @FXML
    private AnchorPane tableToolbar;
    @FXML
    private Button filterButton;
    @FXML
    public Button previousPaginationButton, nextPaginationButton, firstPaginationButton, secondPaginationButton, thirdPaginationButton, fourthPaginationButton, fifthPaginationButton;


    private List<Order> orders = null;
    private String sortOrder = "ASC";
    private String column = "id";
    private int currentPage = 1;
    private String inputSearchText = "";
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    static void handleTableItemSelection(String orderId) {
        id = orderId; // Store the selected user
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeButtonsAndLabels();
        try {
            loadAllOrders();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        initializePaginationButtons();

        inputSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                inputSearchText = newValue;
//                orders = Orderservice.search(inputSearchText);
                try {
                    updateOrdersList();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
//    public DatePicker fromDateField, toDateField;
    private void setupDatePicker() {
        fromDateField.setPromptText("dd/mm/yyyy");
        toDateField.setPromptText("dd/mm/yyyy");

        fromDateField.setConverter(new StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate date) {
                return date != null ? dateFormatter.format(date) : "";
            }

            @Override
            public LocalDate fromString(String string) {
                return string != null && !string.isEmpty() ? LocalDate.parse(string, dateFormatter) : null;
            }
        });

        toDateField.setConverter(new StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate date) {
                return date != null ? dateFormatter.format(date) : "";
            }

            @Override
            public LocalDate fromString(String string) {
                return string != null && !string.isEmpty() ? LocalDate.parse(string, dateFormatter) : null;
            }
        });
        fromDateField.getEditor().addEventFilter(KeyEvent.KEY_TYPED, NumericValidationUtils.numericValidation(10));
        toDateField.getEditor().addEventFilter(KeyEvent.KEY_TYPED, NumericValidationUtils.numericValidation(10));
    }

    private void loadAllOrders() throws IOException {
        orders = new ArrayList<>();
        //orders.add(new Order(UUID.randomUUID(),324,UUID.randomUUID(),LocalDate.of(2024, 4, 11),new BigDecimal("100.00")));
        updateOrdersList();
        //        orders = Orderservice.getAllOrders();
//        try {
//            updateOrdersList();
//        } catch (IOException e) {
//            System.out.println(e.getMessage());
//        }
    }

    private void initializeButtonsAndLabels() {
        customerLabel.setOnMouseClicked(this::handleLabelClick);
        employeeLabel.setOnMouseClicked(this::handleLabelClick);
        orderLabel.setOnMouseClicked(this::handleLabelClick);
        priceLabel.setOnMouseClicked(this::handleLabelClick);
        idLabel.setOnMouseClicked(this::handleLabelClick);

        priceSortLabel.setContent("");
        CustomerSortLabel.setContent("");
        orderSortLabel.setContent("");
        employeeSortLabel.setContent("");
        idSortLabel.setContent("");

    }

    private void initializePaginationButtons() {
        firstPaginationButton.setOnAction(this::handlePaginationButton);
        secondPaginationButton.setOnAction(this::handlePaginationButton);
        thirdPaginationButton.setOnAction(this::handlePaginationButton);
        fourthPaginationButton.setOnAction(this::handlePaginationButton);
        fifthPaginationButton.setOnAction(this::handlePaginationButton);
        previousPaginationButton.setOnAction(this::handlePaginationButton);
        nextPaginationButton.setOnAction(this::handlePaginationButton);
        fromDateField.setPromptText("dd/mm/yyyy");
        toDateField.setPromptText("dd/mm/yyyy");
    }

    @FXML
    void handleFromDayButton(ActionEvent event) {
        toDateField.setDayCellFactory(param -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.compareTo(fromDateField.getValue()) < 0 );
            }
        });
        if(fromDateField.getValue() != null && toDateField.getValue() != null  ){
            System.out.println("get in");
            // Sort calendar her
        }
//        try {
//            FXMLLoaderHelper.loadFXML(new Stage(), "order/bookOrders/addOrder");
//            //update orders list after adding new Order
//            orders = Orderservice.getAllOrders();
//            updateOrdersList();
//        } catch (IOException e) {
//            System.out.println(e.getMessage());
//        }
    }
    @FXML
    void handleToDayButton(ActionEvent event) {
        fromDateField.setDayCellFactory(param -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.compareTo(toDateField.getValue()) > 0 );
            }
        });
        if(fromDateField.getValue() != null && toDateField.getValue() != null  ){
            System.out.println("get in");
            // Sort calendar her
        }
//        try {
//            FXMLLoaderHelper.loadFXML(new Stage(), "order/bookOrders/addOrder");
//            //update orders list after adding new Order
//            orders = Orderservice.getAllOrders();
//            updateOrdersList();
//        } catch (IOException e) {
//            System.out.println(e.getMessage());
//        }
    }



    @FXML
    private void handlePaginationButton(ActionEvent event) {
        Button buttonClicked = (Button) event.getSource();
        if (buttonClicked == previousPaginationButton) {
            currentPage--;
        } else if (buttonClicked == nextPaginationButton) {
            currentPage++;
        } else {
            currentPage = Integer.parseInt(buttonClicked.getText());
        }

        try {
            updateOrdersList();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void updatePaginationButtons(int totalPages) {
        // Clear all pagination buttons visibility
        List<Button> paginationButtons = Arrays.asList(firstPaginationButton, secondPaginationButton, thirdPaginationButton, fourthPaginationButton, fifthPaginationButton);
        paginationButtons.forEach(button -> {
            button.setVisible(false);
            button.setManaged(false);
            button.getStyleClass().add("pagination-button-admin");
        });

        // Show pagination buttons based on the current page and total pages
        if (totalPages > 1) {
            int startPage = Math.max(1, Math.min(currentPage - 2, totalPages - 4));
            int endPage = Math.min(startPage + 4, totalPages);

            previousPaginationButton.setDisable(!(currentPage > 1));
            nextPaginationButton.setDisable(!(currentPage < totalPages));

            for (int i = startPage; i <= endPage; i++) {
                Button button;
                int buttonIndex = i - startPage;
                if (totalPages > 5 && startPage < 6) {
                    button = paginationButtons.get(buttonIndex);
                } else if (totalPages > 5) {
                    button = paginationButtons.get(buttonIndex + 1);
                } else {
                    button = paginationButtons.get(buttonIndex);
                }
                button.setText(String.valueOf(i));
                button.setManaged(true);
                button.setVisible(true);

                if (i == currentPage) {
                    button.setStyle("-fx-background-color: #F5A11C; -fx-text-fill: white;");
                } else {
                    button.setStyle(null);
                }
            }
        } else {
            previousPaginationButton.setDisable(true);
            firstPaginationButton.setText("1");
            firstPaginationButton.setVisible(true);
            firstPaginationButton.setManaged(true);
            firstPaginationButton.setStyle("-fx-background-color: #F5A11C; -fx-text-fill: white;");
            nextPaginationButton.setDisable(true);
        }
    }

    // fix this
    private void updateOrdersList() throws IOException {
        pnItems.getChildren().clear();
        int itemsPerPage = 10;
        int startIndex = (currentPage - 1) * itemsPerPage;
        int endIndex = Math.min(startIndex + itemsPerPage, orders.size());

        for (int i = startIndex; i < endIndex; i++) {
            Order order = orders.get(i);
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/bsm/bsm/view/employee/order/tableItem.fxml"));
                Node item = fxmlLoader.load();
                TableItemController tableItemController = fxmlLoader.getController();
//                tableItemController.setOrder(order);
                pnItems.getChildren().add(item);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        int totalPages = (int) Math.ceil((double) orders.size() / itemsPerPage);
        updatePaginationButtons(totalPages);
    }


    @FXML
    private void handleLabelClick(MouseEvent event) {
        Button clickedLabel = (Button) event.getSource();
        String columnName = clickedLabel.getText();
        if (columnName.equals(column)) {
            sortOrder = sortOrder.equals("ASC") ? "DESC" : "ASC";
        } else {
            sortOrder = "ASC";
        }
        column = columnName;
        idSortLabel.setContent(column.equals("ID") ? (sortOrder.equals("ASC") ? "M233.4 105.4c12.5-12.5 32.8-12.5 45.3 0l192 192c12.5 12.5 12.5 32.8 0 45.3s-32.8 12.5-45.3 0L256 173.3 86.6 342.6c-12.5 12.5-32.8 12.5-45.3 0s-12.5-32.8 0-45.3l192-192z" : "M233.4 406.6c12.5 12.5 32.8 12.5 45.3 0l192-192c12.5-12.5 12.5-32.8 0-45.3s-32.8-12.5-45.3 0L256 338.7 86.6 169.4c-12.5-12.5-32.8-12.5-45.3 0s-12.5 32.8 0 45.3l192 192z") : "");
        CustomerSortLabel.setContent(column.equals("Customer") ? (sortOrder.equals("ASC") ? "M233.4 105.4c12.5-12.5 32.8-12.5 45.3 0l192 192c12.5 12.5 12.5 32.8 0 45.3s-32.8 12.5-45.3 0L256 173.3 86.6 342.6c-12.5 12.5-32.8 12.5-45.3 0s-12.5-32.8 0-45.3l192-192z" : "M233.4 406.6c12.5 12.5 32.8 12.5 45.3 0l192-192c12.5-12.5 12.5-32.8 0-45.3s-32.8-12.5-45.3 0L256 338.7 86.6 169.4c-12.5-12.5-32.8-12.5-45.3 0s-12.5 32.8 0 45.3l192 192z") : "");
        orderSortLabel.setContent(column.equals("Order Date") ? (sortOrder.equals("ASC") ? "M233.4 105.4c12.5-12.5 32.8-12.5 45.3 0l192 192c12.5 12.5 12.5 32.8 0 45.3s-32.8 12.5-45.3 0L256 173.3 86.6 342.6c-12.5 12.5-32.8 12.5-45.3 0s-12.5-32.8 0-45.3l192-192z" : "M233.4 406.6c12.5 12.5 32.8 12.5 45.3 0l192-192c12.5-12.5 12.5-32.8 0-45.3s-32.8-12.5-45.3 0L256 338.7 86.6 169.4c-12.5-12.5-32.8-12.5-45.3 0s-12.5 32.8 0 45.3l192 192z") : "");
        employeeSortLabel.setContent(column.equals("Employee") ? (sortOrder.equals("ASC") ? "M233.4 105.4c12.5-12.5 32.8-12.5 45.3 0l192 192c12.5 12.5 12.5 32.8 0 45.3s-32.8 12.5-45.3 0L256 173.3 86.6 342.6c-12.5 12.5-32.8 12.5-45.3 0s-12.5-32.8 0-45.3l192-192z" : "M233.4 406.6c12.5 12.5 32.8 12.5 45.3 0l192-192c12.5-12.5 12.5-32.8 0-45.3s-32.8-12.5-45.3 0L256 338.7 86.6 169.4c-12.5-12.5-32.8-12.5-45.3 0s-12.5 32.8 0 45.3l192 192z") : "");
        priceSortLabel.setContent(column.equals("Total Price") ? (sortOrder.equals("ASC") ? "M233.4 105.4c12.5-12.5 32.8-12.5 45.3 0l192 192c12.5 12.5 12.5 32.8 0 45.3s-32.8 12.5-45.3 0L256 173.3 86.6 342.6c-12.5 12.5-32.8 12.5-45.3 0s-12.5-32.8 0-45.3l192-192z" : "M233.4 406.6c12.5 12.5 32.8 12.5 45.3 0l192-192c12.5-12.5 12.5-32.8 0-45.3s-32.8-12.5-45.3 0L256 338.7 86.6 169.4c-12.5-12.5-32.8-12.5-45.3 0s-12.5 32.8 0 45.3l192 192z") : "");
        try {
//            orders = Orderservice.sort(orders, isAscending, column);
//            updateOrdersList();
//            orders.forEach(System.out::println);
            System.out.println("-".repeat(30));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
