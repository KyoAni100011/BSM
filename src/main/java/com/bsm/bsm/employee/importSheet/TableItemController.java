package com.bsm.bsm.employee.importSheet;


import com.bsm.bsm.sheet.ImportSheet;
import com.bsm.bsm.sheet.ImportSheetService;
import com.bsm.bsm.utils.AlertUtils;
import com.bsm.bsm.utils.FXMLLoaderHelper;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;


public class TableItemController {
    private final ImportSheetService importSheetService = new ImportSheetService();
    @FXML
    public ToggleButton toggleButton;
    public Label idLabel, dateImportLabel, employeeLabel, quantityLabel, totalPriceLabel;

    private String id;
    private ImportSheet sheetModel;

    @FXML
    private void initialize() {
        ViewSheetController.handleTableItemSelection(null);
    }

    @FXML
    void handleTableItemDoubleClick(MouseEvent event) throws IOException {
        if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
            if (id != null) {
                ImportSheetDetailController.handleTableItemSelection(id, sheetModel);
                FXMLLoaderHelper.loadFXML(new Stage(), "employee/importSheet/importSheetDetail");
            } else {
                AlertUtils.showAlert("Error", "Can't find user", Alert.AlertType.ERROR);

            }
        }
    }

    public void setSheetModel(ImportSheet sheet) {
        sheetModel = sheet;
        id = sheet.getId();
        idLabel.setText(sheet.getId());
        dateImportLabel.setText(sheet.getImportDate());
        employeeLabel.setText(sheet.getEmployee().getName());
        quantityLabel.setText(String.valueOf(sheet.getQuantity()));
        totalPriceLabel.setText(sheet.getTotalPrice().toString());
    }

}
