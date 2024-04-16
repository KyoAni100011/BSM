package com.bsm.bsm.employee.importSheet;

import com.bsm.bsm.author.Author;
import com.bsm.bsm.book.Book;
import com.bsm.bsm.book.BookBatch;
import com.bsm.bsm.category.Category;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.List;

public class ItemImportController {
    @FXML
    public Label bookNameLabel, authorLabel, publisherLabel, qtyLabel, typeLabel, priceLabel;

    @FXML
    private void initialize() {
        ImportSheetController.handleTableItemSelection(null);
    }

    @FXML
    private void handleRemoveButtonClick() {
        ImportSheetController.handleTableItemSelection(bookNameLabel.getText());
    }

    public void setBookBatch(BookBatch bookBatch) {
        bookNameLabel.setText(bookBatch.getBook().getTitle());

        List<Author> authors = bookBatch.getBook().getAuthors();
        StringBuilder authorsName = new StringBuilder();
        for (var author: authors) {
            authorsName.append(author.getName().trim()).append(", ");
        }
        authorLabel.setText(authorsName.toString());

        publisherLabel.setText(bookBatch.getBook().getPublisher().getName());
        qtyLabel.setText(String.valueOf(bookBatch.getQuantity()));

        List<Category> categories = bookBatch.getBook().getCategories();
        StringBuilder categoriesName = new StringBuilder();
        for (var category: categories) {
            categoriesName.append(category.getName().trim()).append(", ");
        }
        typeLabel.setText(categoriesName.toString());

        priceLabel.setText(String.valueOf(bookBatch.getImportPrice()));
    }
}
