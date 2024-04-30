package com.bsm.bsm.employee.importSheet;

import com.bsm.bsm.sheet.BookSheetDetail;
import javafx.scene.control.TextField;

public class ImportSheetDetailItemController {
    public TextField titleField;
    public TextField quantityField;
    public TextField priceField;

    public void setItem(BookSheetDetail sheet) {
        titleField.setText(sheet.getTitle());
        quantityField.setText(String.valueOf(sheet.getQuantity()) );
        priceField.setText(String.valueOf(sheet.getPrice()) );
    }
}
