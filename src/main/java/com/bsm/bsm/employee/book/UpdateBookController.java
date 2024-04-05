package com.bsm.bsm.employee.book;
import org.controlsfx.control.CheckComboBox;
import javafx.collections.ObservableList;
import com.bsm.bsm.utils.AlertUtils;
import com.bsm.bsm.utils.NumericValidationUtils;
import com.bsm.bsm.utils.ValidationUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.controlsfx.control.CheckComboBox;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.controlsfx.control.textfield.TextFields;

public class UpdateBookController {
    @FXML
    public Label bookNameErrorLabel,languageErrorLabel,categoryErrorLabel,authorErrorLabel,quantityErrorLabel,publisherErrorLabel,priceErrorLabel,releaseErrorLabel;
    @FXML
    public TextField fullNameField, publisherNameField,bookQuantityField, bookPriceField;
    @FXML
    public CheckComboBox<String> authorNameCheckCombo , categoryCheckCombo;
    @FXML
    public ComboBox languageComboBox;
    @FXML
    public Button saveChangesButton;
    @FXML
    private DatePicker releaseDatePicker;

    @FXML
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @FXML
    public void initialize() {
        ObservableList<String> items = FXCollections.observableArrayList(
                "Item 1", "Item 2", "Item 3", "Item 4", "Item 5"
        );
        categoryCheckCombo.getItems().addAll(items);
        authorNameCheckCombo.getItems().addAll(items);
        languageComboBox.setItems(items);
        setupDatePicker();
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

    private boolean validateInputs(String fullName,String release , String price, String publisher, String language, ObservableList<String> category,ObservableList<String> author, String quantity) {
        String bookNameError = ValidationUtils.validateFullName(fullName, "book");
        String publisherError = ValidationUtils.validateFullName(publisher,"publisher");
        String languageError = ValidationUtils.validateLanguage(language,"book");
        String categoryError = ValidationUtils.validateCategory(category,"book");
        String authorError = ValidationUtils.validateAuthor(author,"author");
        String quantityError = ValidationUtils.validateQuantity(quantity,"book");
        String priceError = ValidationUtils.validatePrice(price,"book");
        String releaseError = ValidationUtils.validateReleaseDay(release,"book");

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
        ObservableList<String> selectedAuthor = authorNameCheckCombo.getCheckModel().getCheckedItems();
        ObservableList<String> selectedCategory = categoryCheckCombo.getCheckModel().getCheckedItems();
        String selectedLanguage =  (String) languageComboBox.getValue();

        if (validateInputs(fullName, releaseDate, price, publisherName,selectedLanguage,selectedCategory,selectedAuthor,quantity)) {
            System.out.println("hi");
            // need to call ID
//            String id = userModel.getId();
//            if (accountService.updateUserProfile(id, fullName, phone, dob, address)){
//                AlertUtils.showAlert("Success", "Profile updated successfully.", Alert.AlertType.INFORMATION);
//                updateUserInformation(fullName, phone, dob, address);
//                clearInputs();
//                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//                // Close the stage
//                stage.close();
//            } else {
//                AlertUtils.showAlert("Error", "Profile update failed.", Alert.AlertType.ERROR);
//            }
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
