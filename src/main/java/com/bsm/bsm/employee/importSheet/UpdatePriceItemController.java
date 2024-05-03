package com.bsm.bsm.employee.importSheet;

import com.bsm.bsm.book.BookService;
import com.bsm.bsm.sheet.ImportSheetService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import com.bsm.bsm.book.BookBatch;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class UpdatePriceItemController {
    BigDecimal correctSellPrice = BigDecimal.ZERO;

    private final BookService bookService = new BookService();

    @FXML
    public TextField sellPriceField;
    @FXML
    public Label sellPriceLabel, title;
    @FXML
    public Text id, importPrice, currentSellPrice;


    BigDecimal getSellPriceValue() {
        BigDecimal sellPriceValue;
        try {
            sellPriceValue = new BigDecimal(sellPriceField.getText());
            if (sellPriceValue.compareTo(correctSellPrice) <= 0) {
                sellPriceLabel.setText("Must be more than 10% of import price");
                sellPriceValue = null;
            } else {
                sellPriceLabel.setText("");
            }
        } catch (NumberFormatException e) {
            if (sellPriceField.getText().isEmpty()) {
                sellPriceLabel.setText("Price cannot be empty");
                sellPriceValue = null;
            } else {
                sellPriceLabel.setText("Invalid price");
                sellPriceValue = null;
            }
            sellPriceValue = null;
        }
        return sellPriceValue;
    }


    @FXML
    void initialize() {
        sellPriceField.setFocusTraversable(false);
    }

    public void setBookBatch(BookBatch bookBatch) {
        id.setText(String.valueOf(bookBatch.getBook().getIsbn()));
        title.setText(bookBatch.getBook().getTitle());
        importPrice.setText(String.valueOf(bookBatch.getImportPrice()));

        correctSellPrice = bookBatch.getImportPrice().multiply(BigDecimal.valueOf(1.1));
        correctSellPrice = correctSellPrice.setScale(0, RoundingMode.UP);

        BigDecimal currentSellPriceString = bookService.getBookByName(bookBatch.getBook().getTitle()).getSalePrice();
        if (currentSellPriceString != null){
            currentSellPrice.setText(currentSellPriceString.toString());
        }
        else {
            currentSellPrice.setText("Not set");
        }
        sellPriceField.setPromptText("More than " + correctSellPrice.toString());

    }
}