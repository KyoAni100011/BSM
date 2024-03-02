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


public class sidebarController {

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
    private Button order;

    @FXML
    void SwitchBook(ActionEvent event) throws IOException {
        loadPage("book");
    }

    @FXML
    void SwitchBookAuthors(ActionEvent event) throws IOException {
        loadPage("bookAuthors");
    }

    @FXML
    void SwitchBookCategories(ActionEvent event) throws IOException {
        loadPage("bookCategories");
    }

    @FXML
    void SwitchBookPublishers(ActionEvent event) throws IOException {
        loadPage("bookPublishers");
    }

    @FXML
    void SwitchImportSheet(ActionEvent event) throws IOException {
        loadPage("importSheets");
    }

    @FXML
    void SwitchOrder(ActionEvent event) throws IOException {
        loadPage("order");
    }

    @FXML
    void handleLogOut(ActionEvent event) throws IOException {
        FXMLLoaderHelper.loadFXML((Stage) bp.getScene().getWindow(), "login");
    }

    private void loadPage(String page) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/bsm/bsm/view/" + page + ".fxml")));
        bp.setCenter(root);
    }
}
