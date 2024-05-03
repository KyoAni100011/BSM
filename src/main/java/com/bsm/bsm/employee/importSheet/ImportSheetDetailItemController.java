package com.bsm.bsm.employee.importSheet;

import com.bsm.bsm.book.Book;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class ImportSheetDetailItemController {


    public Label isbnField;
    public Label titleField;
    public Label priceField;
    public Label quantityField;

    public void setItem(Book sheet) {
        Tooltip title = new Tooltip(sheet.getTitle());
        titleField.setTooltip(title);
        title.setShowDuration(Duration.seconds(10));
        title.setShowDelay(Duration.millis(2)); // Set the delay to 500 milliseconds (0.5 seconds)


        titleField.setText(sheet.getTitle());
        quantityField.setText(String.valueOf(sheet.getQuantity()) );
        priceField.setText(String.valueOf(sheet.getSalePrice()) );
        isbnField.setText(String.valueOf(sheet.getIsbn()));
    }
}
