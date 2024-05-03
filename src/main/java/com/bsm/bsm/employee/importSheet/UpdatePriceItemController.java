package com.bsm.bsm.employee.importSheet;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import com.bsm.bsm.book.BookBatch;

import java.math.BigDecimal;

public class UpdatePriceItemController {
    BigDecimal correctSellPrice = BigDecimal.ZERO;

    @FXML
    public TextField sellPriceField;
    @FXML
    public Label sellPriceLabel;
    @FXML
    public Text id, title, importPrice, currentSellPrice;

    BigDecimal getSellPriceValue() {
        BigDecimal sellPriceValue = BigDecimal.ZERO;
        try {
            sellPriceValue = new BigDecimal(sellPriceField.getText());
            if (sellPriceValue.compareTo(correctSellPrice.multiply(BigDecimal.valueOf(1.1))) <= 0) {
                sellPriceLabel.setText("At least >= 10% import price");
            } else {
                sellPriceLabel.setText("");
            }
        } catch (NumberFormatException e) {
            if (sellPriceField.getText().isEmpty()) {
                sellPriceLabel.setText("Price cannot be empty");
            } else {
                sellPriceLabel.setText("Invalid price");
            }
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

        if (bookBatch.getBook().getSalePrice() != null) {
            currentSellPrice.setText(String.valueOf(bookBatch.getBook().getSalePrice()));
        }
        else {
            currentSellPrice.setText("Not set");
        }
        sellPriceField.setPromptText("Should be: " + correctSellPrice.toString());
    }
}
