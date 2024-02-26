package main.java.admin.profileSetting;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;

public class ProfileSettingController {

    public Button editProfileButton;
    public Button changePasswordButton;

    public BorderPane bp;

    @FXML
    private void initialize()  {
        try {
            loadPage("editProfile");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleEditProfile(ActionEvent event) throws IOException{
        System.out.println("Edit Profile button clicked");
        editProfileButton.setStyle("-fx-border-width: 0px 0px 2px; -fx-border-color: #914D2A; -fx-background-color: transparent; -fx-text-fill: #914D2A;");
        changePasswordButton.setStyle("-fx-background-color: #ffffff; -fx-border-color: #f4f4f4; -fx-border-width: 0px 0px 2px; -fx-text-fill: #000000;");
        loadPage("editProfile");
    }

    @FXML
    private void handleChangePassword(ActionEvent event) throws IOException {
        System.out.println("Change Password button clicked");
        changePasswordButton.setStyle("-fx-border-width: 0px 0px 2px; -fx-border-color: #914D2A; -fx-background-color: transparent; -fx-text-fill: #914D2A;");
        editProfileButton.setStyle("-fx-background-color: #ffffff; -fx-border-color: #f4f4f4; -fx-border-width: 0px 0px 2px; -fx-text-fill: #000000;");
        loadPage("changePassword");
    }

    private void loadPage(String page) throws IOException {
        String resourcePath = "../../../resources/view/admin/profileSetting/" + page + ".fxml";
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