package com.bsm.bsm.admin.profileSetting;

import com.bsm.bsm.user.UserSingleton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;

public class ProfileSettingController {

    public Button editProfileButton, changePasswordButton;

    public BorderPane bp;

    @FXML
    private void initialize() {
        try {
            editProfileButton.getStyleClass().addAll("profile-setting-button-selected", "profile-setting-button-admin");
            changePasswordButton.getStyleClass().removeAll("profile-setting-button", "profile-setting-button-admin");
            loadPage("editProfile");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleEditProfile(ActionEvent event) throws IOException {
        editProfileButton.getStyleClass().addAll("profile-setting-button-selected", "profile-setting-button-admin");
        changePasswordButton.getStyleClass().removeAll("profile-setting-button", "profile-setting-button-admin");
        loadPage("editProfile");
    }

    @FXML
    private void handleChangePassword(ActionEvent event) throws IOException {
        changePasswordButton.getStyleClass().addAll("profile-setting-button-selected", "profile-setting-button-admin");
        editProfileButton.getStyleClass().removeAll("profile-setting-button", "profile-setting-button-admin");
        loadPage("changePassword");
    }

    private void loadPage(String page) throws IOException {
        String resourcePath = "/com/bsm/bsm/view/admin/profileSetting/" + page + ".fxml";
        URL resourceUrl = getClass().getResource(resourcePath);
        if (resourceUrl == null) {
            System.err.println("Resource not found: " + resourcePath);
            return;
        }
        Parent root = FXMLLoader.load(resourceUrl);
        bp.setCenter(root);
    }
}