package com.bsm.bsm.employee.importSheet;

import com.bsm.bsm.author.Author;
import com.bsm.bsm.book.Book;
import com.bsm.bsm.book.BookBatch;
import com.bsm.bsm.category.Category;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.util.Duration;

import java.util.List;

public class ItemImportController {
    @FXML
    public Label bookNameLabel, authorLabel, publisherLabel, qtyLabel, typeLabel, priceLabel;

    ImportSheetController importSheetController;

    void setImportSheetController(ImportSheetController thisImportSheetController) {
        importSheetController = thisImportSheetController;
    }

    @FXML
    private void initialize() {
    }

    @FXML
    private void handleRemoveButtonClick() {
        importSheetController.handleTableItemSelection(bookNameLabel.getText());
    }

    public void setBookBatch(BookBatch bookBatch) {
       g
    }
}
