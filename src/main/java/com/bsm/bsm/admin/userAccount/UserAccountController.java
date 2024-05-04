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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class UserAccountController implements Initializable {
    private static String email;
    private final ToggleGroup toggleGroup = new ToggleGroup();
    private final AdminModel adminInfo = (AdminModel) UserSingleton.getInstance().getUser();
    private final AccountService accountService = new AccountService();
    @FXML
    public Button employeeButton, adminButton, addUserButton, passwordResetButton, updateUserButton;
    @FXML
    public Button previousPaginationButton, nextPaginationButton, firstPaginationButton, secondPaginationButton, thirdPaginationButton, fourthPaginationButton, fifthPaginationButton;
    @FXML
    public Button idLabel, nameLabel, emailLabel, lastLoginLabel, actionLabel;
    @FXML
    public SVGPath idSortLabel, nameSortLabel, emailSortLabel, lastLoginSortLabel, actionSortLabel;
    public List<UserModel> users = null;
    @FXML
    private VBox pnItems;
    @FXML
    private Button refreshButton;
    @FXML
    private TextField inputSearch;
    private String sortOrder = "ASC";
    private String column = "id";
    private int currentPage = 1;
    private String inputSearchText = "";
    private static final String EMPLOYEE_ROLE = ".employee@bms.com";
    private static final String ADMIN_ROLE = ".admin@bms.com";
    private static final int ITEMS_PER_PAGE = 8;
    private boolean isSearch = false;


    static void handleTableItemSelection(String userEmail) {
        email = userEmail; // Store the selected user
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeButtonsAndLabels();
        loadAllUsers(EMPLOYEE_ROLE);
        initializePaginationButtons();

        inputSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                isSearch = !newValue.isEmpty();
                inputSearchText = newValue;
                if (!isSearch) loadAllUsers(getRoleFromButton());
                else users = accountService.search(inputSearchText, adminInfo.getId());

                users = accountService.sort(users, sortOrder.equals("ASC"), column);
                try {
                    updateUsersList(getRoleFromButton());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private String getRoleFromButton() {
        return employeeButton.getStyleClass().contains("profile-setting-button-admin") ? EMPLOYEE_ROLE : ADMIN_ROLE;
    }

    private void loadAllUsers(String role) {
        users = accountService.getAllUsersBySortInfo(adminInfo.getId(), sortOrder, column);
        adminInfo.setUsers(users);

        try {
            if (role == null) role = getRoleFromButton();

            updateUsersList(role);
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
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
        currentPage = 1;
        defaultSort();
        inputSearch.clear();
        loadAllUsers(EMPLOYEE_ROLE);
        updateButtonStyle(employeeButton);
    }

    @FXML
    private void handleAdminButton(ActionEvent event) {
        currentPage = 1;
        defaultSort();
        inputSearch.clear();
        loadAllUsers(ADMIN_ROLE);
        updateButtonStyle(adminButton);
    }

    @FXML
    private void handlePasswordResetButton(ActionEvent event) {
        try {
            if (email != null) {
                PasswordResetController.handleTableItemSelection(email);
                FXMLLoaderHelper.loadFXML(new Stage(), "admin/userAccount/passwordReset", "Reset Password");
            } else {
                AlertUtils.showAlert("Error", "Can't find user", Alert.AlertType.ERROR);
            }
        } catch (IOException e) {
            e.printStackTrace();
            AlertUtils.showAlert("Error", "Error loading passwordReset FXML", Alert.AlertType.ERROR);
        }
    }

    @FXML
    void handleRefreshButton(ActionEvent event) {
        loadAllUsers(getRoleFromButton());
    }

    @FXML
    private void handleUpdateUserButton(ActionEvent event) {
        try {
            if (email != null) {
                UpdateUserController.handleTableItemSelection(email);

                UserModel user = users.stream()
                        .filter(userModel -> userModel.getEmail().equals(email))
                        .findFirst()
                        .orElse(new UserModel());

                if (!user.isEnabled()) {
                    AlertUtils.showAlert("Error", "This user has been disabled. Please enable the user to update profile.", Alert.AlertType.ERROR);
                } else {
                    FXMLLoaderHelper.loadFXML(new Stage(), "admin/userAccount/updateUser", "Update User");
                }
            } else {
                AlertUtils.showAlert("Error", "Can't find user", Alert.AlertType.ERROR);
            }
        } catch (IOException e) {
            AlertUtils.showAlert("Error", "Error loading updateUser FXML", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void handleAddUserButton(ActionEvent event) {
        try {
            Stage stage = new Stage();
            FXMLLoaderHelper.loadFXML(stage, "admin/userAccount/addUser", "Add User ");
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

        loadAllUsers(getRoleFromButton());
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
//                if (totalPages > 5 && startPage < 6) {
//                    button = paginationButtons.get(buttonIndex);
//                } else if (totalPages > 5) {
//                    button = paginationButtons.get(buttonIndex + 1);
//                } else {
                    button = paginationButtons.get(buttonIndex);
//                }
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

    private void updateUsersList(String role) throws IOException {
        pnItems.getChildren().clear();
        int itemsPerPage = ITEMS_PER_PAGE;
        int totalUserCountForRole = getTotalUserCountForRole(role);

        int totalPages = (int) Math.ceil((double) totalUserCountForRole / itemsPerPage);

        int startIndex = (currentPage - 1) * itemsPerPage;

        List<UserModel> userForRole = new ArrayList<>();

        for (UserModel user : users) {
            if ((user instanceof EmployeeModel && role.equals(".employee@bms.com")) ||
                    (user instanceof AdminModel && role.equals(".admin@bms.com"))) {
                userForRole.add(user);
            }
        }

        for (int i = startIndex, totalCount = 0; i < userForRole.size(); i++) {

            if (totalCount >= itemsPerPage) {
                break;
            }
            UserModel user = userForRole.get(i);
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

            totalCount++;
        }

        updatePaginationButtons(totalPages);
    }

    private int getTotalUserCountForRole(String role) {
        int count = 0;
        for (UserModel user : users) {
            if ((user instanceof EmployeeModel && role.equals(".employee@bms.com")) ||
                    (user instanceof AdminModel && role.equals(".admin@bms.com"))) {
                count++;
            }
        }
        return count;
    }
    
    @FXML
    private void handleLabelClick(MouseEvent event) {
        Button clickedLabel = (Button) event.getSource();
        String columnName = clickedLabel.getText().trim().toLowerCase();
        if (columnName.equals(column)) {
            sortOrder = sortOrder.equals("ASC") ? "DESC" : "ASC";
        } else {
            sortOrder = "ASC";
        }
        column = columnName;

        var isAscending = sortOrder.equals("ASC");
        idSortLabel.setContent(column.equals("id") ? (isAscending ? "M233.4 105.4c12.5-12.5 32.8-12.5 45.3 0l192 192c12.5 12.5 12.5 32.8 0 45.3s-32.8 12.5-45.3 0L256 173.3 86.6 342.6c-12.5 12.5-32.8 12.5-45.3 0s-12.5-32.8 0-45.3l192-192z" : "M233.4 406.6c12.5 12.5 32.8 12.5 45.3 0l192-192c12.5-12.5 12.5-32.8 0-45.3s-32.8-12.5-45.3 0L256 338.7 86.6 169.4c-12.5-12.5-32.8-12.5-45.3 0s-12.5 32.8 0 45.3l192 192z") : "");
        nameSortLabel.setContent(column.equals("name") ? (isAscending ? "M233.4 105.4c12.5-12.5 32.8-12.5 45.3 0l192 192c12.5 12.5 12.5 32.8 0 45.3s-32.8 12.5-45.3 0L256 173.3 86.6 342.6c-12.5 12.5-32.8 12.5-45.3 0s-12.5-32.8 0-45.3l192-192z" : "M233.4 406.6c12.5 12.5 32.8 12.5 45.3 0l192-192c12.5-12.5 12.5-32.8 0-45.3s-32.8-12.5-45.3 0L256 338.7 86.6 169.4c-12.5-12.5-32.8-12.5-45.3 0s-12.5 32.8 0 45.3l192 192z") : "");
        emailSortLabel.setContent(column.equals("email") ? (isAscending ? "M233.4 105.4c12.5-12.5 32.8-12.5 45.3 0l192 192c12.5 12.5 12.5 32.8 0 45.3s-32.8 12.5-45.3 0L256 173.3 86.6 342.6c-12.5 12.5-32.8 12.5-45.3 0s-12.5-32.8 0-45.3l192-192z" : "M233.4 406.6c12.5 12.5 32.8 12.5 45.3 0l192-192c12.5-12.5 12.5-32.8 0-45.3s-32.8-12.5-45.3 0L256 338.7 86.6 169.4c-12.5-12.5-32.8-12.5-45.3 0s-12.5 32.8 0 45.3l192 192z") : "");
        lastLoginSortLabel.setContent(column.equals("last login") ? (isAscending ? "M233.4 105.4c12.5-12.5 32.8-12.5 45.3 0l192 192c12.5 12.5 12.5 32.8 0 45.3s-32.8 12.5-45.3 0L256 173.3 86.6 342.6c-12.5 12.5-32.8 12.5-45.3 0s-12.5-32.8 0-45.3l192-192z" : "M233.4 406.6c12.5 12.5 32.8 12.5 45.3 0l192-192c12.5-12.5 12.5-32.8 0-45.3s-32.8-12.5-45.3 0L256 338.7 86.6 169.4c-12.5-12.5-32.8-12.5-45.3 0s-12.5 32.8 0 45.3l192 192z") : "");
        actionSortLabel.setContent(column.equals("enable/disable") ? (isAscending ? "M233.4 105.4c12.5-12.5 32.8-12.5 45.3 0l192 192c12.5 12.5 12.5 32.8 0 45.3s-32.8 12.5-45.3 0L256 173.3 86.6 342.6c-12.5 12.5-32.8 12.5-45.3 0s-12.5-32.8 0-45.3l192-192z" : "M233.4 406.6c12.5 12.5 32.8 12.5 45.3 0l192-192c12.5-12.5 12.5-32.8 0-45.3s-32.8-12.5-45.3 0L256 338.7 86.6 169.4c-12.5-12.5-32.8-12.5-45.3 0s-12.5 32.8 0 45.3l192 192z") : "");

        if (isSearch) {
            users = accountService.search(inputSearchText, adminInfo.getId());
        } else {
            users = accountService.getAllUsersBySortInfo(adminInfo.getId(), sortOrder, column);
        }
        users = accountService.sort(users, isAscending, column);
        try {
            updateUsersList(getRoleFromButton());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void clearSortLabels() {
        idSortLabel.setContent("");
        nameSortLabel.setContent("");
        emailSortLabel.setContent("");
        lastLoginSortLabel.setContent("");
        actionSortLabel.setContent("");
    }

    private void defaultSort() {
        sortOrder = "ASC";
        column = "id";
        clearSortLabels();
    }
}