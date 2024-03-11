package com.bsm.bsm.admin.userAccount;

import com.bsm.bsm.user.UserModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class TableItemController {
    public Button isEnabled;
    @FXML
    private Label idLabel, nameLabel, emailLabel, dobLabel, phoneLabel, addressLabel;


    public void setUserModel(UserModel user) {
        idLabel.setText(user.getId());
        nameLabel.setText(user.getName());
        emailLabel.setText(user.getEmail());
        dobLabel.setText(user.getDob());
//        phoneLabel.setText(user.getPhone());
//        addressLabel.setText(user.getAddress());
//        isEnabledLabel.setText(user.isEnabled() ? "Enabled" : "Disabled");
    }
}