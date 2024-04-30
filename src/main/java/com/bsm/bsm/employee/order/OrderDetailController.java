package com.bsm.bsm.employee.order;

import com.bsm.bsm.order.Order;
import com.bsm.bsm.order.OrderBooksDetails;
import com.bsm.bsm.order.OrderService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailController {
    private static final OrderService importSheetService = new OrderService();
    private static final OrderService orderService = new OrderService();
    public static List<OrderBooksDetails> listBook = new ArrayList<>();
    private static int id = 1;
    private static Order order;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    @FXML
    public TextField employeeNameField, totalPricefield, idField, customerNameField;
    @FXML
    public DatePicker importDatePicker;
    @FXML
    public VBox bookItem;

    static void handleTableItemSelection(int myId, Order thisorder) {
        id = myId;
        listBook = orderService.getOrderBookDetails(id);
        order = thisorder;
    }

    @FXML
    public void initialize() {
        new OrderDetailController();
        importDatePicker.getEditor().setOpacity(1);
        setOrderInfo();
        updateSheet();
    }

    private void setOrderInfo() {
        idField.setText(String.valueOf(order.getId()));
        employeeNameField.setText(order.getEmployee().getName());
        customerNameField.setText(order.getCustomer().getName());
        totalPricefield.setText(String.valueOf(order.getTotalPrice()));

        // pass data from table to detail and create to detail have dif day format
        System.out.println("here" + order.getOrderDate());
        importDatePicker.setValue(LocalDate.parse(order.getOrderDate(), dateFormatter));
//        if (isValidDateFormat(order.getOrderDate(), "dd/MM/yyyy")) {
//            // Parse the date with "dd/MM/yyyy" format
//            importDatePicker.setValue(LocalDate.parse(order.getOrderDate(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
//        } else {
//            // Parse the date with "yyyy-MM-dd" format
//            importDatePicker.setValue(LocalDate.parse(order.getOrderDate(), dateFormatter));
//        }
    }

    private void updateSheet() {
        bookItem.getChildren().clear();

        for (OrderBooksDetails b : listBook) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/bsm/bsm/view/employee/order/orderDetailItem.fxml"));
                Node item = fxmlLoader.load();
                OrderDetailItemController orderDetailItemController = fxmlLoader.getController();
                orderDetailItemController.setItem(b);
                bookItem.getChildren().add(item);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
