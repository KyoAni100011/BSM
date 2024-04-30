package com.bsm.bsm.employee.importSheet;

import com.bsm.bsm.sheet.BookSheetDetail;
import com.bsm.bsm.sheet.ImportSheet;
import com.bsm.bsm.sheet.ImportSheetService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ImportSheetDetailController {

    private static final ImportSheetService importSheetService = new ImportSheetService();
    public static List<BookSheetDetail> listBook = null;
    private static String id = "1";
    private static ImportSheet importSheet;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
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

    static void handleTableItemSelection(String myId, ImportSheet sheet) {
        id = myId;
        listBook = importSheetService.getISheetBookDetails(id);
        importSheet = sheet;
    }

    @FXML
    public void initialize() {
        new ImportSheetDetailController();
        setImportSheetInfo();
        updateSheet();
    }

    private void setImportSheetInfo() {
        idField.setText(importSheet.getId());
        employeeNameField.setText(importSheet.getEmployee().getName());
        totalPricefield.setText(String.valueOf(importSheet.getTotalPrice()));
        importDatePicker.setValue(LocalDate.parse(importSheet.getImportDate(), dateFormatter));
    }

    private void updateSheet() {
        bookItem.getChildren().clear();
        for (BookSheetDetail b : listBook) {
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
