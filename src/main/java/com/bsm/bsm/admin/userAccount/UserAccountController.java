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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
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
    @FXML
    private VBox pnItems = null;
    @FXML
    private static UserModel selectedUser; // Variable to store the selected user

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
        // Load all users initially
        try {
            updateUsersList(".employee@bms.com");
            updateButtonStyle(employeeButton);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Attach event handlers to pagination buttons
        firstPaginationButton.setOnAction(this::handlePaginationButton);
        secondPaginationButton.setOnAction(this::handlePaginationButton);
        thirdPaginationButton.setOnAction(this::handlePaginationButton);
        fourthPaginationButton.setOnAction(this::handlePaginationButton);
        fifthPaginationButton.setOnAction(this::handlePaginationButton);
    }

    @FXML
    private void handleEmployeeButton(ActionEvent event) throws IOException {
        updateUsersList(".employee@bms.com");
        updateButtonStyle(employeeButton);
    }

    @FXML
    private void handleAdminButton(ActionEvent event) throws IOException {
        updateUsersList(".admin@bms.com");
        updateButtonStyle(adminButton);
    }

    @FXML
    private void handlePasswordResetButton(ActionEvent event) throws IOException {
        if (selectedUser != null) {
            PasswordResetController.handleTableItemSelection(selectedUser);
            FXMLLoaderHelper.loadFXML(new Stage(), "admin/userAccount/passwordReset");
        } else {
            AlertUtils.showAlert("Error", "Can't find user", Alert.AlertType.ERROR);
        }
    }
    @FXML
    private void handleUpdateUserButton(ActionEvent event) throws IOException {
        if (selectedUser != null) {
            UpdateUserController.handleTableItemSelection(selectedUser);
            FXMLLoaderHelper.loadFXML(new Stage(), "admin/userAccount/updateUser");
        } else {
            AlertUtils.showAlert("Error", "Can't find user", Alert.AlertType.ERROR);
        }
    }
    @FXML
    private void handleAddUserButton(ActionEvent event) throws IOException {

    }
    public static void handleTableItemDoubleClick(UserModel user) throws IOException {
        if (user != null) {
            UserDetailController.handleTableItemSelection(user);
            FXMLLoaderHelper.loadFXML(new Stage(), "admin/userAccount/userDetail");
        } else {
            AlertUtils.showAlert("Error", "Can't find user", Alert.AlertType.ERROR);

        }
    }
    public static void handleTableItemSelection(UserModel user) {
        selectedUser = user; // Store the selected user
    }
    @FXML
    private void handlePaginationButton(ActionEvent event) {
        Button buttonClicked = (Button) event.getSource();
        currentPage = Integer.parseInt(buttonClicked.getText());
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
        }

        // Show pagination buttons based on the current page and total pages
        if (totalPages > 1) {
            int startPage = Math.max(1, Math.min(currentPage - 2, totalPages - 4));
            int endPage = Math.min(startPage + 4, totalPages);

            previousPaginationButton.setVisible(currentPage > 1);
            nextPaginationButton.setVisible(currentPage < totalPages);

            for (int i = startPage; i <= endPage; i++) {
                Button button;
                switch (i - startPage) {
                    case 0:
                        button = firstPaginationButton;
                        break;
                    case 1:
                        button = secondPaginationButton;
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
                button.setVisible(true);
            }
        } else {
            previousPaginationButton.setVisible(false);
            nextPaginationButton.setVisible(false);
        }
    }
}
