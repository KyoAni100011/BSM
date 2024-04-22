package com.bsm.bsm.employee.order;

import com.bsm.bsm.order.Order;
import com.bsm.bsm.order.ToggleSwitch;
import com.bsm.bsm.employee.order.OrderController;
import com.bsm.bsm.utils.AlertUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class TableItemController {
    public Label idLabel, customerNameLabel, EmployeeNameLabel, OrderDateLabel,PriceLabel;

//    private final OrderService orderService = new OrderService();
    @FXML
    public ToggleButton toogleButton;
    @FXML
    public ToggleSwitch isOn;
    @FXML
    private String isbn;
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
            if (isbn != null) {
//                OrderDetailController.handleTableItemSelection(isbn);
//                FXMLLoaderHelper.loadFXML(new Stage(), "employee/order/orderDetail");
            } else {
                AlertUtils.showAlert("Error", "Can't find order", Alert.AlertType.ERROR);

            }
        }
    }

    public void setOrder(Order thisOrder) {
//        System.out.println("have"+thisOrder.toString());
//        order = thisOrder;
//        isbn = String.valueOf(thisOrder.getId());
//
//        idLabel.setText(String.valueOf(thisOrder.getId()));
//
//        customerNameLabel.setText(String.valueOf(thisOrder.getCustomerID()));
//        EmployeeNameLabel.setText(String.valueOf(thisOrder.getEmployeeID()));
//        OrderDateLabel.setText(String.valueOf(thisOrder.getOrderDate()));
//        PriceLabel.setText(String.valueOf(thisOrder.getTotalPrice()));
    }
}
