package com.bsm.bsm.employee.order;

import com.bsm.bsm.order.Order;
import com.bsm.bsm.order.OrderService;
import com.bsm.bsm.order.ToggleSwitch;
import com.bsm.bsm.utils.FXMLLoaderHelper;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TableItemController {
    private final OrderService orderService = new OrderService();
    public Label idLabel, customerNameLabel, EmployeeNameLabel, OrderDateLabel, PriceLabel;
    @FXML
    public ToggleButton toogleButton;
    @FXML
    public ToggleSwitch isOn;
    @FXML
    private int id;
    private ToggleGroup toggle;
    @FXML
    private Order order;

    @FXML
    private void initialize() {
        OrderController.handleTableItemSelection(null);
    }


    @FXML
    private void handleTableItemDoubleClick(MouseEvent event) throws IOException {
        if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
            OrderDetailController.handleTableItemSelection(id, order);
            FXMLLoaderHelper.loadFXML(new Stage(), "employee/order/orderDetail", "Order Detail");
        }
    }

    private static String getFormattedDate(String dateString) {
        LocalDate date = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return date.format(formatter);
    }

    public void setOrder(Order thisOrder) {
        order = thisOrder;
        id = thisOrder.getId();

        idLabel.setText(String.valueOf(thisOrder.getId()));
        customerNameLabel.setText(String.valueOf(thisOrder.getCustomer().getName()));
        EmployeeNameLabel.setText(String.valueOf(thisOrder.getEmployee().getName()));
        OrderDateLabel.setText(String.valueOf(getFormattedDate(thisOrder.getOrderDate())));
        PriceLabel.setText(String.valueOf(thisOrder.getTotalPrice()));
    }
}
