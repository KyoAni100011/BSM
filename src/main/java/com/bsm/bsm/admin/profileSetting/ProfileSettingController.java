package com.bsm.bsm.admin.profileSetting;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;

public class ProfileSettingController {

    public Button editProfileButton;
    public Button changePasswordButton;

    public BorderPane bp;

    @FXML
    private void initialize() {
        try {
            editProfileButton.getStyleClass().add("profile-setting-button-selected");
            changePasswordButton.getStyleClass().remove("profile-setting-button-selected");
            loadPage("editProfile");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleEditProfile(ActionEvent event) throws IOException {
        editProfileButton.getStyleClass().add("profile-setting-button-selected");
        changePasswordButton.getStyleClass().remove("profile-setting-button-selected");
        loadPage("editProfile");
    }

    @FXML
    private void handleChangePassword(ActionEvent event) throws IOException {
        changePasswordButton.getStyleClass().add("profile-setting-button-selected");
        editProfileButton.getStyleClass().remove("profile-setting-button-selected");
        loadPage("changePassword");
    }

    private void loadPage(String page) throws IOException {
        String resourcePath = "/com/bsm/bsm/view/" + page + ".fxml";
        System.out.println("Resource Path: " + resourcePath);
        URL resourceUrl = getClass().getResource(resourcePath);
        if (resourceUrl == null) {
            System.err.println("Resource not found: " + resourcePath);
            return;
        }
        Parent root = FXMLLoader.load(resourceUrl);
        bp.setCenter(root);
    }
}