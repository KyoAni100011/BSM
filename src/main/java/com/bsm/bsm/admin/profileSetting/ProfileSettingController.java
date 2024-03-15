package com.bsm.bsm.admin.profileSetting;

import com.bsm.bsm.user.UserSingleton;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class ProfileSettingController {

    public Button editProfileButton, changePasswordButton;

    public BorderPane bp;

    @FXML
    private void initialize() throws IOException {
        editProfileButton.getStyleClass().addAll("profile-setting-button", "profile-setting-button-admin");
        changePasswordButton.getStyleClass().add("profile-setting-button");
        loadPage("editProfile");
    }

    @FXML
    private void handleEditProfile(ActionEvent event) throws IOException {
        editProfileButton.getStyleClass().remove("profile-setting-button-admin");
        editProfileButton.getStyleClass().add("profile-setting-button-admin");
        changePasswordButton.getStyleClass().remove("profile-setting-button-admin");
        loadPage("editProfile");
    }

    @FXML
    private void handleChangePassword(ActionEvent event) throws IOException {
        changePasswordButton.getStyleClass().remove("profile-setting-button-admin");
        changePasswordButton.getStyleClass().add( "profile-setting-button-admin");
        editProfileButton.getStyleClass().remove( "profile-setting-button-admin");
        loadPage("changePassword");
    }

    private void loadPage(String page) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/bsm/bsm/view/admin/profileSetting/" + page + ".fxml")));
        bp.setCenter(root);
    }
}
