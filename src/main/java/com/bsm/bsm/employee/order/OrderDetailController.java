package com.bsm.bsm.employee.order;

import com.bsm.bsm.employee.bookCategories.CategoryDetailController;
import com.bsm.bsm.employee.order.OrderDetailItemController;
import com.bsm.bsm.order.OrderBooksDetails;
import com.bsm.bsm.order.Order;
import com.bsm.bsm.order.OrderService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailController {
    @FXML
    public TextField employeeNameField, totalPricefield, idField,  customerNameField;
    @FXML
    public DatePicker importDatePicker;
    @FXML
    public VBox bookItem;
    private static String id ="1";
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static Order order ;

    public static List<OrderBooksDetails> listBook = new ArrayList<>();
    private static final OrderService importSheetService =  new OrderService();
    @FXML
    public void initialize() {
        new OrderDetailController();
        setOrderInfo();
        updateSheet();
    }
    static void handleTableItemSelection(String myId, Order thisorder) {
        id = myId;
        listBook.add(new OrderBooksDetails("ORD001", "BB001", 5, new BigDecimal("25.50")));
        listBook.add(new OrderBooksDetails("ORD002", "BB002", 3, new BigDecimal("15.75")));
        listBook.add(new OrderBooksDetails("ORD003", "BB003", 8, new BigDecimal("32.20")));

//        listBook = importSheetService.getISheetBookDetails(id);
        order = thisorder;
    }
    private void setOrderInfo() {
        idField.setText(String.valueOf(order.getId()));
        employeeNameField.setText(order.getEmployee().getName());
        totalPricefield.setText(String.valueOf(order.getTotalPrice()));
        importDatePicker.setValue(LocalDate.parse(order.getOrderDate(), dateFormatter));
    }
    private void updateSheet(){
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
