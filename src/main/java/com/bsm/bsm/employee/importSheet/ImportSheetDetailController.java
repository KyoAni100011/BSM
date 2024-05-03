package com.bsm.bsm.employee.importSheet;

import com.bsm.bsm.book.Book;
import com.bsm.bsm.sheet.ImportSheet;
import com.bsm.bsm.sheet.ImportSheetService;
import com.bsm.bsm.utils.NumericValidationUtils;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import javafx.util.StringConverter;

import java.io.IOException;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import static com.bsm.bsm.utils.DateUtils.convertDOBFormat;

public class ImportSheetDetailController {

    private static final ImportSheetService importSheetService = new ImportSheetService();
    public static List<Book> listBook = null;
    private static String id = "1";
    private static ImportSheet importSheet;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    @FXML
    public TextField employeeNameField;
    @FXML
    public TextField totalPricefield;
    @FXML
    public DatePicker importDatePicker;
    @FXML
    public TextField idField;
    @FXML
    public VBox bookItem;
    public Tooltip employeeTooltip;

    @FXML
    public void initialize() {
        new ImportSheetDetailController();
//

        importDatePicker.getEditor().setOpacity(1);
        setupDatePicker();

        setImportSheetInfo();
        updateSheet();
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
    static void handleTableItemSelection(String id, ImportSheet sheet) {
        ImportSheetDetailController.id = id;
        listBook = importSheetService.getISheetBookDetails(id);
        importSheet = sheet;
    }

    private void setImportSheetInfo() {

        idField.setText(importSheet.getId());
        employeeNameField.setText(importSheet.getEmployee().getName());



        totalPricefield.setText(String.valueOf(importSheet.getTotalPrice()));
        String date = convertDOBFormat(importSheet.getImportDate());
        importDatePicker.setValue(LocalDate.parse(date, dateFormatter));

    }

    private void updateSheet() {
        bookItem.getChildren().clear();
        for (Book b : listBook) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/bsm/bsm/view/employee/importSheet/importSheetDetailItem.fxml"));
                Node item = fxmlLoader.load();
                ImportSheetDetailItemController importSheetDetailItemController = fxmlLoader.getController();
                importSheetDetailItemController.setItem(b);
                bookItem.getChildren().add(item);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
