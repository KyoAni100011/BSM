package com.bsm.bsm.admin.userAccount;

import com.bsm.bsm.user.UserModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class UserAccountController implements Initializable {
    @FXML
    private VBox pnItems = null;

    private final UserAccountService userAccountService = new UserAccountService();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<UserModel> users = userAccountService.getAllUsersInfo();

        for (UserModel user : users) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/bsm/bsm/view/admin/userAccount/tableItem.fxml"));
                Node node = loader.load();
                // Assuming TableItemController has a method setUserModel
                TableItemController controller = loader.getController();
                controller.setUserModel(user);
                pnItems.getChildren().add(node);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
