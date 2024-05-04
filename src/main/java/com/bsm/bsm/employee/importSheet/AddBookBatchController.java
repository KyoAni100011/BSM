package com.bsm.bsm.employee.importSheet;

import com.bsm.bsm.author.Author;
import com.bsm.bsm.author.AuthorService;
import com.bsm.bsm.book.Book;
import com.bsm.bsm.book.BookBatch;
import com.bsm.bsm.book.BookBatchService;
import com.bsm.bsm.book.BookService;
import com.bsm.bsm.category.Category;
import com.bsm.bsm.category.CategoryService;
import com.bsm.bsm.publisher.Publisher;
import com.bsm.bsm.publisher.PublisherService;
import com.bsm.bsm.utils.DateUtils;
import com.bsm.bsm.utils.NumericValidationUtils;
import com.bsm.bsm.utils.ValidationUtils;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.controlsfx.control.CheckComboBox;
import org.controlsfx.control.SearchableComboBox;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public class AddBookBatchController {
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    @FXML
    public Label bookNameErrorLabel,languageErrorLabel,categoryErrorLabel,authorErrorLabel,quantityErrorLabel,publisherErrorLabel,priceErrorLabel,releaseErrorLabel;
    @FXML
    public TextField  bookQuantityField, bookPriceField, bookNameField;
    @FXML
    public CheckComboBox<String> authorNameCheckCombo , categoryCheckCombo;
    @FXML
    public SearchableComboBox languageComboBox, publisherComboBox;
    @FXML
    public Button saveChangesButton;
    public ScrollPane scrollPanelBook;
    public VBox itemBook;
    @FXML
    private DatePicker releaseDatePicker;
    @FXML
    private TextField categorySearch, authorSearch;


    BookBatch bookBatch;
    private BookService bookService = new BookService();
    private CategoryService categoryService = new CategoryService();
    private AuthorService authorService = new AuthorService();
    private PublisherService publisherService = new PublisherService();
    private BookBatchService bookBatchService = new BookBatchService();

    private Set<String> selectedLanguages = new HashSet<>();
    private ObservableList<String> selectedAuthors = FXCollections.observableArrayList();
    private ObservableList<String> selectedCategories = FXCollections.observableArrayList();
    private ObservableList<String> selectedBooks = FXCollections.observableArrayList();
    ObservableList<String> authorItems = FXCollections.observableArrayList();
    ObservableList<String> categoriesItems = FXCollections.observableArrayList();
    ObservableList<String> publisherItems = FXCollections.observableArrayList();
    ObservableList<String> bookItems = FXCollections.observableArrayList();
    private Book bookDetails = null;

    private ObservableList<String> filteredCategoriesItems = FXCollections.observableArrayList(); // Store filtered category items
    private ObservableList<String> filteredAuthorItems = FXCollections.observableArrayList(); // Store filtered category items
    private ObservableList<String> filteredBookItems = FXCollections.observableArrayList();

    // Khai báo biến kiểm tra số nguyên
    private final UnaryOperator<TextFormatter.Change> integerFilter = change -> {
        // Kiểm tra nếu chuỗi mới chứa chỉ chứa các ký tự số hoặc là chuỗi rỗng
        if (Pattern.matches("\\d*", change.getControlNewText())) {
            return change;
        } else {
            return null; // Nếu không phải là số, chặn việc nhập
        }
    };

    ImportSheetController importSheetController;

    void setImportSheetController(ImportSheetController thisImportSheetController) {
        importSheetController = thisImportSheetController;
    }

    @FXML
    public void initialize() {
        bookQuantityField.setTextFormatter(new TextFormatter<>(integerFilter));
        bookPriceField.setTextFormatter(new TextFormatter<>(integerFilter));
        for (var category: categoryService.display()) {
            if (category.isEnabled())
                categoriesItems.add(category.getName());
        }

        for (var author: authorService.display()) {
            if (author.isEnabled())
                authorItems.add(author.getName());
        }

        for (var publisher: publisherService.display()) {
            if (publisher.isEnabled())
                publisherItems.add(publisher.getName());
        }
        itemBook.getChildren().clear();
        for (var book: bookService.getAllBooks()) {
            if (book.isEnabled()){
                bookItems.add(book.getTitle());
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/bsm/bsm/view/employee/importSheet/itemSearch.fxml"));
                    Node no = fxmlLoader.load();
                    ItemSearch a = fxmlLoader.getController();
                    a.addName(book.getTitle(), this);
                    itemBook.getChildren().add(no);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }


        }

        List<String> languages = bookService.getLanguages();
        ObservableList<String> languageItems = FXCollections.observableArrayList(languages);

        categoryCheckCombo.getItems().addAll(categoriesItems);
        authorNameCheckCombo.getItems().addAll(authorItems);
        languageComboBox.setItems(languageItems);
        publisherComboBox.getItems().addAll(publisherItems);
        setupDatePicker();
        bookNameField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue){
                scrollPanelBook.setVisible(true);
            }
        });
        bookNameField.textProperty().addListener((observable, oldValue, newValue) -> {
            int i = 0;
            itemBook.getChildren().clear();
            clearInputs();
            for (String book : bookItems) {
                if (newValue == null || newValue.isEmpty()) {
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/bsm/bsm/view/employee/importSheet/itemSearch.fxml"));
                        Node no = fxmlLoader.load();
                        ItemSearch a = fxmlLoader.getController();
                        a.addName(book, this);
                        itemBook.getChildren().add(no);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else if (book.toLowerCase().contains(newValue.toLowerCase())) {
                    try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/bsm/bsm/view/employee/importSheet/itemSearch.fxml"));
                    Node no = fxmlLoader.load();
                    ItemSearch a = fxmlLoader.getController();
                    a.addName(book, this);
                    itemBook.getChildren().add(no);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                // You can perform any action you want with each item here
            }
        });

        bookNameField.getParent().addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
            // Check if the mouse event target is outside the bookNameField
            if (!bookNameField.getBoundsInParent().contains(event.getX(), event.getY())) {
                // Mouse is pressed outside the bookNameField
                if (bookNameField.isFocused()) {
                    bookNameField.getParent().requestFocus();

                }
            }
        });
        scrollPanelBook.getParent().addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
            // Check if the mouse event target is outside the bookNameField
            if (!scrollPanelBook.getBoundsInParent().contains(event.getX(), event.getY())) {
                // Mouse is pressed outside the bookNameField
                scrollPanelBook.setVisible(false);

            }
        });
        categorySearch.setVisible(false);
        authorSearch.setVisible(false);

        categoryCheckCombo.addEventHandler(ComboBox.ON_SHOWN, event -> {
            categorySearch.setVisible(true);

        });
        categoryCheckCombo.addEventHandler(ComboBox.ON_HIDDEN, event -> {
            categorySearch.focusedProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue) {
                    // Author search field is focused
                    if (!isPopupOpen(categoryCheckCombo)) {
                        categoryCheckCombo.show();
                    }
                } else {
                    // Author search field lost focus
                    if (!isPopupOpen(categoryCheckCombo)) {
                        categorySearch.setVisible(false);
                    }
                }
            });
        });

        categorySearch.textProperty().addListener((observable, oldValue, newValue) -> {
            categoryCheckCombo.show();
            try {
                updateFilteredCategories(newValue.toLowerCase());
            } catch (IndexOutOfBoundsException e) {

                System.err.println("IndexOutOfBoundsException caught: " + e.getMessage());
            }
        });
        categorySearch.getParent().addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
            // Check if the mouse event target is outside the categorySearch field
            if (!categorySearch.getBoundsInParent().contains(event.getX(), event.getY())) {
                categorySearch.clear();
                categorySearch.setVisible(false); // Hide the categorySearch field
                categoryCheckCombo.hide();
            }
        });



        categoryCheckCombo.getCheckModel().getCheckedItems().addListener((ListChangeListener<String>) c -> {
            while (c.next()) {
                if (c.wasAdded()) {
                    for (String item : c.getAddedSubList()) {
                        if (!selectedCategories.contains(item) && item != null) {
                            selectedCategories.add(item);
                        }
                    }
                }
                if (c.wasRemoved()) {
                    for (String item : c.getRemoved()) {
                        selectedCategories.remove(item);
                    }
                }
            }
        });

        authorNameCheckCombo.addEventHandler(ComboBox.ON_SHOWN, event -> {
            authorSearch.setVisible(true);
        });

        authorNameCheckCombo.addEventHandler(ComboBox.ON_HIDDEN, event -> {
            authorSearch.focusedProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue) {
                    // Author search field is focused
                    if (!isPopupOpen(authorNameCheckCombo)) {
                        authorNameCheckCombo.show();
                    }
                } else {
                    // Author search field lost focus
                    if (!isPopupOpen(authorNameCheckCombo)) {
                        authorSearch.setVisible(false);
                    }
                }
            });
        });

        authorSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            authorNameCheckCombo.show();
            try {
                updateFilteredAuthor(newValue.toLowerCase());
            } catch (IndexOutOfBoundsException e) {

                System.err.println("IndexOutOfBoundsException caught: " + e.getMessage());
            }


        });
        authorSearch.getParent().addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
            // Check if the mouse event target is outside the authorSearch field
            if (!authorSearch.getBoundsInParent().contains(event.getX(), event.getY())) {
                authorSearch.clear();
                // Mouse is pressed outside the authorSearch field
                authorSearch.setVisible(false); // Hide the authorSearch field
                authorNameCheckCombo.hide();
            }
        });

        authorNameCheckCombo.getCheckModel().getCheckedItems().addListener((ListChangeListener<String>) c -> {
            while (c.next()) {
                if (c.wasAdded()) {
                    for (String item : c.getAddedSubList()) {
                        if (!selectedAuthors.contains(item) && item != null) {
                            selectedAuthors.add(item);
                        }
                    }
                }
                if (c.wasRemoved()) {
                    for (String item : c.getRemoved()) {
                        selectedAuthors.remove(item);
                    }
                }
            }
        });


    }

    void handleChoiceAction(String title){
        bookNameField.setText(title);
        scrollPanelBook.setVisible(false);

        bookDetails = bookService.getBookByName(title);

        List<Author> authors = bookDetails.getAuthors();
        List<Category> categories = bookDetails.getCategories();
        String publishers = bookDetails.getPublisher().getName();
        String language = bookDetails.getLanguages();
        int quanity = bookDetails.getQuantity();
        BigDecimal salePrice = bookDetails.getSalePrice();
        String releaseDate = bookDetails.getPublishingDate();

        authorNameCheckCombo.getCheckModel().clearChecks();
        categoryCheckCombo.getCheckModel().clearChecks();


        for (Author author : authors) {
            if (authorNameCheckCombo.getItems().contains(author.getName())) {
                authorNameCheckCombo.getCheckModel().check(author.getName());
            }
        }

        for (Category category : categories) {
            if (categoryCheckCombo.getItems().contains(category.getName())) {
                categoryCheckCombo.getCheckModel().check(category.getName());
            }
        }

        publisherComboBox.setValue(publishers);
        languageComboBox.setValue(language);

        releaseDatePicker.setValue(LocalDate.parse(releaseDate, dateFormatter));

    }

    private void updateFilteredCategories(String searchQuery) {
        filteredCategoriesItems.clear();

        if (searchQuery.isEmpty()) {
            filteredCategoriesItems.addAll(categoriesItems);
        } else {
            filteredCategoriesItems.addAll(selectedCategories);
            for (String category : categoriesItems) {
                if (category.toLowerCase().contains(searchQuery)) {
                    if (!selectedCategories.contains(category)) {
                        filteredCategoriesItems.add(category);
                    }
                }
            }

        }

        categoryCheckCombo.getItems().setAll(filteredCategoriesItems);

        Set<String> previouslySelectedCategories = new HashSet<>(selectedCategories);

        try {
            categoryCheckCombo.getCheckModel().clearChecks();
        } catch (IndexOutOfBoundsException e) {
            System.err.println("IndexOutOfBoundsException caught: " + e.getMessage());
        }

        for (String filteredCategory : filteredCategoriesItems) {
            if (previouslySelectedCategories.contains(filteredCategory)) {
                categoryCheckCombo.getCheckModel().check(filteredCategory);
                System.out.println("checked" + filteredCategory);
            }
        }
    }

    private void updateFilteredAuthor(String searchQuery) {
        filteredAuthorItems.clear();

        if (searchQuery.isEmpty()) {
            filteredAuthorItems.addAll(authorItems);
        } else {
            filteredAuthorItems.addAll(selectedAuthors);
            for (String author : authorItems) {
                if (author.toLowerCase().contains(searchQuery)) {
                    if (!selectedAuthors.contains(author)) {
                        filteredAuthorItems.add(author);
                    }
                }
            }

        }

        authorNameCheckCombo.getItems().setAll(filteredAuthorItems);

        Set<String> previouslySelectedAuthor = new HashSet<>(selectedAuthors);

        try {
            authorNameCheckCombo.getCheckModel().clearChecks();
        } catch (IndexOutOfBoundsException e) {
            System.err.println("IndexOutOfBoundsException caught: " + e.getMessage());
        }

        for (String filtererAuthor : filteredAuthorItems) {
            if (previouslySelectedAuthor.contains(filtererAuthor)) {
                authorNameCheckCombo.getCheckModel().check(filtererAuthor);
                System.out.println("checked" + filtererAuthor);
            }
        }
    }

    private boolean isPopupOpen(CheckComboBox<?> checkComboBox) {
        // Get the popup window of the CheckComboBox
        Node popup = checkComboBox.getSkin().getNode().lookup(".combo-box-popup");

        // Return true if the popup is not null and visible, false otherwise
        return popup != null && popup.isVisible();
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
        String bookNameError = ValidationUtils.validateBookName(fullName);
        String publisherError = ValidationUtils.validatePublisher(publisher, "publisher");
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

        String title =  bookNameField.getText();
        String releaseDate = releaseDatePicker.getEditor().getText();
        String publisherName = (String)publisherComboBox.getValue();
        String quantity = bookQuantityField.getText();
        String importPrice = bookPriceField.getText();
        var selectedAuthor = authorNameCheckCombo.getCheckModel().getCheckedItems();
        var selectedCategory = categoryCheckCombo.getCheckModel().getCheckedItems();
        String selectedLanguage = (String) languageComboBox.getValue();

        if (validateInputs(title, releaseDate, importPrice, publisherName, selectedLanguage, selectedCategory, selectedAuthor, quantity)) {
            Publisher publisher = publisherService.getPublisherByName(publisherName);
            List<Category> categories = new ArrayList<>();
            for (var category: selectedCategory) {
                categories.add(categoryService.getCategoryByName(category));
            }

            List<Author> authors = new ArrayList<>();
            for (var author: selectedAuthor) {
                authors.add(authorService.getAuthorByName(author));
            }

            var publishingDateConverted = DateUtils.formatDOB(releaseDate);

            Book book = new Book(title, publisher, publishingDateConverted, selectedLanguage, authors, categories);
            BookBatch bookBatch = new BookBatch(Integer.parseInt(quantity), new BigDecimal(importPrice), book);
            importSheetController.setBookBatch(bookBatch);

            // Close the current stage
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();

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
        bookQuantityField.setText("");
        bookPriceField.setText("");
        languageComboBox.setValue(null);
        publisherComboBox.setValue(null);
        categoryCheckCombo.getCheckModel().clearChecks();
        releaseDatePicker.setValue(null);
        authorNameCheckCombo.getCheckModel().clearChecks();

    }
}