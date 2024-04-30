package com.bsm.bsm.employee.order;

import com.bsm.bsm.order.OrderBooksDetails;
import javafx.scene.control.TextField;

public class OrderDetailItemController {
    public TextField titleField;
    public TextField quantityField;
    public TextField priceField;

    public void setItem(OrderBooksDetails orderBooksDetails) {
        titleField.setText(String.valueOf(orderBooksDetails.getBookBatch().getBook().getTitle()));
        quantityField.setText(String.valueOf(orderBooksDetails.getQuantity()));
        priceField.setText(String.valueOf(orderBooksDetails.getSalePrice()));
    }
}
