package com.bsm.bsm.admin.userAccount;

import com.bsm.bsm.admin.AdminModel;
import com.bsm.bsm.user.UserModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UserAccountController{
    public Button employeeButton, adminButton;
    @FXML
    private VBox pnItems = null;

    private final UserAccountService userAccountService = new UserAccountService();

    @FXML
    private void initialize() throws IOException {
        employeeButton.getStyleClass().add("profile-setting-button");
        adminButton.getStyleClass().add("profile-setting-button");
        // Load all users initially
        updateUsersList(".employee@bms.com");
        updateButtonStyle(employeeButton);
    }

//    @Override
//    public void initialize(URL location, ResourceBundle resources) {
//    }

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


    private void updateUsersList(String emailSuffix) {
        // Clear current items in the list
        pnItems.getChildren().clear();

        AdminModel adminModel = userAccountService.getAllUsersInfo();
        for (UserModel user : adminModel.viewUsers()) {
            if (user.getEmail().endsWith(emailSuffix) || emailSuffix.isEmpty()) {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/com/bsm/bsm/view/admin/userAccount/tableItem.fxml"));
                    Node item = fxmlLoader.load();
                    TableItemController tableItemController = fxmlLoader.getController();
                    tableItemController.setUserModel(user);
                    pnItems.getChildren().add(item);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
