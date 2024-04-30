package com.bsm.bsm.employee.order;

import com.bsm.bsm.order.OrderBooksDetails;
import javafx.scene.control.TextField;

public class OrderDetailItemController {
    public TextField titleField;
    public TextField quantityField;
    public TextField priceField;
    public void setItem(OrderBooksDetails order) {
        titleField.setText(order.getOrderID());
        quantityField.setText(String.valueOf(order.getQuantity()) );
        priceField.setText(String.valueOf(order.getSalePrice()) );
    }
}
