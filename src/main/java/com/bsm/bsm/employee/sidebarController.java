package com.bsm.bsm.employee;

import java.io.IOException;
import java.util.Objects;

import com.bsm.bsm.user.UserController;
import com.bsm.bsm.user.UserService;
import com.bsm.bsm.utils.FXMLLoaderHelper;
import com.bsm.bsm.utils.SceneSwitch;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;
import com.bsm.bsm.user.UserModel;
import com.bsm.bsm.user.UserSingleton;
import javafx.scene.control.MenuItem;
import com.bsm.bsm.utils.FXMLLoaderHelper;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;


public class sidebarController {
    private final SceneSwitch sceneSwitch = new SceneSwitch();
    public UserModel employeeInfo = UserSingleton.getInstance().getUser();
    public MenuItem buttonProfileSetting, buttonLogOut;
    public VBox bp;
    public Button addOrder;
    public Button viewSheet;
    public SVGPath svgAddSheet;
    public SVGPath svgAddOrder;
    public SVGPath svgOrder;
    public SVGPath svgViewSheet;
    public SVGPath svgProfileSetting;
    public SVGPath svgLogOut;


    @FXML
    private AnchorPane AnchorPaneEmployee;

    @FXML
    private Button book;

    @FXML
    private Button bookAuthors;

    @FXML
    private Button bookCategories;

    @FXML
    private Button bookPublishers;


    @FXML
    private Button importSheet;
    @FXML
    private Text roleText;

    @FXML
    private Text nameText;

    @FXML
    private Button order;

    private UserController userController = null;

    public sidebarController()
    {
        userController = new UserService();
    }

    public void initialize()
    {
        new sidebarController();
        String[] name = employeeInfo.getName().split(" ");
        nameText.setText(name[name.length - 1]);
        if (employeeInfo instanceof EmployeeModel) {
            roleText.setText("Employee");
        }
    }

    @FXML
    void SwitchBook(ActionEvent event) throws IOException {
        loadPage("book/book");
        book.getStyleClass().add("sideBarItemEmployeeActive");
        bookAuthors.getStyleClass().remove("sideBarItemEmployeeActive");
        addOrder.getStyleClass().remove("sideBarItemEmployeeActive");
        bookCategories.getStyleClass().remove("sideBarItemEmployeeActive");
        bookPublishers.getStyleClass().remove("sideBarItemEmployeeActive");
        importSheet.getStyleClass().remove("sideBarItemEmployeeActive");
        viewSheet.getStyleClass().remove("sideBarItemEmployeeActive");
        order.getStyleClass().remove("sideBarItemEmployeeActive");
        svgViewSheet.getStyleClass().remove("sideBarIconEmployeeActive");
        svgAddSheet.getStyleClass().remove("sideBarIconEmployeeActive");
        svgAddOrder.getStyleClass().remove("sideBarIconEmployeeActive");
        svgOrder.getStyleClass().remove("sideBarIconEmployeeActive");

    }

    @FXML
    void SwitchBookAuthors(ActionEvent event) throws IOException {
        loadPage("bookAuthors/bookAuthors");
        bookAuthors.getStyleClass().add("sideBarItemEmployeeActive");
        book.getStyleClass().remove("sideBarItemEmployeeActive");
        addOrder.getStyleClass().remove("sideBarItemEmployeeActive");
        bookCategories.getStyleClass().remove("sideBarItemEmployeeActive");
        bookPublishers.getStyleClass().remove("sideBarItemEmployeeActive");
        importSheet.getStyleClass().remove("sideBarItemEmployeeActive");
        viewSheet.getStyleClass().remove("sideBarItemEmployeeActive");
        order.getStyleClass().remove("sideBarItemEmployeeActive");
        svgViewSheet.getStyleClass().remove("sideBarIconEmployeeActive");
        svgAddSheet.getStyleClass().remove("sideBarIconEmployeeActive");
        svgAddOrder.getStyleClass().remove("sideBarIconEmployeeActive");
        svgOrder.getStyleClass().remove("sideBarIconEmployeeActive");
    }
    @FXML
    void SwitchAddOrder(ActionEvent event) throws IOException {
        loadPage("order/createOrder");
        addOrder.getStyleClass().add("sideBarItemEmployeeActive");
        bookAuthors.getStyleClass().remove("sideBarItemEmployeeActive");
        book.getStyleClass().remove("sideBarItemEmployeeActive");
        bookCategories.getStyleClass().remove("sideBarItemEmployeeActive");
        bookPublishers.getStyleClass().remove("sideBarItemEmployeeActive");
        importSheet.getStyleClass().remove("sideBarItemEmployeeActive");
        viewSheet.getStyleClass().remove("sideBarItemEmployeeActive");
        order.getStyleClass().remove("sideBarItemEmployeeActive");
        svgViewSheet.getStyleClass().remove("sideBarIconEmployeeActive");
        svgAddSheet.getStyleClass().remove("sideBarIconEmployeeActive");
        svgAddOrder.getStyleClass().add("sideBarIconEmployeeActive");
        svgOrder.getStyleClass().remove("sideBarIconEmployeeActive");
    }

    @FXML
    void SwitchBookCategories(ActionEvent event) throws IOException {
        loadPage("bookCategories/bookCategories");
        bookCategories.getStyleClass().add("sideBarItemEmployeeActive");
        bookAuthors.getStyleClass().remove("sideBarItemEmployeeActive");
        book.getStyleClass().remove("sideBarItemEmployeeActive");
        addOrder.getStyleClass().remove("sideBarItemEmployeeActive");
        bookPublishers.getStyleClass().remove("sideBarItemEmployeeActive");
        importSheet.getStyleClass().remove("sideBarItemEmployeeActive");
        viewSheet.getStyleClass().remove("sideBarItemEmployeeActive");
        order.getStyleClass().remove("sideBarItemEmployeeActive");
        svgViewSheet.getStyleClass().remove("sideBarIconEmployeeActive");
        svgAddSheet.getStyleClass().remove("sideBarIconEmployeeActive");
        svgAddOrder.getStyleClass().remove("sideBarIconEmployeeActive");
        svgOrder.getStyleClass().remove("sideBarIconEmployeeActive");
    }

    @FXML
    void SwitchBookPublishers(ActionEvent event) throws IOException {
        loadPage("bookPublishers/bookPublishers");
        bookPublishers.getStyleClass().add("sideBarItemEmployeeActive");
        bookAuthors.getStyleClass().remove("sideBarItemEmployeeActive");
        book.getStyleClass().remove("sideBarItemEmployeeActive");
        addOrder.getStyleClass().remove("sideBarItemEmployeeActive");
        bookCategories.getStyleClass().remove("sideBarItemEmployeeActive");
        importSheet.getStyleClass().remove("sideBarItemEmployeeActive");
        viewSheet.getStyleClass().remove("sideBarItemEmployeeActive");
        order.getStyleClass().remove("sideBarItemEmployeeActive");
        svgViewSheet.getStyleClass().remove("sideBarIconEmployeeActive");
        svgAddSheet.getStyleClass().remove("sideBarIconEmployeeActive");
        svgAddOrder.getStyleClass().remove("sideBarIconEmployeeActive");
        svgOrder.getStyleClass().remove("sideBarIconEmployeeActive");
    }

    @FXML
    void SwitchImportSheet(ActionEvent event) throws IOException {
        loadPage("importSheet/importSheet");
        importSheet.getStyleClass().add("sideBarItemEmployeeActive");
        bookAuthors.getStyleClass().remove("sideBarItemEmployeeActive");
        book.getStyleClass().remove("sideBarItemEmployeeActive");
        addOrder.getStyleClass().remove("sideBarItemEmployeeActive");
        bookCategories.getStyleClass().remove("sideBarItemEmployeeActive");
        bookPublishers.getStyleClass().remove("sideBarItemEmployeeActive");
        viewSheet.getStyleClass().remove("sideBarItemEmployeeActive");
        order.getStyleClass().remove("sideBarItemEmployeeActive");
        svgViewSheet.getStyleClass().remove("sideBarIconEmployeeActive");
        svgAddSheet.getStyleClass().add("sideBarIconEmployeeActive");
        svgAddOrder.getStyleClass().remove("sideBarIconEmployeeActive");
        svgOrder.getStyleClass().remove("sideBarIconEmployeeActive");
    }

    @FXML
    void SwitchViewSheet(ActionEvent event) throws  IOException {
        loadPage("importSheet/viewSheet");
        viewSheet.getStyleClass().add("sideBarItemEmployeeActive");
        bookAuthors.getStyleClass().remove("sideBarItemEmployeeActive");
        book.getStyleClass().remove("sideBarItemEmployeeActive");
        addOrder.getStyleClass().remove("sideBarItemEmployeeActive");
        bookCategories.getStyleClass().remove("sideBarItemEmployeeActive");
        bookPublishers.getStyleClass().remove("sideBarItemEmployeeActive");
        importSheet.getStyleClass().remove("sideBarItemEmployeeActive");
        order.getStyleClass().remove("sideBarItemEmployeeActive");
        svgViewSheet.getStyleClass().add("sideBarIconEmployeeActive");
        svgAddSheet.getStyleClass().remove("sideBarIconEmployeeActive");
        svgAddOrder.getStyleClass().remove("sideBarIconEmployeeActive");
        svgOrder.getStyleClass().remove("sideBarIconEmployeeActive");
    }

    @FXML
    void SwitchOrder(ActionEvent event) throws IOException {
        loadPage("order/order");
        order.getStyleClass().add("sideBarItemEmployeeActive");
        bookAuthors.getStyleClass().remove("sideBarItemEmployeeActive");
        book.getStyleClass().remove("sideBarItemEmployeeActive");
        addOrder.getStyleClass().remove("sideBarItemEmployeeActive");
        bookCategories.getStyleClass().remove("sideBarItemEmployeeActive");
        bookPublishers.getStyleClass().remove("sideBarItemEmployeeActive");
        importSheet.getStyleClass().remove("sideBarItemEmployeeActive");
        viewSheet.getStyleClass().remove("sideBarItemEmployeeActive");
        svgViewSheet.getStyleClass().remove("sideBarIconEmployeeActive");
        svgAddSheet.getStyleClass().remove("sideBarIconEmployeeActive");
        svgAddOrder.getStyleClass().remove("sideBarIconEmployeeActive");
        svgOrder.getStyleClass().add("sideBarIconEmployeeActive");
    }

    @FXML
    void handleLogOut(ActionEvent event) throws IOException {
        userController.logout();
        sceneSwitch.SceneSwitchDifferSize(AnchorPaneEmployee, "/com/bsm/bsm/view/login.fxml");
    }

    @FXML
    void handleProfileSetting(ActionEvent event) throws IOException {
        loadPage("profileSetting/profileSetting");
    }

    private void loadPage(String page) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/bsm/bsm/view/employee/" + page + ".fxml")));
        bp.getChildren().clear();
        bp.getChildren().add(root);
    }
}
