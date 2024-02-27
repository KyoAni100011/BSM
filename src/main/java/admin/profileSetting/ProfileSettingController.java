package main.java.admin.profileSetting;

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
            loadPage("editProfile");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleEditProfile(ActionEvent event) throws IOException {




        loadPage("editProfile");
    }

    @FXML
    private void handleChangePassword(ActionEvent event) throws IOException {


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