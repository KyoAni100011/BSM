package com.bsm.bsm.employee.importSheet;

import com.bsm.bsm.book.BookBatch;
import com.bsm.bsm.book.BookService;
import com.bsm.bsm.sheet.ImportSheetDAO;
import com.bsm.bsm.sheet.ImportSheetService;
import com.bsm.bsm.utils.AlertUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UpdatePriceController {
    private static List<BookBatch> bookBatches;
    private final BookService bookService = new BookService();
    @FXML
    public VBox pnItems;
    private final List<UpdatePriceItemController> updatePriceItemControllers = new ArrayList<>();
    private final ImportSheetService importSheetService = new ImportSheetService();

    static void setBookBatches(List<BookBatch> bookBatchesIn) {
        bookBatches = bookBatchesIn;
    }

    @FXML
    void initialize() {
        updateBookList();
    }

    private void updateBookList() {
        pnItems.getChildren().clear();
        for (BookBatch bookBatch : bookBatches) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/bsm/bsm/view/employee/importSheet/updatePriceItem.fxml"));
                Node item = fxmlLoader.load();
                pnItems.getChildren().add(item);
                updatePriceItemControllers.add(fxmlLoader.getController());
                updatePriceItemControllers.get(updatePriceItemControllers.size() - 1).setBookBatch(bookBatch);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }


    public void handleSaveChanges(ActionEvent event) {
        boolean isValid = true;

        for (int i = 0; i < updatePriceItemControllers.size(); i++) {
            UpdatePriceItemController updatePriceItemController = updatePriceItemControllers.get(i);
            BigDecimal sellPriceValue = updatePriceItemController.getSellPriceValue();

            if (sellPriceValue == null) {
                isValid = false;
                break;
            }

            bookBatches.get(i).getBook().setSalePrice(sellPriceValue);
            System.out.println(bookBatches.get(i).getBook().getTitle() + " " + bookBatches.get(i).getBook().getSalePrice());
        }

        if (isValid) {
            if (importSheetService.updateSalePrice(bookBatches)) {
                AlertUtils.showAlert("Success", "Update all sell prices successfully", Alert.AlertType.INFORMATION);
            } else {
                AlertUtils.showAlert("Error", "Update sell price failed", Alert.AlertType.ERROR);
            }
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();
        }
    }

}
