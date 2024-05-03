package com.bsm.bsm.admin;

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
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.Objects;

public class sidebarController {
    private final SceneSwitch sceneSwitch = new SceneSwitch();
    private final UserModel adminInfo = UserSingleton.getInstance().getUser();
    private final UserController userController = new UserService();

    @FXML
    public MenuItem buttonProfileSetting, buttonLogOut;
    @FXML
    public SVGPath svgCategory;
    @FXML
    public MenuButton menuButton;
    @FXML private Button bookRevenue, categoryRevenue, revenueByCustomer, revenueByEmployee, userAccount, btnLogOut, logoButton;
    @FXML private VBox bp;
    @FXML private Text roleText, nameText;
    @FXML private AnchorPane anchorPaneAdmin;

    private static String  userName;

    public static void setUserName(String name)
    {
        userName = name;
    }

    @FXML
    public void initialize() {
        menuButton.setVisible(true);
        updateNameText();
        updateRoleText();
    }

    private void updateNameText() {
        String name = userName != null ? userName.split(" ")[userName.split(" ").length - 1] : adminInfo.getName().split(" ")[adminInfo.getName().split(" ").length - 1];
        nameText.setText(name);
    }

    private void updateRoleText() {
        if (adminInfo instanceof AdminModel) {
            roleText.setText("Admin");
        }
    }

    @FXML
    void SwitchBookRevenue(ActionEvent event) throws IOException {
        initialize();
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
        initialize();
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
        initialize();
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
        initialize();
        loadPage("revenueByEmployee/revenueByEmployee");
        revenueByEmployee.getStyleClass().add("sideBarItemActive");
        categoryRevenue.getStyleClass().remove("sideBarItemActive");
        revenueByCustomer.getStyleClass().remove("sideBarItemActive");
        userAccount.getStyleClass().remove("sideBarItemActive");
        bookRevenue.getStyleClass().remove("sideBarItemActive");
        svgCategory.getStyleClass().remove("sideBarIconActive");

    }

    @FXML
    void SwitchUserAccount(ActionEvent event) throws IOException {
        initialize();
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
        sceneSwitch.SceneSwitchDifferSize(anchorPaneAdmin, "/com/bsm/bsm/view/login.fxml");
    }

    @FXML
    void handleProfileSetting(ActionEvent event) throws IOException {
        loadPage("profileSetting/profileSetting");
        menuButton.setVisible(false);
    }

    private void loadPage(String page) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/bsm/bsm/view/admin/" + page + ".fxml")));
        bp.getChildren().clear();
        bp.getChildren().add(root);
    }
}
