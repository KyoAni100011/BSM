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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
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
    private final SceneSwitch sceneSwitch = new SceneSwitch();
    public UserModel employeeInfo = UserSingleton.getInstance().getUser();
    public MenuItem buttonProfileSetting, buttonLogOut;

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
        loadPage("book/updateBook");
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
        loadPage("order/order");
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
        bp.setCenter(null);

        bp.setCenter(root);
    }
}
