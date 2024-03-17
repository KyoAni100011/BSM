package com.bsm.bsm.admin.userAccount;

import com.bsm.bsm.account.AccountService;
import com.bsm.bsm.admin.AdminModel;
import com.bsm.bsm.employee.EmployeeModel;
import com.bsm.bsm.user.UserModel;
import com.bsm.bsm.user.UserSingleton;
import com.bsm.bsm.utils.AlertUtils;
import com.bsm.bsm.utils.FXMLLoaderHelper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class UserAccountController implements Initializable {
    private AdminModel adminInfo = (AdminModel) UserSingleton.getInstance().getUser();
    private static String email;
    private final ToggleGroup toggleGroup = new ToggleGroup();
    private UserAccountService userAccountService = new UserAccountService();
    private AccountService accountService = new AccountService();
    private List<UserModel> users = null;
    private final int itemsPerPage = 9;
    private String sortOrder = "ASC";
    private String column = "id";
    private String role;
    private int currentPage = 1;
    private String inputSearchText = "";
    private String typeSearchText = "name";

    @FXML
    private VBox pnItems;

    @FXML
    public Button employeeButton, adminButton, addUserButton, passwordResetButton, updateUserButton;

    @FXML
    public Button previousPaginationButton, nextPaginationButton, firstPaginationButton, secondPaginationButton, thirdPaginationButton, fourthPaginationButton, fifthPaginationButton;

    @FXML
    public Button idLabel, nameLabel, emailLabel, lastLoginLabel, actionLabel;

    @FXML
    public SVGPath idSortLabel, nameSortLabel, emailSortLabel, lastLoginSortLabel, actionSortLabel;

    @FXML
    private TextField inputSearch;

    @FXML
    private MenuButton typeSearch;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeButtonsAndLabels();
        loadAllUsers();
        initializePaginationButtons();

        typeSearch.setText(typeSearchText);

        for (MenuItem item : typeSearch.getItems()) {
            item.setOnAction(event -> {
                typeSearchText = ((MenuItem) event.getSource()).getText();
                typeSearch.setText(typeSearchText);
            });
        }

        inputSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                inputSearchText = newValue;
                users = accountService.search(inputSearchText, typeSearchText);
                try {
                    updateUsersList();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private void initializeButtonsAndLabels() {
        employeeButton.getStyleClass().add("profile-setting-button");
        updateButtonStyle(employeeButton);
        adminButton.getStyleClass().add("profile-setting-button");

        idLabel.setOnMouseClicked(this::handleLabelClick);
        nameLabel.setOnMouseClicked(this::handleLabelClick);
        emailLabel.setOnMouseClicked(this::handleLabelClick);
        lastLoginLabel.setOnMouseClicked(this::handleLabelClick);
        actionLabel.setOnMouseClicked(this::handleLabelClick);

        idSortLabel.setContent("");
        nameSortLabel.setContent("");
        emailSortLabel.setContent("");
        lastLoginSortLabel.setContent("");
        actionSortLabel.setContent("");

        addUserButton.setOnAction(this::handleAddUserButton);
        passwordResetButton.setOnAction(this::handlePasswordResetButton);
        updateUserButton.setOnAction(this::handleUpdateUserButton);
        employeeButton.setOnAction(this::handleEmployeeButton);
        adminButton.setOnAction(this::handleAdminButton);
    }

    private void loadAllUsers() {
        users = userAccountService.getAllUsersInfo(adminInfo.getId(), sortOrder, column);
        adminInfo.setUsers(users);

        try {
            role = ".employee@bms.com";
            updateUsersList();
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    static void handleTableItemSelection(String userEmail) {
        email = userEmail; // Store the selected user
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
    private void handleEmployeeButton(ActionEvent event) {
        try {
            role = ".employee@bms.com";
            updateUsersList();
            updateButtonStyle(employeeButton);
        } catch (IOException e) {
            e.printStackTrace();
            AlertUtils.showAlert("Error", "Error loading employee users", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void handleAdminButton(ActionEvent event) {
        try {
            role = ".admin@bms.com";
            updateUsersList();
            updateButtonStyle(adminButton);
        } catch (IOException e) {
            e.printStackTrace();
            AlertUtils.showAlert("Error", "Error loading admin users", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void handlePasswordResetButton(ActionEvent event) {
        try {
            if (email != null) {
                PasswordResetController.handleTableItemSelection(email);
                FXMLLoaderHelper.loadFXML(new Stage(), "admin/userAccount/passwordReset");
            } else {
                AlertUtils.showAlert("Error", "Can't find user", Alert.AlertType.ERROR);
            }
        } catch (IOException e) {
            e.printStackTrace();
            AlertUtils.showAlert("Error", "Error loading passwordReset FXML", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void handleUpdateUserButton(ActionEvent event) {
        try {
            if (email != null) {
                UpdateUserController.handleTableItemSelection(email);
                FXMLLoaderHelper.loadFXML(new Stage(), "admin/userAccount/updateUser");
            } else {
                AlertUtils.showAlert("Error", "Can't find user", Alert.AlertType.ERROR);
            }
        } catch (IOException e) {
            e.printStackTrace();
            AlertUtils.showAlert("Error", "Error loading updateUser FXML", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void handleAddUserButton(ActionEvent event) {
        try {
            FXMLLoaderHelper.loadFXML(new Stage(), "admin/userAccount/addUser");
            //refresh page the user list after adding new user
            users = userAccountService.getAllUsersInfo(UserSingleton.getInstance().getUser().getId(), sortOrder, column);
            updateUsersList();
        } catch (IOException e) {
            e.printStackTrace();
            AlertUtils.showAlert("Error", "Error loading addUser FXML", Alert.AlertType.ERROR);
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
        if (employeeButton.getStyleClass().contains("profile-setting-button-admin")) {
            try {
                role = ".employee@bms.com";
                updateUsersList();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            role = ".admin@bms.com";
        }
    }

    private void updateButtonStyle(Button activeButton) {
        if (activeButton == employeeButton) {
            employeeButton.getStyleClass().remove("profile-setting-button-admin");
            employeeButton.getStyleClass().add("profile-setting-button-admin");
            adminButton.getStyleClass().remove("profile-setting-button-admin");
        } else {
            adminButton.getStyleClass().remove("profile-setting-button-admin");
            adminButton.getStyleClass().add("profile-setting-button-admin");
            employeeButton.getStyleClass().remove("profile-setting-button-admin");
        }
    }

    private void updateUsersList() throws IOException {
        pnItems.getChildren().clear();
        int startIndex = (currentPage - 1) * itemsPerPage;
        int endIndex = Math.min(startIndex + itemsPerPage, users.size());

        for (int i = startIndex; i < endIndex; i++) {
            UserModel user = users.get(i);
            if ((user instanceof EmployeeModel && role.equals(".employee@bms.com")) ||
                    (user instanceof AdminModel && role.equals(".admin@bms.com"))) {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/bsm/bsm/view/admin/userAccount/tableItem.fxml"));
                    Node item = fxmlLoader.load();
                    TableItemController tableItemController = fxmlLoader.getController();
                    tableItemController.setToggleGroup(toggleGroup);
                    tableItemController.setUserModel(user);
                    pnItems.getChildren().add(item);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        int totalPages = (int) Math.ceil((double) users.size() / itemsPerPage);
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
        emailSortLabel.setContent(column.equals("Email") ? (sortOrder.equals("ASC") ? "M233.4 105.4c12.5-12.5 32.8-12.5 45.3 0l192 192c12.5 12.5 12.5 32.8 0 45.3s-32.8 12.5-45.3 0L256 173.3 86.6 342.6c-12.5 12.5-32.8 12.5-45.3 0s-12.5-32.8 0-45.3l192-192z" : "M233.4 406.6c12.5 12.5 32.8 12.5 45.3 0l192-192c12.5-12.5 12.5-32.8 0-45.3s-32.8-12.5-45.3 0L256 338.7 86.6 169.4c-12.5-12.5-32.8-12.5-45.3 0s-12.5 32.8 0 45.3l192 192z") : "");
        lastLoginSortLabel.setContent(column.equals("Last login") ? (sortOrder.equals("ASC") ? "M233.4 105.4c12.5-12.5 32.8-12.5 45.3 0l192 192c12.5 12.5 12.5 32.8 0 45.3s-32.8 12.5-45.3 0L256 173.3 86.6 342.6c-12.5 12.5-32.8 12.5-45.3 0s-12.5-32.8 0-45.3l192-192z" : "M233.4 406.6c12.5 12.5 32.8 12.5 45.3 0l192-192c12.5-12.5 12.5-32.8 0-45.3s-32.8-12.5-45.3 0L256 338.7 86.6 169.4c-12.5-12.5-32.8-12.5-45.3 0s-12.5 32.8 0 45.3l192 192z") : "");
        actionSortLabel.setContent(column.equals("Action") ? (sortOrder.equals("ASC") ? "M233.4 105.4c12.5-12.5 32.8-12.5 45.3 0l192 192c12.5 12.5 12.5 32.8 0 45.3s-32.8 12.5-45.3 0L256 173.3 86.6 342.6c-12.5 12.5-32.8 12.5-45.3 0s-12.5-32.8 0-45.3l192-192z" : "M233.4 406.6c12.5 12.5 32.8 12.5 45.3 0l192-192c12.5-12.5 12.5-32.8 0-45.3s-32.8-12.5-45.3 0L256 338.7 86.6 169.4c-12.5-12.5-32.8-12.5-45.3 0s-12.5 32.8 0 45.3l192 192z") : "");
        try {
            System.out.println("Column: " + column + " Sort Order: " + sortOrder);
            updateUsersList();
        } catch (IOException e) {
            e.printStackTrace();
        }
        users = userAccountService.getAllUsersInfo(UserSingleton.getInstance().getUser().getId(), sortOrder, column);
        try {
            updateUsersList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
