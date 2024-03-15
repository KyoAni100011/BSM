package com.bsm.bsm.admin.userAccount;

import com.bsm.bsm.admin.AdminModel;
import com.bsm.bsm.user.UserModel;
import com.bsm.bsm.user.UserSingleton;
import com.bsm.bsm.utils.AlertUtils;
import com.bsm.bsm.utils.FXMLLoaderHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class UserAccountController implements Initializable {
    @FXML
    public Button employeeButton, adminButton;
    @FXML
    public Button addUserButton, passwordResetButton, updateUserButton;
    @FXML
    public Button previousPaginationButton, nextPaginationButton, firstPaginationButton, secondPaginationButton, thirdPaginationButton, fourthPaginationButton, fifthPaginationButton;
    public Label idLabel, nameLabel, emailLabel, lastLoginLabel;
    @FXML
    private VBox pnItems = null;
    @FXML
    private static String email; // Variable to store the selected user
    private String sortOrder;
    private String column;
    private String emailSuffix;
    private final ToggleGroup toggleGroup = new ToggleGroup();
    private final UserAccountService userAccountService = new UserAccountService();
    private final UserModel adminInfo = UserSingleton.getInstance().getUser();
    private final AdminModel adminModel = userAccountService.getAllUsersInfo(adminInfo.getId());
    private final List<UserModel> users = adminModel.viewUsers();
    private int currentPage = 1;
    private final int itemsPerPage = 9;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        employeeButton.getStyleClass().add("profile-setting-button");
        adminButton.getStyleClass().add("profile-setting-button");

        idLabel.setOnMouseClicked(this::handleLabelClick);
        nameLabel.setOnMouseClicked(this::handleLabelClick);
        emailLabel.setOnMouseClicked(this::handleLabelClick);
        lastLoginLabel.setOnMouseClicked(this::handleLabelClick);

        // Load all users initially
        emailSuffix = ".employee@bms.com";
        updateButtonStyle(employeeButton);

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
        emailSuffix = ".employee@bms.com";
        updateButtonStyle(employeeButton);
    }

    @FXML
    private void handleAdminButton(ActionEvent event) throws IOException {
        emailSuffix = ".admin@bms.com";
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

    @FXML void handleAddUserButton(ActionEvent event) throws IOException {
        FXMLLoaderHelper.loadFXML(new Stage(), "admin/userAccount/addUser");
    }
    static  void handleTableItemSelection(String userEmail) {
        email = userEmail; // Store the selected user
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
                emailSuffix = ".employee@bms.com";
                updateUsersList();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            emailSuffix = ".admin@bms.com";
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

        AdminModel adminModel = userAccountService.getAllUsersInfo(adminInfo.getId(), sortOrder, column);
        List<UserModel> users = adminModel.viewUsers();
        System.out.println(users.size());
        System.out.println(users.get(0).getEmail());
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
                if(totalPages > 5 && startPage <6){
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
                }else if(totalPages > 5 && startPage >= 6){
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
                }
                else{
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

    public enum SortOrder {
        ASCENDING,
        DESCENDING,
        DEFAULT
    }

    private SortOrder idSortOrder = SortOrder.DEFAULT;
    private SortOrder employeeSortOrder = SortOrder.DEFAULT;
    private SortOrder emailSortOrder = SortOrder.DEFAULT;
    private SortOrder lastLoginSortOrder = SortOrder.DEFAULT;


    @FXML
    private void handleLabelClick(MouseEvent event) {
        // Retrieve column name from the clicked label
        Label clickedLabel = (Label) event.getSource();
        String columnName = clickedLabel.getText();

        // Determine the new sort order based on the current sort order
        if (columnName.equals(column)) {
            sortOrder = sortOrder.equals("ASC") ? "DESC" : "ASC";
        } else {
            sortOrder = "ASC"; // Default to ascending order for new column
        }

        // Update column to reflect the clicked column
        column = columnName;

        // Call updateUsersList with updated parameters for sorting
        try {
            updateUsersList();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
