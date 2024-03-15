package com.bsm.bsm.admin.userAccount;

import com.bsm.bsm.account.AccountController;
import com.bsm.bsm.account.AccountService;
import com.bsm.bsm.admin.AdminModel;
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
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class UserAccountController implements Initializable {
    @FXML
    private static String email; // Variable to store the selected user
    private final ToggleGroup toggleGroup = new ToggleGroup();
    private final UserAccountService userAccountService = new UserAccountService();
    private final UserModel adminInfo = UserSingleton.getInstance().getUser();
    private final AdminModel adminModel = userAccountService.getAllUsersInfo(adminInfo.getId());
    public List<UserModel> users = adminModel.viewUsers();
    private final int itemsPerPage = 9;
    @FXML
    public Button employeeButton, adminButton;
    @FXML
    public Button addUserButton, passwordResetButton, updateUserButton;
    @FXML
    public Button previousPaginationButton, nextPaginationButton, firstPaginationButton, secondPaginationButton, thirdPaginationButton, fourthPaginationButton, fifthPaginationButton;
    String typeSearchText;
    @FXML
    private VBox pnItems = null;
    @FXML
    private TextField inputSearch;
    @FXML
    private MenuButton typeSearch;
    private AccountController accountController = null;
    private int currentPage = 1;
    private String currentTypeUser = "employee";
    private String inputSearchText = "";

    public UserAccountController() {
        accountController = new AccountService();
    }

    static void handleTableItemSelection(String userEmail) {
        email = userEmail; // Store the selected user
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        employeeButton.getStyleClass().add("profile-setting-button");
        adminButton.getStyleClass().add("profile-setting-button");

        // Load all users initially
        try {
            updateUsersList(".employee@bms.com");
            updateButtonStyle(employeeButton);
        } catch (IOException e) {
            e.printStackTrace();
        }

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
                users = accountController.search(inputSearchText, typeSearchText);
                if (currentTypeUser.equals("employee")) {
                    users = users.stream()
                            .filter(user ->
                                    user.getEmail().endsWith(".employee@bms.com"))
                            .toList();
                } else {
                    users = users.stream()
                            .filter(user ->
                            {

                                return user.getEmail().endsWith(".admin@bms.com");
                            })
                            .toList();
                }
                try {
                    updateUsersList(Objects.equals(currentTypeUser, "employee") ? ".employee@bms.com" : ".admin@bms.com");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        // Attach event handlers to pagination buttons
        firstPaginationButton.setOnAction(this::handlePaginationButton);
        secondPaginationButton.setOnAction(this::handlePaginationButton);
        thirdPaginationButton.setOnAction(this::handlePaginationButton);
        fourthPaginationButton.setOnAction(this::handlePaginationButton);
        fifthPaginationButton.setOnAction(this::handlePaginationButton);
        previousPaginationButton.setOnAction(this::handlePaginationButton);
        nextPaginationButton.setOnAction(this::handlePaginationButton);
    }

    @FXML
    private void handleEmployeeButton(ActionEvent event) throws IOException {
        this.users = adminModel.viewUsers();
        System.out.println(users);
        typeSearchText = "";
        inputSearchText = "";
        inputSearch.setText("");
        typeSearch.setText("");
        updateUsersList(".employee@bms.com");
        currentTypeUser = "employee";
        updateButtonStyle(employeeButton);
    }

    @FXML
    private void handleAdminButton(ActionEvent event) throws IOException {
        this.users = adminModel.viewUsers();
        System.out.println(users);
        typeSearchText = "";
        inputSearchText = "";
        inputSearch.setText("");
        typeSearch.setText("");
        updateUsersList(".admin@bms.com");
        currentTypeUser = "admin";
        updateButtonStyle(adminButton);
    }

    @FXML
    private void handlePasswordResetButton(ActionEvent event) throws IOException {
        if (email != null) {
            PasswordResetController.handleTableItemSelection(email);
            FXMLLoaderHelper.loadFXML(new Stage(), "admin/userAccount/passwordReset");
        } else {
            AlertUtils.showAlert("Error", "Can't find user", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void handleUpdateUserButton(ActionEvent event) throws IOException {
        if (email != null) {
            UpdateUserController.handleTableItemSelection(email);
            FXMLLoaderHelper.loadFXML(new Stage(), "admin/userAccount/updateUser");
        } else {
            AlertUtils.showAlert("Error", "Can't find user", Alert.AlertType.ERROR);
        }
    }

    @FXML
    void handleAddUserButton(ActionEvent event) throws IOException {
        FXMLLoaderHelper.loadFXML(new Stage(), "admin/userAccount/addUser");
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
        // Call updateUsersList with the appropriate email suffix
        if (employeeButton.getStyleClass().contains("profile-setting-button-admin")) {
            try {
                updateUsersList(".employee@bms.com");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                updateUsersList(".admin@bms.com");
            } catch (IOException e) {
                e.printStackTrace();
            }
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


    private void updateUsersList(String emailSuffix) throws IOException {
        pnItems.getChildren().clear();
        int startIndex = (currentPage - 1) * itemsPerPage;
        int endIndex = Math.min(startIndex + itemsPerPage, users.size());

        for (int i = startIndex; i < endIndex; i++) {
            UserModel user = users.get(i);
            if (user.getEmail().endsWith(emailSuffix)) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/bsm/bsm/view/admin/userAccount/tableItem.fxml"));
                Node item = fxmlLoader.load();
                TableItemController tableItemController = fxmlLoader.getController();
                tableItemController.setToggleGroup(toggleGroup);
                tableItemController.setUserModel(user);
                pnItems.getChildren().add(item);
            }
        }

        int totalPages = (int) Math.ceil((double) users.size() / itemsPerPage);
        updatePaginationButtons(totalPages);
    }

    private void updatePaginationButtons(int totalPages) {
        // Clear all pagination buttons visibility
        for (Button button : Arrays.asList(firstPaginationButton, secondPaginationButton, thirdPaginationButton, fourthPaginationButton, fifthPaginationButton)) {
            button.setVisible(false);
            button.setManaged(false);
            button.getStyleClass().add("pagination-button-admin");
        }

        // Show pagination buttons based on the current page and total pages
        if (totalPages > 1) {
            int startPage = Math.max(1, Math.min(currentPage - 2, totalPages - 4));
            int endPage = Math.min(startPage + 4, totalPages);

            previousPaginationButton.setDisable(!(currentPage > 1));
            nextPaginationButton.setDisable(!(currentPage < totalPages));

            for (int i = startPage; i <= endPage; i++) {
                Button button;
                if (totalPages > 5 && startPage < 6) {
                    switch (i - startPage) {
                        case 0:
                            button = firstPaginationButton;
                            button.setText(String.valueOf(i));
                            break;
                        case 1:
                            button = secondPaginationButton;
                            button.setDisable(false);
                            button.setText(String.valueOf(i));
                            break;
                        case 2:
                            button = thirdPaginationButton;
                            button.setText(String.valueOf(i));
                            break;
                        case 3:
                            button = fourthPaginationButton;
                            button.setText("...");
                            button.setDisable(true);
                            break;
                        case 4:
                            button = fifthPaginationButton;
                            button.setText(String.valueOf(totalPages));
                            break;
                        default:
                            throw new IllegalStateException("Unexpected value: " + (i - startPage));
                    }
                } else if (totalPages > 5 && startPage >= 6) {
                    switch (i - startPage) {
                        case 0:
                            button = firstPaginationButton;
                            button.setText(String.valueOf(1));
                            break;
                        case 1:
                            button = secondPaginationButton;
                            button.setText("...");
                            button.setDisable(true);
                            break;
                        case 2:
                            button = thirdPaginationButton;
                            button.setText(String.valueOf(i));
                            break;
                        case 3:
                            button = fourthPaginationButton;
                            button.setDisable(false);
                            button.setText(String.valueOf(i));
                            break;
                        case 4:
                            button = fifthPaginationButton;
                            button.setText(String.valueOf(i));
                            break;
                        default:
                            throw new IllegalStateException("Unexpected value: " + (i - startPage));
                    }
                } else {
                    switch (i - startPage) {
                        case 0:
                            button = firstPaginationButton;
                            break;
                        case 1:
                            button = secondPaginationButton;
                            button.setDisable(false);
                            break;
                        case 2:
                            button = thirdPaginationButton;
                            break;
                        case 3:
                            button = fourthPaginationButton;
                            break;
                        case 4:
                            button = fifthPaginationButton;
                            break;
                        default:
                            throw new IllegalStateException("Unexpected value: " + (i - startPage));
                    }
                    button.setText(String.valueOf(i));
                }

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
}