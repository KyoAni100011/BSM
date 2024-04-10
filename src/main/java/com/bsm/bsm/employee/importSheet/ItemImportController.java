package com.bsm.bsm.employee.importSheet;

import com.bsm.bsm.author.Author;
import com.bsm.bsm.book.Book;
import com.bsm.bsm.category.Category;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;

import java.util.List;

public class ItemImportController {
    @FXML
    public Label bookNameLabel, authorLabel, publisherLabel, qtyLabel, typeLabel, priceLabel;

    private String bookName;

    @FXML
    private void initialize() {
        ImportSheetController.handleTableItemSelection(null);
    }

    @FXML
    private void handleRemoveButtonClick() {
        ImportSheetController.handleTableItemSelection(bookName);
        System.out.println(bookName);
    }

    public void setBook(Book book) {
        bookName = book.getTitle();
        bookNameLabel.setText(bookName);

        List<Author> authors = book.getAuthors();
        StringBuilder authorNames = new StringBuilder();
        for (Author author : authors) {
            authorNames.append(author.getName()).append(", ");
        }
        authorLabel.setText(authorNames.toString());

        publisherLabel.setText(book.getPublisher().getName());

        qtyLabel.setText(String.valueOf(book.getQuantity()));

        List<Category> categories = book.getCategories();
        StringBuilder categoryNames = new StringBuilder();
        for (Category category : categories) {
            categoryNames.append(category.getName()).append(", ");
        }
        typeLabel.setText(categoryNames.toString());

        priceLabel.setText(String.valueOf(book.getSalePrice()));
    }
}
