package com.bsm.bsm.employee.importSheet;

import com.bsm.bsm.book.Book;
import com.bsm.bsm.category.Category;
import com.bsm.bsm.employee.book.TableItemController;
import com.bsm.bsm.employee.bookCategories.CategoryDetailController;
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
    private static String id ="1";
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private static ImportSheet importSheet ;

    public static List<Book> listBook = null;
    private static final ImportSheetService importSheetService =  new ImportSheetService();

    @FXML
    public void initialize() {
        new ImportSheetDetailController();
        setImportSheetInfo();
        updateSheet();
    }
    static void handleTableItemSelection(String myId, ImportSheet sheet) {
        id = myId;
        listBook = importSheetService.getISheetBookDetails(id);
        importSheet = sheet;
    }
    private void setImportSheetInfo() {
        idField.setText(importSheet.getId());
        employeeNameField.setText(importSheet.getEmployee().getName());
        totalPricefield.setText(String.valueOf(importSheet.getTotalPrice()));
        importDatePicker.setValue(LocalDate.parse(importSheet.getImportDate(), dateFormatter));
    }
    private void updateSheet(){
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
