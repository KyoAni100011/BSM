package com.bsm.bsm.employee.importSheet;

import com.bsm.bsm.book.BookBatch;
import com.bsm.bsm.book.BookService;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private List<BookBatch> bookBatches;

    private ImportSheetService importSheetService = new ImportSheetService();
    private BookService bookService = new BookService();

    void handleTableItemSelection(String thisBookName) {
        if (thisBookName == null) {
            return;
        }
        bookBatches.removeIf(bookBatch -> bookBatch.getBook().getTitle().equals(thisBookName));
        try {
            updateBookList();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    public void initialize() {
        setupDatePicker();
        clearInputs();
        clearErrorMessages();
        importDatePicker.setValue(LocalDate.now());
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
    public void handleAddBookButton() {
        try {
            AddBookBatchController controller = FXMLLoaderHelper.loadFXMLWithController(new Stage(),"/com/bsm/bsm/view/employee/importSheet/addBook.fxml", "Add Book Batch To Import Sheet");
            controller.setImportSheetController(this);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    boolean setBookBatch(BookBatch bookBatch) {
        if (bookBatches.stream().anyMatch(b -> b.getBook().getTitle().equals(bookBatch.getBook().getTitle()))) {
            return false;
        }
        bookBatches.add(bookBatch);
        try {
            updateBookList();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }


    private void updateBookList() throws Exception {
        pnItems.getChildren().clear();
        for (BookBatch b : bookBatches) {
            System.out.println(b);
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/bsm/bsm/view/employee/importSheet/itemImport.fxml"));
                Node item = fxmlLoader.load();
                ItemImportController tableItemController = fxmlLoader.getController();
                tableItemController.setBookBatch(b);
                tableItemController.setImportSheetController(this);
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

            EmployeeModel employee = (EmployeeModel) UserSingleton.getInstance().getUser();
            String importDateConverted = DateUtils.formatDOB(importDate);
            Map<BookBatch, Integer> importBooks = new HashMap<>();
            for (BookBatch bookBatch : bookBatches) {
                importBooks.put(bookBatch, bookBatch.getQuantity());
            }

            ImportSheet importSheet = new ImportSheet(employee, importDateConverted, totalQuantity, new BigDecimal(totalCost));
            importSheet.setImportBooks(importBooks);


            if (importSheetService.createImportSheet(importSheet)) {
                String importSheetID = importSheetService.getImportSheetID(importSheet);
                importSheet.setId(importSheetID);

                ImportSheetDetailController.handleTableItemSelection(importSheetID, importSheet);
                Stage s = new Stage();
                FXMLLoaderHelper.loadFXML(s, "employee/importSheet/importSheetDetail");
                ImportSheetDetailController.setStage(s);

                if (!bookBatches.isEmpty()) {
                    UpdatePriceController.setBookBatches(bookBatches);
                }

                clearInputs();
                bookBatches = new ArrayList<>();
                importSheet.setImportBooks(null);
                setupDatePicker();
                importDatePicker.setValue(LocalDate.now());
                updateBookList();
            }
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
