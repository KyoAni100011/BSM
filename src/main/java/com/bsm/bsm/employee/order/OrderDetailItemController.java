package com.bsm.bsm.employee.order;

import com.bsm.bsm.order.OrderBooksDetails;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.util.Duration;

public class OrderDetailItemController {
    public Label titleField;
    public Label quantityField;
    public Label priceField;

    public void setItem(OrderBooksDetails orderBooksDetails) {
        Tooltip title = new Tooltip(String.valueOf(orderBooksDetails.getBookBatch().getBook().getTitle()));
        titleField.setTooltip(title);
        title.setShowDuration(Duration.seconds(10));
        title.setShowDelay(Duration.millis(2)); // Set the delay to 500 milliseconds (0.5 seconds)

        titleField.setText(String.valueOf(orderBooksDetails.getBookBatch().getBook().getTitle()));
        quantityField.setText(String.valueOf(orderBooksDetails.getQuantity()));
        priceField.setText(String.valueOf(orderBooksDetails.getSalePrice()));
    }
}
