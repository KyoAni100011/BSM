package com.bsm.bsm.employee.book;

import com.bsm.bsm.author.Author;
import com.bsm.bsm.author.AuthorService;
import com.bsm.bsm.book.Book;
import com.bsm.bsm.book.BookService;
import com.bsm.bsm.category.Category;
import com.bsm.bsm.category.CategoryService;
import com.bsm.bsm.publisher.Publisher;
import com.bsm.bsm.publisher.PublisherService;
import com.bsm.bsm.utils.AlertUtils;
import com.bsm.bsm.utils.NumericValidationUtils;
import com.bsm.bsm.utils.ValidationUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.controlsfx.control.CheckComboBox;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class UpdateBookController {
    @FXML
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    @FXML
    public Label bookNameErrorLabel, languageErrorLabel, categoryErrorLabel, authorErrorLabel, quantityErrorLabel, publisherErrorLabel, priceErrorLabel, releaseErrorLabel;
    @FXML
    public TextField fullNameField, publisherNameField, bookQuantityField, bookPriceField;
    @FXML
    public CheckComboBox<String> authorNameCheckCombo, categoryCheckCombo;
    @FXML
    public ComboBox languageComboBox;
    @FXML
    public Button saveChangesButton;
    @FXML
    private DatePicker releaseDatePicker;
    Book book;
    private BookService bookService = new BookService();
    private CategoryService categoryService = new CategoryService();
    private AuthorService authorService = new AuthorService();
    private PublisherService publisherService = new PublisherService();

    // assume id book
    String bookID = "66661111";

    @FXML
    public void initialize() {
        book = bookService.getBookByISBN(bookID);
        ObservableList<String> categorieItems = FXCollections.observableArrayList();
        for (var item: categoryService.getAllCategories()) {
            categorieItems.add(item.getName());
        }

        ObservableList<String> authorItems = FXCollections.observableArrayList();
        for (var item: authorService.getAllAuthors()) {
            authorItems.add(item.getName());
        }

        ObservableList<String> languageItems = FXCollections.observableArrayList(
                "English", "French", "Vietnamese", "Japanese", "Korean", "Spanish", "German"
        );

        categoryCheckCombo.getItems().addAll(categorieItems);
        authorNameCheckCombo.getItems().addAll(authorItems);
        languageComboBox.setItems(languageItems);

        setBookInfo(book);
        setupDatePicker();
    }

    private void setBookInfo(Book thisBook) {
        fullNameField.setText(thisBook.getTitle());
        publisherNameField.setText(thisBook.getPublisher().getName());
        bookPriceField.setText(String.valueOf(thisBook.getSalePrice()));
        releaseDatePicker.setValue(LocalDate.now());
        bookQuantityField.setText(String.valueOf(thisBook.getQuantity()));
        authorNameCheckCombo.getCheckModel().checkIndices(0, 2, 4);
        categoryCheckCombo.getCheckModel().checkIndices(0, 2, 4);
        languageComboBox.setValue("Tieng Viet");
    }

    private void setupDatePicker() {
        releaseDatePicker.setPromptText("dd/mm/yyyy");

        releaseDatePicker.setConverter(new StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate date) {
                return date != null ? dateFormatter.format(date) : "";
            }

            @Override
            public LocalDate fromString(String string) {
                return string != null && !string.isEmpty() ? LocalDate.parse(string, dateFormatter) : null;
            }
        });

        releaseDatePicker.getEditor().addEventFilter(KeyEvent.KEY_TYPED, NumericValidationUtils.numericValidation(10));
    }

    private boolean validateInputs(String fullName, String release, String price, String publisher, String language, ObservableList<String> category, ObservableList<String> author, String quantity) {
        String bookNameError = ValidationUtils.validateFullName(fullName, "book");
        String publisherError = ValidationUtils.validateFullName(publisher, "publisher");
        String languageError = ValidationUtils.validateLanguage(language, "book");
        String categoryError = ValidationUtils.validateCategory(category, "book");
        String authorError = ValidationUtils.validateAuthor(author, "author");
        String quantityError = ValidationUtils.validateQuantity(quantity, "book");
        String priceError = ValidationUtils.validatePrice(price, "book");
        String releaseError = ValidationUtils.validateReleaseDay(release, "book");

        if (bookNameError != null) {
            bookNameErrorLabel.setText(bookNameError);
        }
        if (quantityError != null) {
            quantityErrorLabel.setText(quantityError);
        }
        if (releaseError != null) {
            releaseErrorLabel.setText(releaseError);
        }
        if (priceError != null) {
            priceErrorLabel.setText(priceError);
        }
        if (authorError != null) {
            authorErrorLabel.setText(authorError);
        }
        if (categoryError != null) {
            categoryErrorLabel.setText(categoryError);
        }
        if (publisherError != null) {
            publisherErrorLabel.setText(publisherError);
        }
        if (languageError != null) {
            languageErrorLabel.setText(languageError);
        }

        return bookNameError == null && quantityError == null && authorError == null && releaseError == null && languageError == null && publisherError == null && categoryError == null;
    }

    @FXML
    private void handleSaveChanges(ActionEvent event) {
        clearErrorMessages();
        String fullName = fullNameField.getText();
        String releaseDate = releaseDatePicker.getEditor().getText();
        String publisherName = publisherNameField.getText();
        String quantity = bookQuantityField.getText();
        String price = bookPriceField.getText();
        var selectedAuthor = authorNameCheckCombo.getCheckModel().getCheckedItems();
        var selectedCategory = categoryCheckCombo.getCheckModel().getCheckedItems();
        String selectedLanguage = (String) languageComboBox.getValue();

        if (validateInputs(fullName, releaseDate, price, publisherName, selectedLanguage, selectedCategory, selectedAuthor, quantity)) {
            // check sale price > import price * 1.1
            BigDecimal salePrice = new BigDecimal(price);
            if (!bookService.isSalePriceValid(book, salePrice)) {
                priceErrorLabel.setText("Sale price must be greater than import price 10%");
                return;
            }

            Publisher publiser = publisherService.getPublisherByName(publisherName);
            List<Category> categories = new ArrayList<>();
            for (var item: selectedCategory) {
                categories.add(categoryService.getCategoryByName(item));
            }

            List<Author> authors = new ArrayList<>();
            for (var item: selectedAuthor) {
                authors.add(authorService.getAuthorByName(item));
            }

            categories.forEach(System.out::println);
            authors.forEach(System.out::println);

//            int quantityInt = Integer.parseInt(quantity);
//            bookService.update(new Book(book.getIsbn(), fullName, publiser, releaseDate, selectedLanguage, true,
//                    quantityInt, salePrice, authors, categories));
        }
    }

    private void clearErrorMessages() {
        bookNameErrorLabel.setText("");
        languageErrorLabel.setText("");
        categoryErrorLabel.setText("");
        authorErrorLabel.setText("");
        quantityErrorLabel.setText("");
        publisherErrorLabel.setText("");
        priceErrorLabel.setText("");
        releaseErrorLabel.setText("");
    }

    private void clearInputs() {

    }
}
