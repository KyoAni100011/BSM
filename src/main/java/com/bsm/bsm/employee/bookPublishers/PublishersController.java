package com.bsm.bsm.employee.bookPublishers;

import com.bsm.bsm.employee.EmployeeModel;
import com.bsm.bsm.employee.bookPublishers.TableItemController;
import com.bsm.bsm.employee.bookPublishers.UpdatePublisherController;
import com.bsm.bsm.publisher.Publisher;
import com.bsm.bsm.publisher.PublisherService;
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
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class PublishersController implements Initializable {
    private static String id;
    private final ToggleGroup toggleGroup = new ToggleGroup();
    private final EmployeeModel employeeInfo = (EmployeeModel) UserSingleton.getInstance().getUser();
    private final PublisherService publisherService = new PublisherService();

    private boolean isSearch = false;
    private boolean isSearchAndPagination = false;

    @FXML
    private TextField inputSearch;
    @FXML
    private VBox pnItems;
    @FXML
    private AnchorPane tableToolbar;
    @FXML
    private Button addPublisherButton, updatePublisherButton;
    @FXML
    private Button idLabel, nameLabel, introductionLabel, actionLabel;
    @FXML
    private SVGPath idSortLabel, nameSortLabel, introductionSortLabel, actionSortLabel;
    @FXML
    public Button previousPaginationButton, nextPaginationButton, firstPaginationButton, secondPaginationButton, thirdPaginationButton, fourthPaginationButton, fifthPaginationButton;


    private List<Publisher> publishers = null;
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
        loadAllPublishers();
        initializePaginationButtons();

        inputSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                isSearch = !newValue.isEmpty();
                inputSearchText = newValue;
                if(!isSearch) loadAllPublishers();
                else publishers = publisherService.search(inputSearchText);

                publishers = publisherService.sort(publishers, sortOrder.equals("ASC"), column);
                try {
                    updatePublishersList();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private void loadAllPublishers() {
        publishers = publisherService.getAllPublishers();
        employeeInfo.setPublishers(publishers);
        try {
            updatePublishersList();
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

        addPublisherButton.setOnAction(this::handleAddPublisherButton);
        updatePublisherButton.setOnAction(this::handleUpdatePublisherButton);
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
    void handleRefreshButton(ActionEvent event) {
        loadAllPublishers();
    }

    @FXML
    void handleAddPublisherButton(ActionEvent event) {
        try {
            FXMLLoaderHelper.loadFXML(new Stage(), "employee/bookPublishers/addPublisher", "Add Publisher");

            publishers = publisherService.getAllPublishers();
            updatePublishersList();
        } catch (IOException e) {
            e.printStackTrace();
            AlertUtils.showAlert("Error", "Error loading addUser FXML", Alert.AlertType.ERROR);
        }
    }

    @FXML
    void handleUpdatePublisherButton(ActionEvent event) {
        try {
            if (id != null) {
                UpdatePublisherController.handleTableItemSelection(id);
                FXMLLoaderHelper.loadFXML(new Stage(), "employee/bookPublishers/updatePublisher", "Update Publisher");
            } else {
                AlertUtils.showAlert("Error", "Can't find publisher", Alert.AlertType.ERROR);
            }
        } catch (IOException e) {
            e.printStackTrace();
            AlertUtils.showAlert("Error", "Error loading updatePublisher FXML", Alert.AlertType.ERROR);
        }
    }

    private void updatePublishersList() throws IOException {
        pnItems.getChildren().clear();
        int itemsPerPage = 9;
        int startIndex = isSearchAndPagination ? ((currentPage - 1) * itemsPerPage) : (isSearch ? 0 : (currentPage - 1) * itemsPerPage);
        int endIndex = Math.min(startIndex + itemsPerPage, publishers.size());

        for (int i = startIndex; i < endIndex; i++) {
            Publisher publisher = publishers.get(i);

            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/bsm/bsm/view/employee/bookPublishers/tableItem.fxml"));
                Node item = fxmlLoader.load();
                TableItemController tableItemController = fxmlLoader.getController();
                tableItemController.setToggleGroup(toggleGroup);
                tableItemController.setPublisherModel(publisher);
                pnItems.getChildren().add(item);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        int totalPages = (int) Math.ceil((double) publishers.size() / itemsPerPage);
        updatePaginationButtons(totalPages);
    }

    @FXML
    private void handlePaginationButton(ActionEvent event) {
        if(isSearch) isSearchAndPagination = true;
        Button buttonClicked = (Button) event.getSource();
        if (buttonClicked == previousPaginationButton) {
            currentPage--;
        } else if (buttonClicked == nextPaginationButton) {
            currentPage++;
        } else {
            currentPage = Integer.parseInt(buttonClicked.getText());
        }

        try {
            updatePublishersList();
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
    private void handleLabelClick(MouseEvent event){
        Button clickedLabel = (Button) event.getSource();
        String columnName = clickedLabel.getText().toLowerCase();

        column = column.equals("publisher") ? "name" : column;
        columnName = columnName.equals("publisher") ? "name" : columnName;

        if (columnName.equals(column)) {
            sortOrder = sortOrder.equals("ASC") ? "DESC" : "ASC";
        } else {
            sortOrder = "ASC";
        }
        var isAscending = sortOrder.equals("ASC");

        column = columnName;
        idSortLabel.setContent(column.equals("id") ? (isAscending ? "M233.4 105.4c12.5-12.5 32.8-12.5 45.3 0l192 192c12.5 12.5 12.5 32.8 0 45.3s-32.8 12.5-45.3 0L256 173.3 86.6 342.6c-12.5 12.5-32.8 12.5-45.3 0s-12.5-32.8 0-45.3l192-192z" : "M233.4 406.6c12.5 12.5 32.8 12.5 45.3 0l192-192c12.5-12.5 12.5-32.8 0-45.3s-32.8-12.5-45.3 0L256 338.7 86.6 169.4c-12.5-12.5-32.8-12.5-45.3 0s-12.5 32.8 0 45.3l192 192z") : "");
        nameSortLabel.setContent(column.equals("name") ? (isAscending ? "M233.4 105.4c12.5-12.5 32.8-12.5 45.3 0l192 192c12.5 12.5 12.5 32.8 0 45.3s-32.8 12.5-45.3 0L256 173.3 86.6 342.6c-12.5 12.5-32.8 12.5-45.3 0s-12.5-32.8 0-45.3l192-192z" : "M233.4 406.6c12.5 12.5 32.8 12.5 45.3 0l192-192c12.5-12.5 12.5-32.8 0-45.3s-32.8-12.5-45.3 0L256 338.7 86.6 169.4c-12.5-12.5-32.8-12.5-45.3 0s-12.5 32.8 0 45.3l192 192z") : "");
        introductionSortLabel.setContent(column.equals("address") ? (isAscending ? "M233.4 105.4c12.5-12.5 32.8-12.5 45.3 0l192 192c12.5 12.5 12.5 32.8 0 45.3s-32.8 12.5-45.3 0L256 173.3 86.6 342.6c-12.5 12.5-32.8 12.5-45.3 0s-12.5-32.8 0-45.3l192-192z" : "M233.4 406.6c12.5 12.5 32.8 12.5 45.3 0l192-192c12.5-12.5 12.5-32.8 0-45.3s-32.8-12.5-45.3 0L256 338.7 86.6 169.4c-12.5-12.5-32.8-12.5-45.3 0s-12.5 32.8 0 45.3l192 192z") : "");
        actionSortLabel.setContent(column.equals("enable/disable") ? (isAscending ? "M233.4 105.4c12.5-12.5 32.8-12.5 45.3 0l192 192c12.5 12.5 12.5 32.8 0 45.3s-32.8 12.5-45.3 0L256 173.3 86.6 342.6c-12.5 12.5-32.8 12.5-45.3 0s-12.5-32.8 0-45.3l192-192z" : "M233.4 406.6c12.5 12.5 32.8 12.5 45.3 0l192-192c12.5-12.5 12.5-32.8 0-45.3s-32.8-12.5-45.3 0L256 338.7 86.6 169.4c-12.5-12.5-32.8-12.5-45.3 0s-12.5 32.8 0 45.3l192 192z") : "");


        try {
            if (isSearch) {
                publishers = publisherService.search(inputSearchText);
            } else {
                publishers = publisherService.getAllPublishers();
            }
            publishers = publisherService.sort(publishers, isAscending, column);
            updatePublishersList();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
