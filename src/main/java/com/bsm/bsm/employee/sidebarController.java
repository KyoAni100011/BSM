package com.bsm.bsm.employee;

import java.io.IOException;
import java.util.Objects;

import com.bsm.bsm.utils.FXMLLoaderHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import com.bsm.bsm.user.UserModel;
import com.bsm.bsm.user.UserSingleton;
import javafx.scene.control.MenuItem;
import com.bsm.bsm.utils.FXMLLoaderHelper;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.Objects;


public class sidebarController {
    public UserModel adminInfo = UserSingleton.getInstance().getUser();
    public MenuItem buttonProfileSetting, buttonLogOut;

    @FXML
    private Button book;

    @FXML
    private Button bookAuthors;

    @FXML
    private Button bookCategories;

    @FXML
    private Button bookPublishers;

    @FXML
    private BorderPane bp;

    @FXML
    private Button btnLogOut;

    @FXML
    private Button importSheet;
    @FXML
    private Text roleText;

    @FXML
    private Text nameText;

    @FXML
    private Button order;


    public void initialize()
    {
        nameText.setText(adminInfo.getName().split(" ")[1]);
        roleText.setText("Admin");
    }


    @FXML
    void SwitchBook(ActionEvent event) throws IOException {
        loadPage("book/book");
    }

    @FXML
    void SwitchBookAuthors(ActionEvent event) throws IOException {
        loadPage("bookAuthors/bookAuthors");
    }

    @FXML
    void SwitchBookCategories(ActionEvent event) throws IOException {
        loadPage("bookCategories/bookCategories");
    }

    @FXML
    void SwitchBookPublishers(ActionEvent event) throws IOException {
        loadPage("bookPublishers/bookPublishers");
    }

    @FXML
    void SwitchImportSheet(ActionEvent event) throws IOException {
        loadPage("importSheets/importSheets");
    }

    @FXML
    void SwitchOrder(ActionEvent event) throws IOException {
        loadPage("profileSetting/profileSetting");
    }

    @FXML
    void handleLogOut(ActionEvent event) throws IOException {
        FXMLLoaderHelper.loadFXML((Stage) bp.getScene().getWindow(), "login");
    }

    @FXML
    void handleProfileSetting(ActionEvent event) throws IOException {
        loadPage("profileSetting/profileSetting");
    }

    private void loadPage(String page) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/bsm/bsm/view/employee/" + page + ".fxml")));
        bp.setCenter(root);
    }
}
