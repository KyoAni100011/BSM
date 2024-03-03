package com.bsm.bsm.admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import com.bsm.bsm.utils.FXMLLoaderHelper;

import java.io.IOException;
import java.util.Objects;

public class sidebarController {
    @FXML
    private Button bookRevenue;

    @FXML
    private BorderPane bp;

    @FXML
    private Button categoryRevenue;

    @FXML
    private Button btnLogOut;

    @FXML
    private Button revenueByCustomer;

    @FXML
    private Button revenueByEmployee;

    @FXML
    private Button userAccount;

    @FXML
    void SwitchBookRevenue(ActionEvent event) throws IOException {
        loadPage("bookRevenue/bookRevenue");
    }

    @FXML
    void SwitchCategoryRevenue(ActionEvent event) throws IOException {
        loadPage("categoryRevenue/categoryRevenue");
    }

    @FXML
    void SwitchRevenueByCustomer(ActionEvent event) throws IOException {
        loadPage("revenueByCustomer/revenueByCustomer");
    }

    @FXML
    void SwitchRevenueByEmployee(ActionEvent event) throws IOException {
        loadPage("revenueByEmployee/revenueByEmployee");
    }

    @FXML
    void SwitchUserAccount(ActionEvent event) throws IOException {
        loadPage("profileSetting/profileSetting");
    }

    @FXML
    void handleLogOut(ActionEvent event) throws IOException {
        FXMLLoaderHelper.loadFXML((Stage) bp.getScene().getWindow(), "login");
    }

    private void loadPage(String page) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/bsm/bsm/view/admin/" + page + ".fxml")));
        bp.setCenter(root);
    }

}
