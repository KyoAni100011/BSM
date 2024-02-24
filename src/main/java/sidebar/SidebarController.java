package main.java.sidebar;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

public class SidebarController {

    @FXML
    private Button bookRevenue;

    @FXML
    private BorderPane bp;

    @FXML
    private Button categoryRevenue;

    @FXML
    private Button revenueByCustomer;

    @FXML
    private Button revenueByEmployee;

    @FXML
    private Button userAccount;

    @FXML
    void SwitchBookRevenue(ActionEvent event) throws IOException {
        loadPage("bookRevenue");
    }

    @FXML
    void SwitchCategoryRevenue(ActionEvent event) throws IOException {
        loadPage("revenueByCustomer");
    }

    @FXML
    void SwitchRevenueByCustomer(ActionEvent event) throws IOException {
        loadPage("revenueByCustomer");
    }

    @FXML
    void SwitchRevenueByEmployee(ActionEvent event) throws IOException {
        loadPage("revenueByEmployee");
    }

    @FXML
    void SwitchUserAccount(ActionEvent event) throws IOException {
        loadPage("userAccount");
    }

    private void loadPage(String page) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../../resources/view/" + page + ".fxml"));
        bp.setCenter(root);
    }

}
