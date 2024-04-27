package com.bsm.bsm.employee.importSheet;

import com.bsm.bsm.book.BookBatch;
import com.bsm.bsm.employee.EmployeeModel;
import com.bsm.bsm.sheet.ImportSheet;
import com.bsm.bsm.sheet.ImportSheetService;
import com.bsm.bsm.user.UserSingleton;
import com.bsm.bsm.utils.AlertUtils;
import com.bsm.bsm.utils.DateUtils;
import com.bsm.bsm.utils.NumericValidationUtils;
import javafx.event.ActionEvent;
import com.bsm.bsm.utils.ValidationUtils;
import com.bsm.bsm.utils.FXMLLoaderHelper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDate;
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
    public TextField totalCostTextField, totalQuantityTextField;
    @FXML
    public Label totalCostErrorLabel, importDateErrorLabel, totalQuantityErrorLabel;
    @FXML
    public Button btnAddSheet;

    private static List<BookBatch> bookBatches;
    public Button refreshButton;

    private ImportSheetService importSheetService = new ImportSheetService();

    static void handleTableItemSelection(String thisBookName) {
        if (thisBookName == null) {
            return;
        }
        bookBatches.removeIf(bookBatch -> bookBatch.getBook().getTitle().equals(thisBookName));
    }

    @FXML
    public void initialize() {
        setupDatePicker();
        importDatePicker.setValue(LocalDate.now());
        clearErrorMessages();
        clearInputs();
        bookBatches = new ArrayList<>();
        try {
            updateBookList();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void setupDatePicker() {
        importDatePicker.setPromptText("dd/mm/yyyy");

        importDatePicker.setConverter(new StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate date) {
                return date != null ? dateFormatter.format(date) : "";
            }

            @Override
            public LocalDate fromString(String string) {
                return string != null && !string.isEmpty() ? LocalDate.parse(string, dateFormatter) : null;
            }
        });

        importDatePicker.getEditor().addEventFilter(KeyEvent.KEY_TYPED, NumericValidationUtils.numericValidation(10));
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
            FXMLLoaderHelper.loadFXML(new Stage(),"employee/importSheet/addBook", "Add Book");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    static void addBookBatchToSheet(BookBatch bookBatch) {
        bookBatches.add(bookBatch);
    }

    private void updateBookList() throws Exception {
        pnItems.getChildren().clear();
        for (BookBatch b : bookBatches) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/bsm/bsm/view/employee/importSheet/itemImport.fxml"));
                Node item = fxmlLoader.load();
                ItemImportController tableItemController = fxmlLoader.getController();
                tableItemController.setBookBatch(b);
                pnItems.getChildren().add(item);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        long totalCost = bookBatches.stream().mapToLong(bookBatch -> bookBatch.getImportPrice().longValue() * bookBatch.getQuantity()).sum();
        totalCostTextField.setText(String.valueOf(totalCost));
        long totalQuantity = bookBatches.stream().mapToLong(BookBatch::getQuantity).sum();
        totalQuantityTextField.setText(String.valueOf(totalQuantity));
    }

    @FXML
    public void handleAddSheetButton(ActionEvent event) throws Exception {
        clearErrorMessages();
        String importDate = importDatePicker.getEditor().getText();
        String totalCost = totalCostTextField.getText();

        int totalQuantity = 0;
        for (BookBatch bookBatch : bookBatches) {
            totalQuantity += bookBatch.getQuantity();
        }

        if (validateInputs(importDate, totalCost)) {
            if (bookBatches.isEmpty()) {
                AlertUtils.showAlert("Error", "Please add books to import sheet.", Alert.AlertType.ERROR);
                return;
            }

            bookBatches.forEach(System.out::println);
            EmployeeModel employee = (EmployeeModel) UserSingleton.getInstance().getUser();
            String importDateConverted = DateUtils.formatDOB(importDate);
            ImportSheet importSheet = new ImportSheet(employee, importDateConverted, totalQuantity, new BigDecimal(totalCost));
            if (importSheetService.createImportSheet(importSheet, bookBatches)) {
                System.out.println("ok");
            }

            AlertUtils.showAlert("Success", "Import sheet successfully.", Alert.AlertType.INFORMATION);

            // Clear inputs
            clearInputs();
            bookBatches = new ArrayList<>();
            updateBookList();
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
        totalQuantityErrorLabel.setText("");
    }

    private void clearInputs() {
        totalCostTextField.setText("");
        importDatePicker.getEditor().setText("");
        totalQuantityTextField.setText("");
    }
}
