package com.bsm.bsm.publisher;

import com.bsm.bsm.author.Author;
import com.bsm.bsm.author.AuthorService;
import com.bsm.bsm.employee.bookAuthors.TableItemController;
import com.bsm.bsm.employee.bookAuthors.UpdateAuthorController;
import com.bsm.bsm.utils.AlertUtils;
import com.bsm.bsm.utils.FXMLLoaderHelper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
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
import java.util.ResourceBundle;

public class bookPublisher implements Initializable {

    @FXML
    private Button actionLabel;

    @FXML
    private SVGPath actionSortLabel;

    @FXML
    private Button addPublisherButton;

    @FXML
    private Button addressLabel;

    @FXML
    private Button fifthPaginationButton;

    @FXML
    private Button firstPaginationButton;

    @FXML
    private Button fourthPaginationButton;

    @FXML
    private Button idLabel;

    @FXML
    private SVGPath idSortLabel;

    @FXML
    private TextField inputSearch;

    @FXML
    private Button nameLabel;

    @FXML
    private SVGPath nameSortLabel;

    @FXML
    private Button nextPaginationButton;

    @FXML
    private VBox pnItems;

    @FXML
    private Button previousPaginationButton;

    @FXML
    private Button secondPaginationButton;

    @FXML
    private AnchorPane tableToolbar;

    @FXML
    private Button thirdPaginationButton;

    @FXML
    private Button updatePublisherButton;

    private List<Author> authors = null;

    private final int itemsPerPage = 9;
    private String sortOrder = "ASC";
    private String column = "id";
    private String role;
    private int currentPage = 1;
    private String inputSearchText = "";

    private final ToggleGroup toggleGroup = new ToggleGroup();

    private AuthorService authorService = new AuthorService();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeButtonsAndLabels();
        loadAllAuthors();
        initializePaginationButtons();

        inputSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                inputSearchText = newValue;
                authors = null; // Add backend
                try {
                    updateUsersList();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private void loadAllAuthors() {
        authors = null;
        employeeInfo.setAuthors(authors);
    }

    private void initializeButtonsAndLabels() {
        idLabel.setOnMouseClicked(this::handleLabelClick);
        nameLabel.setOnMouseClicked(this::handleLabelClick);
        actionLabel.setOnMouseClicked(this::handleLabelClick);

        idSortLabel.setContent("");
        nameSortLabel.setContent("");
        actionSortLabel.setContent("");

        addAuthorButton.setOnAction(this::handleAddAuthorButton);
        updateAuthorButton.setOnAction(this::handleUpdateAuthorButton);
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
    void handleAddAuthorButton(ActionEvent event) {
        try {
            FXMLLoaderHelper.loadFXML(new Stage(), "employee/bookAuthors/addAuthor");
            authors = null; // Add backend
            updateUsersList();
        } catch (IOException e) {
            e.printStackTrace();
            AlertUtils.showAlert("Error", "Error loading addUser FXML", Alert.AlertType.ERROR);
        }
    }

    @FXML
    void handleUpdateAuthorButton(ActionEvent event) {
        try {
            if (name != null) {
                UpdateAuthorController.handleTableItemSelection(name);
                FXMLLoaderHelper.loadFXML(new Stage(), "employee/bookAuthors/updateAuthor");
            } else {
                AlertUtils.showAlert("Error", "Can't find author", Alert.AlertType.ERROR);
            }
        } catch (IOException e) {
            e.printStackTrace();
            AlertUtils.showAlert("Error", "Error loading updateAuthor FXML", Alert.AlertType.ERROR);
        }
    }

    static void handleTableItemSelection(String authorName) {
        name = authorName; // Store the selected user
    }
    private void updateUsersList() throws IOException {
        pnItems.getChildren().clear();
        int startIndex = (currentPage - 1) * itemsPerPage;
        int endIndex = Math.min(startIndex + itemsPerPage, authors.size());

        for (int i = startIndex; i < endIndex; i++) {
            Author author = authors.get(i);

            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/bsm/bsm/view/employee/bookAuthors/tableItem.fxml"));
                Node item = fxmlLoader.load();
                TableItemController tableItemController = fxmlLoader.getController();
                tableItemController.setToggleGroup(toggleGroup);
                tableItemController.setAuthorModel(author);
                pnItems.getChildren().add(item);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        int totalPages = (int) Math.ceil((double) authors.size() / itemsPerPage);
        updatePaginationButtons(totalPages);
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
                } else if (totalPages > 5 && startPage >= 6) {
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
    }
    @FXML
    private void handleLabelClick(MouseEvent event){
        Button clickedLabel = (Button) event.getSource();
        String columnName = clickedLabel.getText();
        if (columnName.equals(column)) {
            sortOrder = sortOrder.equals("ASC") ? "DESC" : "ASC";
        } else {
            sortOrder = "ASC";
        }
        column = columnName;
        idSortLabel.setContent(column.equals("ID") ? (sortOrder.equals("ASC") ? "M233.4 105.4c12.5-12.5 32.8-12.5 45.3 0l192 192c12.5 12.5 12.5 32.8 0 45.3s-32.8 12.5-45.3 0L256 173.3 86.6 342.6c-12.5 12.5-32.8 12.5-45.3 0s-12.5-32.8 0-45.3l192-192z" : "M233.4 406.6c12.5 12.5 32.8 12.5 45.3 0l192-192c12.5-12.5 12.5-32.8 0-45.3s-32.8-12.5-45.3 0L256 338.7 86.6 169.4c-12.5-12.5-32.8-12.5-45.3 0s-12.5 32.8 0 45.3l192 192z") : "");
        nameSortLabel.setContent(column.equals("Name") ? (sortOrder.equals("ASC") ? "M233.4 105.4c12.5-12.5 32.8-12.5 45.3 0l192 192c12.5 12.5 12.5 32.8 0 45.3s-32.8 12.5-45.3 0L256 173.3 86.6 342.6c-12.5 12.5-32.8 12.5-45.3 0s-12.5-32.8 0-45.3l192-192z" : "M233.4 406.6c12.5 12.5 32.8 12.5 45.3 0l192-192c12.5-12.5 12.5-32.8 0-45.3s-32.8-12.5-45.3 0L256 338.7 86.6 169.4c-12.5-12.5-32.8-12.5-45.3 0s-12.5 32.8 0 45.3l192 192z") : "");
        actionSortLabel.setContent(column.equals("Action") ? (sortOrder.equals("ASC") ? "M233.4 105.4c12.5-12.5 32.8-12.5 45.3 0l192 192c12.5 12.5 12.5 32.8 0 45.3s-32.8 12.5-45.3 0L256 173.3 86.6 342.6c-12.5 12.5-32.8 12.5-45.3 0s-12.5-32.8 0-45.3l192-192z" : "M233.4 406.6c12.5 12.5 32.8 12.5 45.3 0l192-192c12.5-12.5 12.5-32.8 0-45.3s-32.8-12.5-45.3 0L256 338.7 86.6 169.4c-12.5-12.5-32.8-12.5-45.3 0s-12.5 32.8 0 45.3l192 192z") : "");
        try {
            updateUsersList();
        } catch (IOException e) {
            e.printStackTrace();
        }
        authors = null; // Add backend
        try {
            updateUsersList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
}
