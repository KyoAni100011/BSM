package com.bsm.bsm.employee.importSheet;

import com.bsm.bsm.book.BookService;
import com.bsm.bsm.sheet.ImportSheetService;
import com.bsm.bsm.utils.NumericValidationUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.text.Text;
import com.bsm.bsm.book.BookBatch;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

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
                sellPriceField.setText("");
                sellPriceValue = null;
            } else {
                sellPriceLabel.setText("");
            }
        } catch (NumberFormatException e) {
            if (sellPriceField.getText().isEmpty()) {
                sellPriceLabel.setText("Price cannot be empty");
                sellPriceField.setText("");
            } else {
                sellPriceLabel.setText("Invalid price");
                sellPriceField.setText("");
            }
            sellPriceValue = null;
        }
        return sellPriceValue;
    }


    @FXML
    void initialize() {
        sellPriceField.setFocusTraversable(false);
        sellPriceField.setTextFormatter(new TextFormatter<>(NumericValidationUtils.integerFilter));
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