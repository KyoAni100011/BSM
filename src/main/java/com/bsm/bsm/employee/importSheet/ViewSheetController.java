package com.bsm.bsm.employee.importSheet;

import com.bsm.bsm.employee.EmployeeModel;
import com.bsm.bsm.sheet.ImportSheet;
import com.bsm.bsm.sheet.ImportSheetService;
import com.bsm.bsm.user.UserSingleton;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ViewSheetController {
    private static String id;
    private final ToggleGroup toggleGroup = new ToggleGroup();
    private final ImportSheetService importSheetService = new ImportSheetService();
    private final EmployeeModel employeeInfo = (EmployeeModel) UserSingleton.getInstance().getUser();

    @FXML
    private TextField inputSearch;
    @FXML
    private VBox pnItems;
    @FXML
    private Button idLabel, dateImportLabel, employeeLabel, quantityLabel, totalPriceLabel;
    @FXML
    private SVGPath idSortLabel, dateImportSortLabel, employeeSortLabel, quantitySortLabel, totalPriceSortLabel;
    @FXML
    private Button previousPaginationButton, nextPaginationButton, firstPaginationButton, secondPaginationButton, thirdPaginationButton, fourthPaginationButton, fifthPaginationButton;
    private boolean isSearch = false;
    private List<ImportSheet> sheets;
    private String sortOrder = "ASC";
    private String column = "id";
    private int currentPage = 1;
    private String inputSearchText = "";

    static void handleTableItemSelection(String thisId) {
        id = thisId;
    }

    @FXML
    public void initialize() throws SQLException {
        sheets = generateDummyData();
        initializeButtonsAndLabels();
        loadAllSheets();
        initializePaginationButtons();

        inputSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                isSearch = !newValue.isEmpty();
                inputSearchText = newValue;
                if(!isSearch) {
                    try {
                        loadAllSheets();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
                else {
                    try {
                        sheets = importSheetService.search(inputSearchText);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
                try {
                    updateSheetsList();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private List<ImportSheet> generateDummyData() {
        List<ImportSheet> dummySheets = new ArrayList<>();
        for (int i = 1; i <= 50; i++) {
            ImportSheet sheet = new ImportSheet("ID" + i, employeeInfo, LocalDate.now().toString(), i, BigDecimal.valueOf(i * 1000));
            dummySheets.add(sheet);
        }
        return dummySheets;
    }

    private void loadAllSheets() throws SQLException {
        sheets = importSheetService.getAllSheets();
        employeeInfo.setImportSlips(sheets);
        try {
            updateSheetsList();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void initializeButtonsAndLabels() {
        idLabel.setOnMouseClicked(this::handleLabelClick);
        dateImportLabel.setOnMouseClicked(this::handleLabelClick);
        employeeLabel.setOnMouseClicked(this::handleLabelClick);
        quantityLabel.setOnMouseClicked(this::handleLabelClick);
        totalPriceLabel.setOnMouseClicked(this::handleLabelClick);

        idSortLabel.setContent("");
        dateImportSortLabel.setContent("");
        employeeSortLabel.setContent("");
        quantitySortLabel.setContent("");
        totalPriceSortLabel.setContent("");
    }

    private void initializePaginationButtons() {
        firstPaginationButton.setOnAction(this::handlePaginationButton);
        secondPaginationButton.setOnAction(this::handlePaginationButton);
        thirdPaginationButton.setOnAction(this::handlePaginationButton);
        fourthPaginationButton.setOnAction(this::handlePaginationButton);
        fifthPaginationButton.setOnAction(this::handlePaginationButton);
        previousPaginationButton.setOnAction(this::handlePaginationButton);
        nextPaginationButton.setOnAction(this::handlePaginationButton);
    }

    private void updateSheetsList() throws IOException {
        pnItems.getChildren().clear();
        int itemsPerPage = 10;
        int startIndex = isSearch ? 0 : (currentPage - 1) * itemsPerPage;
        int endIndex = Math.min(startIndex + itemsPerPage, sheets.size());

        for (int i = startIndex; i < endIndex; i++) {
            ImportSheet sheet = sheets.get(i);

            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/bsm/bsm/view/employee/importSheet/tableItem.fxml"));
                Node item = fxmlLoader.load();
                TableItemController tableItemController = fxmlLoader.getController();
                tableItemController.setSheetModel(sheet);
                pnItems.getChildren().add(item);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        int totalPages = (int) Math.ceil((double) sheets.size() / itemsPerPage);
        updatePaginationButtons(totalPages);
    }

    @FXML
    private void handlePaginationButton(ActionEvent event) {
        Button buttonClicked = (Button) event.getSource();
        if (buttonClicked == previousPaginationButton) {
            currentPage--;
        } else if (buttonClicked == nextPaginationButton) {
            currentPage++;
        } else {
            currentPage = Integer.parseInt(buttonClicked.getText());
        }

        try {
            updateSheetsList();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void updatePaginationButtons(int totalPages) {
        // Clear all pagination buttons visibility
        List<Button> paginationButtons = Arrays.asList(firstPaginationButton, secondPaginationButton, thirdPaginationButton, fourthPaginationButton, fifthPaginationButton);
        paginationButtons.forEach(button -> {
            button.setVisible(false);
            button.setManaged(false);
            button.getStyleClass().add("pagination-button-admin");
        });

        // Show pagination buttons based on the current page and total pages
        if (totalPages > 1) {
            int startPage = Math.max(1, Math.min(currentPage - 2, totalPages - 4));
            int endPage = Math.min(startPage + 4, totalPages);

            previousPaginationButton.setDisable(!(currentPage > 1));
            nextPaginationButton.setDisable(!(currentPage < totalPages));

            for (int i = startPage; i <= endPage; i++) {
                Button button;
                int buttonIndex = i - startPage;
                if (totalPages > 5 && startPage < 6) {
                    button = paginationButtons.get(buttonIndex);
                } else if (totalPages > 5) {
                    button = paginationButtons.get(buttonIndex + 1);
                } else {
                    button = paginationButtons.get(buttonIndex);
                }
                button.setText(String.valueOf(i));
                button.setManaged(true);
                button.setVisible(true);

                if (i == currentPage) {
                    button.setStyle("-fx-background-color: #f5a11c; -fx-text-fill: white;");
                } else {
                    button.setStyle(null);
                }
            }
        } else {
            previousPaginationButton.setDisable(true);
            firstPaginationButton.setText("1");
            firstPaginationButton.setVisible(true);
            firstPaginationButton.setManaged(true);
            firstPaginationButton.setStyle("-fx-background-color: #f5a11c; -fx-text-fill: white;");
            nextPaginationButton.setDisable(true);
        }
    }

    @FXML
    private void handleLabelClick(MouseEvent event) {
        Button clickedLabel = (Button) event.getSource();
        String columnName = clickedLabel.getText().toLowerCase();


        if (columnName.equals(column)) {
            sortOrder = sortOrder.equals("ASC") ? "DESC" : "ASC";
        } else {
            sortOrder = "ASC";
        }
        var isAscending = sortOrder.equals("ASC");

        column = columnName;
        idSortLabel.setContent(column.equals("id") ? (isAscending ? "M233.4 105.4c12.5-12.5 32.8-12.5 45.3 0l192 192c12.5 12.5 12.5 32.8 0 45.3s-32.8 12.5-45.3 0L256 173.3 86.6 342.6c-12.5 12.5-32.8 12.5-45.3 0s-12.5-32.8 0-45.3l192-192z" : "M233.4 406.6c12.5 12.5 32.8 12.5 45.3 0l192-192c12.5-12.5 12.5-32.8 0-45.3s-32.8-12.5-45.3 0L256 338.7 86.6 169.4c-12.5-12.5-32.8-12.5-45.3 0s-12.5 32.8 0 45.3l192 192z") : "");
        dateImportSortLabel.setContent(column.equals("date import") ? (isAscending ? "M233.4 105.4c12.5-12.5 32.8-12.5 45.3 0l192 192c12.5 12.5 12.5 32.8 0 45.3s-32.8 12.5-45.3 0L256 173.3 86.6 342.6c-12.5 12.5-32.8 12.5-45.3 0s-12.5-32.8 0-45.3l192-192z" : "M233.4 406.6c12.5 12.5 32.8 12.5 45.3 0l192-192c12.5-12.5 12.5-32.8 0-45.3s-32.8-12.5-45.3 0L256 338.7 86.6 169.4c-12.5-12.5-32.8-12.5-45.3 0s-12.5 32.8 0 45.3l192 192z") : "");
        employeeSortLabel.setContent(column.equals("employee") ? (isAscending ? "M233.4 105.4c12.5-12.5 32.8-12.5 45.3 0l192 192c12.5 12.5 12.5 32.8 0 45.3s-32.8 12.5-45.3 0L256 173.3 86.6 342.6c-12.5 12.5-32.8 12.5-45.3 0s-12.5-32.8 0-45.3l192-192z" : "M233.4 406.6c12.5 12.5 32.8 12.5 45.3 0l192-192c12.5-12.5 12.5-32.8 0-45.3s-32.8-12.5-45.3 0L256 338.7 86.6 169.4c-12.5-12.5-32.8-12.5-45.3 0s-12.5 32.8 0 45.3l192 192z") : "");
        quantitySortLabel.setContent(column.equals("quantity") ? (isAscending ? "M233.4 105.4c12.5-12.5 32.8-12.5 45.3 0l192 192c12.5 12.5 12.5 32.8 0 45.3s-32.8 12.5-45.3 0L256 173.3 86.6 342.6c-12.5 12.5-32.8 12.5-45.3 0s-12.5-32.8 0-45.3l192-192z" : "M233.4 406.6c12.5 12.5 32.8 12.5 45.3 0l192-192c12.5-12.5 12.5-32.8 0-45.3s-32.8-12.5-45.3 0L256 338.7 86.6 169.4c-12.5-12.5-32.8-12.5-45.3 0s-12.5 32.8 0 45.3l192 192z") : "");
        totalPriceSortLabel.setContent(column.equals("total price") ? (isAscending ? "M233.4 105.4c12.5-12.5 32.8-12.5 45.3 0l192 192c12.5 12.5 12.5 32.8 0 45.3s-32.8 12.5-45.3 0L256 173.3 86.6 342.6c-12.5 12.5-32.8 12.5-45.3 0s-12.5-32.8 0-45.3l192-192z" : "M233.4 406.6c12.5 12.5 32.8 12.5 45.3 0l192-192c12.5-12.5 12.5-32.8 0-45.3s-32.8-12.5-45.3 0L256 338.7 86.6 169.4c-12.5-12.5-32.8-12.5-45.3 0s-12.5 32.8 0 45.3l192 192z") : "");

        try {
//            sheets = importSheetService.getAllSheets();
//            sheets = importSheetService.sort(sheets, isAscending, column);
            updateSheetsList();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}