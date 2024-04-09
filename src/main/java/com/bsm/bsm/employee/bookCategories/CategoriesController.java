package com.bsm.bsm.employee.bookCategories;

import com.bsm.bsm.employee.EmployeeModel;
import com.bsm.bsm.category.Category;
import com.bsm.bsm.user.UserSingleton;
import com.bsm.bsm.utils.AlertUtils;
import com.bsm.bsm.utils.FXMLLoaderHelper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import com.bsm.bsm.category.CategoryService;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class CategoriesController implements Initializable {
    private static String id;
    private final ToggleGroup toggleGroup = new ToggleGroup();

    private final EmployeeModel employeeInfo = (EmployeeModel) UserSingleton.getInstance().getUser();
    private final CategoryService categoryService = new CategoryService();

    @FXML
    private TextField inputSearch;
    @FXML
    private VBox pnItems;
    @FXML
    private AnchorPane tableToolbar;
    @FXML
    private Button addCategoryButton, updateCategoryButton;
    @FXML
    public Button previousPaginationButton, nextPaginationButton, firstPaginationButton, secondPaginationButton, thirdPaginationButton, fourthPaginationButton, fifthPaginationButton;
    @FXML
    public Button idLabel, nameLabel, introductionLabel, actionLabel;
    @FXML
    private SVGPath idSortLabel, nameSortLabel, introductionSortLabel ,actionSortLabel;

    private List<Category> categories = null;
    private String sortOrder = "ASC";
    private String column = "id";
    private int currentPage = 1;
    private String inputSearchText = "";

    static void handleTableItemSelection(String userId) {
        id = userId;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeButtonsAndLabels();
        loadAllCategory();
        initializePaginationButtons();

        inputSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                inputSearchText = newValue;
                categories = categoryService.search(inputSearchText);
                try {
                    updateCategoryList();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private void loadAllCategory() {
        categories = categoryService.getAllCategories();
        employeeInfo.setCategories(categories);
        try {
            updateCategoryList();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void initializeButtonsAndLabels() {
        idLabel.setOnMouseClicked(this::handleLabelClick);
        nameLabel.setOnMouseClicked(this::handleLabelClick);
        introductionLabel.setOnMouseClicked(this::handleLabelClick);
        actionLabel.setOnMouseClicked(this::handleLabelClick);

        idSortLabel.setContent("");
        nameSortLabel.setContent("");
        introductionSortLabel.setContent("");
        actionSortLabel.setContent("");

        addCategoryButton.setOnAction(this::handleAddCategoryButton);
        updateCategoryButton.setOnAction(this::handleUpdateCategoryButton);
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

    @FXML
    void handleAddCategoryButton(ActionEvent event) {
        try {
            FXMLLoaderHelper.loadFXML(new Stage(), "employee/bookCategories/addCategory");
            //update categories list after adding new category
            categories = categoryService.getAllCategories();
            updateCategoryList();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void handleUpdateCategoryButton(ActionEvent event) {
        try {
            if (id != null) {
                UpdateCategoryController.handleTableItemSelection(id);
                FXMLLoaderHelper.loadFXML(new Stage(), "employee/bookCategories/updateCategory");
            } else {
                AlertUtils.showAlert("Error", "Can't find category", Alert.AlertType.ERROR);
            }
        } catch (IOException e) {
            AlertUtils.showAlert("Error", "Error loading update Category FXML", Alert.AlertType.ERROR);
        }
    }

    @FXML
    void handleRefreshButton(ActionEvent event) {
        loadAllCategory();
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
            updateCategoryList();
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
                    button.setStyle("-fx-background-color: #914d2a; -fx-text-fill: white;");
                } else {
                    button.setStyle(null);
                }
            }
        } else {
            previousPaginationButton.setDisable(true);
            firstPaginationButton.setText("1");
            firstPaginationButton.setVisible(true);
            firstPaginationButton.setManaged(true);
            firstPaginationButton.setStyle("-fx-background-color: #914d2a; -fx-text-fill: white;");
            nextPaginationButton.setDisable(true);
        }
    }

    private void updateCategoryList() throws IOException {
        pnItems.getChildren().clear();
        int itemsPerPage = 10;
        int startIndex = (currentPage - 1) * itemsPerPage;
        int endIndex = Math.min(startIndex + itemsPerPage, categories.size());

        for (int i = startIndex; i < endIndex; i++) {
            Category category = categories.get(i);

            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/bsm/bsm/view/employee/bookCategories/tableItem.fxml"));
                Node item = fxmlLoader.load();
                TableItemController tableItemController = fxmlLoader.getController();
                tableItemController.setToggleGroup(toggleGroup);
                tableItemController.setCategoryModel(category);
                pnItems.getChildren().add(item);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        int totalPages = (int) Math.ceil((double) categories.size() / itemsPerPage);
        updatePaginationButtons(totalPages);
    }

    @FXML
    private void handleLabelClick(MouseEvent event) {
        Button clickedLabel = (Button) event.getSource();
        String columnName = clickedLabel.getText().toLowerCase();

        column = column.equals("category") ? "name" : column;
        columnName = columnName.equals("category") ? "name" : columnName;

        System.out.println(sortOrder);

        if (columnName.equals(column)) {
            sortOrder = sortOrder.equals("ASC") ? "DESC" : "ASC";
        } else {
            sortOrder = "ASC";
        }
        var isAscending = sortOrder.equals("ASC");

        column = columnName;
        idSortLabel.setContent(column.equals("id") ? (isAscending ? "M233.4 105.4c12.5-12.5 32.8-12.5 45.3 0l192 192c12.5 12.5 12.5 32.8 0 45.3s-32.8 12.5-45.3 0L256 173.3 86.6 342.6c-12.5 12.5-32.8 12.5-45.3 0s-12.5-32.8 0-45.3l192-192z" : "M233.4 406.6c12.5 12.5 32.8 12.5 45.3 0l192-192c12.5-12.5 12.5-32.8 0-45.3s-32.8-12.5-45.3 0L256 338.7 86.6 169.4c-12.5-12.5-32.8-12.5-45.3 0s-12.5 32.8 0 45.3l192 192z") : "");
        nameSortLabel.setContent(column.equals("name") ? (isAscending ? "M233.4 105.4c12.5-12.5 32.8-12.5 45.3 0l192 192c12.5 12.5 12.5 32.8 0 45.3s-32.8 12.5-45.3 0L256 173.3 86.6 342.6c-12.5 12.5-32.8 12.5-45.3 0s-12.5-32.8 0-45.3l192-192z" : "M233.4 406.6c12.5 12.5 32.8 12.5 45.3 0l192-192c12.5-12.5 12.5-32.8 0-45.3s-32.8-12.5-45.3 0L256 338.7 86.6 169.4c-12.5-12.5-32.8-12.5-45.3 0s-12.5 32.8 0 45.3l192 192z") : "");
        introductionSortLabel.setContent(column.equals("description") ? (isAscending ? "M233.4 105.4c12.5-12.5 32.8-12.5 45.3 0l192 192c12.5 12.5 12.5 32.8 0 45.3s-32.8 12.5-45.3 0L256 173.3 86.6 342.6c-12.5 12.5-32.8 12.5-45.3 0s-12.5-32.8 0-45.3l192-192z" : "M233.4 406.6c12.5 12.5 32.8 12.5 45.3 0l192-192c12.5-12.5 12.5-32.8 0-45.3s-32.8-12.5-45.3 0L256 338.7 86.6 169.4c-12.5-12.5-32.8-12.5-45.3 0s-12.5 32.8 0 45.3l192 192z") : "");
        actionSortLabel.setContent(column.equals("action") ? (isAscending ? "M233.4 105.4c12.5-12.5 32.8-12.5 45.3 0l192 192c12.5 12.5 12.5 32.8 0 45.3s-32.8 12.5-45.3 0L256 173.3 86.6 342.6c-12.5 12.5-32.8 12.5-45.3 0s-12.5-32.8 0-45.3l192-192z" : "M233.4 406.6c12.5 12.5 32.8 12.5 45.3 0l192-192c12.5-12.5 12.5-32.8 0-45.3s-32.8-12.5-45.3 0L256 338.7 86.6 169.4c-12.5-12.5-32.8-12.5-45.3 0s-12.5 32.8 0 45.3l192 192z") : "");


        try {
            categories = categoryService.sort(categories, isAscending, column);
            updateCategoryList();
            categories.forEach(System.out::println);
            System.out.println("-".repeat(30));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
