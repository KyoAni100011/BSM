package com.bsm.bsm.employee.importSheet;

import com.bsm.bsm.book.Book;
import com.bsm.bsm.utils.AlertUtils;
import javafx.event.ActionEvent;
import com.bsm.bsm.utils.ValidationUtils;
import com.bsm.bsm.utils.FXMLLoaderHelper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.text.ParseException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ImportSheetController {
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private final ToggleGroup toggleGroup = new ToggleGroup();

    @FXML
    public VBox pnItems;
    @FXML
    public Button bookNameLabel, authorLabel, publisherLabel, qtyLabel, typeLabel, priceLabel;
    @FXML
    public Button addBookButton;
    @FXML
    public DatePicker importDatePicker;
    @FXML
    public TextField totalCostTextField;
    @FXML
    public Label totalCostErrorLabel, importDateErrorLabel;
    @FXML
    public Button btnAddSheet;

    private static List<Book> books;
    public Button refreshButton;

    static void handleTableItemSelection(String thisBookName) {
        if (thisBookName == null) {
            return;
        }
        books.removeIf(book -> book.getTitle().equals(thisBookName));
    }

    static void addBookToSheet(Book book) {
        books.add(book);
    }


    @FXML
    public void initialize() {
        books = new ArrayList<>();
        try {
            updateBookList();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void handleRefreshButton(ActionEvent event) {
        try {
            updateBookList();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    @FXML
    public void handleAddBookButton() {
        try {
            FXMLLoaderHelper.loadFXML(new Stage(),"employee/importSheet/addBook");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void updateBookList() throws Exception {
        pnItems.getChildren().clear();
        for (Book b : books) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/bsm/bsm/view/employee/importSheet/itemImport.fxml"));
                Node item = fxmlLoader.load();
                ItemImportController tableItemController = fxmlLoader.getController();
                tableItemController.setBook(b);
                pnItems.getChildren().add(item);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        long totalCost = books.stream().mapToLong(book -> book.getSalePrice().longValue() * book.getQuantity()).sum();
        totalCostTextField.setText(String.valueOf(totalCost));
    }

    @FXML
    public void handleAddSheetButton(ActionEvent event) {
        clearErrorMessages();
        String importDate = importDatePicker.getEditor().getText();
        String totalCost = totalCostTextField.getText();

        if (validateInputs(importDate, totalCost)) {
            // Add import sheet to database
            // Add books to database
            // Clear inputs
            if (books.isEmpty()) {
                AlertUtils.showAlert("Error", "Please add books to import sheet.", Alert.AlertType.ERROR);
                return;
            }
            AlertUtils.showAlert("Success", "Import sheet successfully.", Alert.AlertType.INFORMATION);
            clearInputs();
            books = new ArrayList<>();
        }
        else {
            AlertUtils.showAlert("Error", "Import sheet failed.", Alert.AlertType.ERROR);
        }

    }

    private boolean validateInputs(String importDate, String totalCost) {
        String importDateError = ValidationUtils.validateImportDay(importDate);
        String totalCostError = ValidationUtils.validatePrice(totalCost, "book");

        if (importDateError != null) {
            importDateErrorLabel.setText(importDateError);
        }
        if (totalCostError != null) {
            totalCostErrorLabel.setText(totalCostError);
        }

        return importDateError == null && totalCostError == null;
    }

    private void clearErrorMessages() {
        totalCostErrorLabel.setText("");
        importDateErrorLabel.setText("");
    }

    private void clearInputs() {
        totalCostTextField.setText("");
        importDatePicker.getEditor().setText("");
    }
}
