package com.bsm.bsm.admin.userAccount;

import com.bsm.bsm.user.UserModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TableItemController {
    @FXML
    public RadioButton radioButton;
    @FXML
    public Button isEnabled;
    @FXML
    public Button isEnabledLabel;
    @FXML
    private Label idLabel, nameLabel, emailLabel, lastLoginLabel,dobLabel, phoneLabel, addressLabel;
    private UserModel userModel;
    @FXML
    private void handleRadioButtonClick() {
        UserAccountController.handleTableItemSelection(userModel);
    }
    @FXML
    private void initialize() {

    }
    public void setToggleGroup(ToggleGroup toggleGroup) {
        radioButton.setToggleGroup(toggleGroup);
    }
    @FXML
    private void handleTableItemDoubleClick(MouseEvent event) throws IOException {
        if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
            UserAccountController.handleTableItemDoubleClick(userModel);
        }
    }
    public void setUserModel(UserModel user) {
        this.userModel = user;
        idLabel.setText(user.getId());
        nameLabel.setText(user.getName());
        emailLabel.setText(user.getEmail());
        LocalDateTime lastLoginDateTime = LocalDateTime.parse(user.getLastLogin(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(lastLoginDateTime, now);
        LocalDate lastLoginDate = lastLoginDateTime.toLocalDate();
        long days = duration.toDays();
        long hours = duration.toHours() % 24;
        long minutes = duration.toMinutes() % 60;
//        // Construct the string representing the time elapsed since last login
        String timeElapsed = "";
        if (days > 0) {
            timeElapsed += lastLoginDate;
        }else {
            if (hours > 0) {
                timeElapsed += hours + "h" + " ago";
            }else {
                if (minutes > 0) {
                    timeElapsed += minutes + "m" + " ago";
                }
            }
        }

        // Set the last login label with the time elapsed
        lastLoginLabel.setText(timeElapsed );

        isEnabledLabel.setText(user.isEnabled() ? "Disable" : "Enable");
        if(!user.isEnabled()){
            isEnabledLabel.getStyleClass().add("enable-button");
        }else {
            isEnabledLabel.getStyleClass().add("disable-button");
        }
        //        dobLabel.setText(user.getDob());
//        phoneLabel.setText(user.getPhone());
//        addressLabel.setText(user.getAddress());
    }
}