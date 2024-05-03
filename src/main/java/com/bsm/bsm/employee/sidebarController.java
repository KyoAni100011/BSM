package com.bsm.bsm.employee;

import com.bsm.bsm.admin.AdminModel;
import com.bsm.bsm.user.UserController;
import com.bsm.bsm.user.UserService;
import com.bsm.bsm.utils.SceneSwitch;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;
import com.bsm.bsm.user.UserModel;
import com.bsm.bsm.user.UserSingleton;
import javafx.scene.control.MenuItem;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class sidebarController {
    private final SceneSwitch sceneSwitch = new SceneSwitch();
    private final UserModel employeeInfo = UserSingleton.getInstance().getUser();
    private final UserController userController = new UserService();

    @FXML
    public MenuItem buttonProfileSetting, buttonLogOut;
    @FXML
    public SVGPath svgAddSheet, svgAddOrder, svgOrder, svgViewSheet, svgProfileSetting, svgLogOut;
    @FXML
    public MenuButton menuButton;
    @FXML
    public Button addOrder, viewSheet, book, bookAuthors, bookCategories, bookPublishers, addSheet, order;
    @FXML
    public VBox bp;
    @FXML
    private Text roleText, nameText;
    @FXML
    private AnchorPane AnchorPaneEmployee;

    private static String  userName;

    public static void setUserName(String name)
    {
        userName = name;
    }

    public void initialize() throws IOException {
        menuButton.setVisible(true);
        updateNameText();
        updateRoleText();

        loadPage("order/createOrder");
        applyActiveStyles(addOrder);
    }

    private void updateNameText() {
        String name = userName != null ? userName.split(" ")[userName.split(" ").length - 1] : employeeInfo.getName().split(" ")[employeeInfo.getName().split(" ").length - 1];
        nameText.setText(name);
    }

    private void updateRoleText() {
        if (employeeInfo instanceof EmployeeModel) {
            roleText.setText("Employee");
        }
    }

    @FXML
    void SwitchBook(ActionEvent event) throws IOException {
        initialize();
        loadPage("book/book");
        applyActiveStyles(book);


    }

    @FXML
    void SwitchBookAuthors(ActionEvent event) throws IOException {
        initialize();
        loadPage("bookAuthors/bookAuthors");
        applyActiveStyles(bookAuthors);
    }
    @FXML
    void SwitchAddOrder(ActionEvent event) throws IOException {
        initialize();
        loadPage("order/createOrder");
        applyActiveStyles(addOrder);
    }

    @FXML
    void SwitchBookCategories(ActionEvent event) throws IOException {
        initialize();
        loadPage("bookCategories/bookCategories");
        applyActiveStyles(bookCategories);

    }

    @FXML
    void SwitchBookPublishers(ActionEvent event) throws IOException {
        initialize();
        loadPage("bookPublishers/bookPublishers");
        applyActiveStyles(bookPublishers);

    }

    @FXML
    void SwitchImportSheet(ActionEvent event) throws IOException {
        initialize();
        loadPage("importSheet/importSheet");
        applyActiveStyles(addSheet);

    }

    @FXML
    void SwitchViewSheet(ActionEvent event) throws  IOException {
        initialize();
        loadPage("importSheet/viewSheet");
        applyActiveStyles(viewSheet);

    }

    @FXML
    void SwitchOrder(ActionEvent event) throws IOException {
        initialize();
        loadPage("order/order");
        applyActiveStyles(order);

    }

    @FXML
    void handleLogOut(ActionEvent event) throws IOException {
        userController.logout();
        sceneSwitch.SceneSwitchDifferSize(AnchorPaneEmployee, "/com/bsm/bsm/view/login.fxml");
    }

    @FXML
    void handleProfileSetting(ActionEvent event) throws IOException {
        loadPage("profileSetting/profileSetting");
        menuButton.setVisible(false);
        applyActiveStyles(null);
    }

    private void loadPage(String page) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/bsm/bsm/view/employee/" + page + ".fxml")));
        bp.getChildren().clear();
        bp.getChildren().add(root);
    }

    private void applyActiveStyles(Button activeButton) {
        List<Button> buttons = Arrays.asList(addOrder, viewSheet, book, bookAuthors, bookCategories, bookPublishers, addSheet, order);
        List<SVGPath> svgPaths = Arrays.asList(svgAddSheet, svgAddOrder, svgOrder, svgViewSheet);

        for (Button button : buttons) {
            if (button == activeButton) {
                button.getStyleClass().remove("sideBarItemEmployeeActive");
                button.getStyleClass().add("sideBarItemEmployeeActive");
            } else {
                button.getStyleClass().remove("sideBarItemEmployeeActive");
            }
        }

        for (SVGPath svgPath : svgPaths) {
            String svgId = svgPath.getId().toLowerCase();
            if (activeButton == null) {
                svgPath.getStyleClass().remove("sideBarIconEmployeeActive");
                continue;
            }
            if (svgId.contains(activeButton.getId().toLowerCase()) && svgId.length() == activeButton.getId().length() + 3 ) {
                svgPath.getStyleClass().remove("sideBarIconEmployeeActive");
                svgPath.getStyleClass().add("sideBarIconEmployeeActive");
            } else {
                svgPath.getStyleClass().remove("sideBarIconEmployeeActive");
            }
        }

    }
    
}
