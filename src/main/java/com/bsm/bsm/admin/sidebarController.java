package com.bsm.bsm.admin;

import com.bsm.bsm.employee.EmployeeModel;
import com.bsm.bsm.user.UserController;
import com.bsm.bsm.user.UserModel;
import com.bsm.bsm.user.UserService;
import com.bsm.bsm.user.UserSingleton;
import com.bsm.bsm.utils.SceneSwitch;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;
import com.bsm.bsm.utils.FXMLLoaderHelper;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

public class sidebarController {
    private final SceneSwitch sceneSwitch = new SceneSwitch();
    public UserModel adminInfo = UserSingleton.getInstance().getUser();
    public MenuItem buttonProfileSetting, buttonLogOut;
    public SVGPath svgCategory;
    @FXML
    private Button bookRevenue;

    @FXML
    private VBox bp;

    @FXML
    private Button categoryRevenue;

    @FXML
    private Text roleText;

    @FXML
    private Text nameText;

    @FXML
    private Button btnLogOut;

    @FXML
    private Button revenueByCustomer;

    @FXML
    private Button revenueByEmployee;

    @FXML
    private Button userAccount;

    @FXML
    private AnchorPane AnchorPaneAdmin;

    private UserController userController = null;

    public sidebarController()
    {
        userController = new UserService();
    }

    @FXML
    public void initialize()
    {
        new sidebarController();

        String[] nameParts = adminInfo.getName().split(" ");
        nameText.setText(nameParts[nameParts.length - 1]);

        if (adminInfo instanceof AdminModel) {
            roleText.setText("Admin");
        }
    }

    @FXML
    void SwitchBookRevenue(ActionEvent event) throws IOException {
        loadPage("bookRevenue/bookRevenue");
        bookRevenue.getStyleClass().add("sideBarItemActive");
        categoryRevenue.getStyleClass().remove("sideBarItemActive");
        revenueByCustomer.getStyleClass().remove("sideBarItemActive");
        userAccount.getStyleClass().remove("sideBarItemActive");
        revenueByEmployee.getStyleClass().remove("sideBarItemActive");
        svgCategory.getStyleClass().remove("sideBarIconActive");
    }

    @FXML
    void SwitchCategoryRevenue(ActionEvent event) throws IOException {
        loadPage("categoryRevenue/categoryRevenue");
        categoryRevenue.getStyleClass().add("sideBarItemActive");
        revenueByCustomer.getStyleClass().remove("sideBarItemActive");
        userAccount.getStyleClass().remove("sideBarItemActive");
        bookRevenue.getStyleClass().remove("sideBarItemActive");
        revenueByEmployee.getStyleClass().remove("sideBarItemActive");
        svgCategory.getStyleClass().add("sideBarIconActive");
    }

    @FXML
    void SwitchRevenueByCustomer(ActionEvent event) throws IOException {
        loadPage("revenueByCustomer/revenueByCustomer");
        categoryRevenue.getStyleClass().remove("sideBarItemActive");
        userAccount.getStyleClass().remove("sideBarItemActive");
        bookRevenue.getStyleClass().remove("sideBarItemActive");
        revenueByEmployee.getStyleClass().remove("sideBarItemActive");
        svgCategory.getStyleClass().remove("sideBarIconActive");
        revenueByCustomer.getStyleClass().add("sideBarItemActive");


    }

    @FXML
    void SwitchRevenueByEmployee(ActionEvent event) throws IOException {
//        loadPage("revenueByEmployee/revenueByEmployee");
        loadPage("userAccount/passwordReset");
        revenueByEmployee.getStyleClass().add("sideBarItemActive");
        categoryRevenue.getStyleClass().remove("sideBarItemActive");
        revenueByCustomer.getStyleClass().remove("sideBarItemActive");
        userAccount.getStyleClass().remove("sideBarItemActive");
        bookRevenue.getStyleClass().remove("sideBarItemActive");
        svgCategory.getStyleClass().remove("sideBarIconActive");

    }

    @FXML
    void SwitchUserAccount(ActionEvent event) throws IOException {
        loadPage("userAccount/userAccount");
        revenueByEmployee.getStyleClass().remove("sideBarItemActive");
        userAccount.getStyleClass().add("sideBarItemActive");
        categoryRevenue.getStyleClass().remove("sideBarItemActive");
        revenueByCustomer.getStyleClass().remove("sideBarItemActive");
        bookRevenue.getStyleClass().remove("sideBarItemActive");
        svgCategory.getStyleClass().remove("sideBarIconActive");

    }

    @FXML
    void handleLogOut(ActionEvent event) throws IOException {
        userController.logout();
        sceneSwitch.SceneSwitchDifferSize(AnchorPaneAdmin, "/com/bsm/bsm/view/login.fxml");
    }

    @FXML
    void handleProfileSetting(ActionEvent event) throws IOException {
        loadPage("profileSetting/profileSetting");
    }

    private void loadPage(String page) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/bsm/bsm/view/admin/" + page + ".fxml")));
        bp.getChildren().clear();
        bp.getChildren().add(root);
    }
}
