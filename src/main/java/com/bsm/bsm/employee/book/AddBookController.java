package com.bsm.bsm.employee.book;

import com.bsm.bsm.admin.userAccount.UserDetailController;
import com.bsm.bsm.author.Author;
import com.bsm.bsm.author.AuthorService;
import com.bsm.bsm.book.Book;
import com.bsm.bsm.book.BookService;
import com.bsm.bsm.category.Category;
import com.bsm.bsm.category.CategoryService;
import com.bsm.bsm.publisher.Publisher;
import com.bsm.bsm.publisher.PublisherService;
import com.bsm.bsm.utils.AlertUtils;
import com.bsm.bsm.utils.FXMLLoaderHelper;
import com.bsm.bsm.utils.NumericValidationUtils;
import com.bsm.bsm.utils.ValidationUtils;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
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

public class AddBookController {
    @FXML
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    @FXML
    public Label bookNameErrorLabel,languageErrorLabel,categoryErrorLabel,authorErrorLabel,quantityErrorLabel,publisherErrorLabel,priceErrorLabel,releaseErrorLabel;
    @FXML
    public TextField fullNameField, bookQuantityField, bookPriceField;
    @FXML
    public CheckComboBox<String> authorNameCheckCombo , categoryCheckCombo;
    @FXML
    public SearchableComboBox languageComboBox, publisherComboBox;
    @FXML
    public Button saveChangesButton;
    @FXML
    private DatePicker releaseDatePicker;
    @FXML
    private TextField categorySearch, authorSearch;

    Book book;
    private BookService bookService = new BookService();
    private CategoryService categoryService = new CategoryService();
    private AuthorService authorService = new AuthorService();
    private PublisherService publisherService = new PublisherService();
    private Set<String> selectedLanguages = new HashSet<>();
    private ObservableList<String> selectedAuthors = FXCollections.observableArrayList();
    private ObservableList<String> selectedCategories = FXCollections.observableArrayList();
    ObservableList<String> authorItems = FXCollections.observableArrayList();
    ObservableList<String> categoriesItems = FXCollections.observableArrayList();
    ObservableList<String> publisherItems = FXCollections.observableArrayList();

    private ObservableList<String> filteredCategoriesItems = FXCollections.observableArrayList(); // Store filtered category items
    private ObservableList<String> filteredAuthorItems = FXCollections.observableArrayList(); // Store filtered category items

    @FXML
    public void initialize() {
        for (var item: categoryService.getAllCategories()) {
            // check category is enabled
            if (item.isEnabled())
                categoriesItems.add(item.getName());
        }

        for (var item : authorService.getAllAuthors()) {
            if (item.isEnabled())
                // check category is enabled
                authorItems.add(item.getName());
        }

        for (var item: publisherService.getAllPublishers()) {
            if (item.isEnabled())
                // check category is enabled
                publisherItems.add(item.getName());
        }


        List<String> languages = bookService.getLanguages();
        ObservableList<String> languageItems = FXCollections.observableArrayList(languages);

        categoryCheckCombo.getItems().addAll(categoriesItems);
        authorNameCheckCombo.getItems().addAll(authorItems);
        languageComboBox.setItems(languageItems);
        publisherComboBox.getItems().addAll(publisherItems);
        setupDatePicker();

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
                // Mouse is pressed outside the categorySearch field
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
//this

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

    private boolean validateInputs(String fullName, String release, String publisher, String language, ObservableList<String> category, ObservableList<String> author) {
        String bookNameError = ValidationUtils.validateFullName(fullName, "book");
        String publisherError = ValidationUtils.validatePublisher(publisher, "publisher");
        String languageError = ValidationUtils.validateLanguage(language, "book");
        String categoryError = ValidationUtils.validateCategory(category, "book");
        String authorError = ValidationUtils.validateAuthor(author, "author");
        String releaseError = ValidationUtils.validateReleaseDay(release, "book");

        if (bookNameError != null) {
            bookNameErrorLabel.setText(bookNameError);
        }
        if (releaseError != null) {
            releaseErrorLabel.setText(releaseError);
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

        return bookNameError == null && authorError == null && releaseError == null && languageError == null && publisherError == null && categoryError == null;
    }

    @FXML
    private void handleSaveChanges(ActionEvent event) throws IOException {
        clearErrorMessages();

        String fullName = fullNameField.getText();
        String releaseDate = releaseDatePicker.getEditor().getText();
        String publisherName = (String)publisherComboBox.getValue();
        var selectedAuthor = authorNameCheckCombo.getCheckModel().getCheckedItems();
        var selectedCategory = categoryCheckCombo.getCheckModel().getCheckedItems();
        String selectedLanguage = (String) languageComboBox.getValue();

        if (validateInputs(fullName, releaseDate, publisherName, selectedLanguage, selectedCategory, selectedAuthor)) {

            //check name exist
            if (bookService.isNameExist(fullName)) {
                bookNameErrorLabel.setText("Book name already exists.");
                return;
            }


            Publisher publisers = publisherService.getPublisherByName(publisherName);
            List<Category> categories = new ArrayList<>();
            for (var item: selectedCategory) {
                categories.add(categoryService.getCategoryByName(item));
            }

            List<Author> authors = new ArrayList<>();
            for (var item: selectedAuthor) {
                authors.add(authorService.getAuthorByName(item));
            }

//            categories.forEach(System.out::println);
//            authors.forEach(System.out::println);

            if (bookService.add(new Book(fullName, publisers, releaseDate, selectedLanguage, authors, categories))) {
                AlertUtils.showAlert("Success", "Book added successfully.", Alert.AlertType.INFORMATION);
                clearInputs();
                Book a = bookService.getBookByName(fullName);
                BookDetailController.handleTableItemSelection(a.getIsbn());
                FXMLLoaderHelper.loadFXML(new Stage(), "employee/book/bookDetail");
                closeWindow(event);
            } else {
                AlertUtils.showAlert("Error", "Book add failed.", Alert.AlertType.ERROR);
            }
        }
    }

    private void closeWindow(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
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
        fullNameField.clear();
        bookQuantityField.clear();
        bookPriceField.clear();
        releaseDatePicker.getEditor().clear();
        languageComboBox.getSelectionModel().clearSelection();
        publisherComboBox.getSelectionModel().clearSelection();
        authorNameCheckCombo.getCheckModel().clearChecks();
        categoryCheckCombo.getCheckModel().clearChecks();
    }
}
