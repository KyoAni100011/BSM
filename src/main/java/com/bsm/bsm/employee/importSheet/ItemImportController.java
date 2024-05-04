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
        bookNameLabel.setText(bookBatch.getBook().getTitle());

        List<Author> authors = bookBatch.getBook().getAuthors();
        StringBuilder authorsName = new StringBuilder();
        for (var author: authors) {
            authorsName.append(author.getName().trim()).append(", ");
        }
        authorsName.deleteCharAt(authorsName.length() - 2);
        authorLabel.setText(authors.size() == 1 ? authors.getFirst().getName() : authors.getFirst().getName() + "...");

        publisherLabel.setText(bookBatch.getBook().getPublisher().getName());
        qtyLabel.setText(String.valueOf(bookBatch.getQuantity()));

        List<Category> categories = bookBatch.getBook().getCategories();
        StringBuilder categoriesName = new StringBuilder();
        for (var category: categories) {
            categoriesName.append(category.getName().trim()).append(", ");
        }
        categoriesName.deleteCharAt(categoriesName.length() - 2);
        typeLabel.setText(categories.size() == 1 ? categories.getFirst().getName() : categories.getFirst().getName() + "...");

        priceLabel.setText(String.valueOf(bookBatch.getImportPrice()));

        Tooltip title = new Tooltip(bookBatch.getBook().getTitle());
        bookNameLabel.setTooltip(title);
        title.setShowDuration(Duration.seconds(10));
        title.setShowDelay(Duration.millis(2)); // Set the delay to 500 milliseconds (0.5 seconds)

        Tooltip author = new Tooltip(authorsName.toString());
        authorLabel.setTooltip(author);
        author.setShowDuration(Duration.seconds(10));
        author.setShowDelay(Duration.millis(2)); // Set the delay to 500 milliseconds (0.5 seconds)

        Tooltip publisher = new Tooltip(bookBatch.getBook().getPublisher().getName());
        publisherLabel.setTooltip(publisher);
        publisher.setShowDuration(Duration.seconds(10));
        publisher.setShowDelay(Duration.millis(2)); // Set the delay to 500 milliseconds (0.5 seconds)

        Tooltip type = new Tooltip(categoriesName.toString());
        typeLabel.setTooltip(type);
        type.setShowDuration(Duration.seconds(10));
        type.setShowDelay(Duration.millis(2)); // Set the delay to 500 milliseconds (0.5 seconds)

    }
}
