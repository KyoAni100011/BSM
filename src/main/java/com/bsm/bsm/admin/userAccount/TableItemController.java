package com.bsm.bsm.admin.userAccount;

import com.bsm.bsm.user.UserModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TableItemController {
    public Button isEnabled;
    public Button isEnabledLabel;
    @FXML
    private Label idLabel, nameLabel, emailLabel, lastLoginLabel,dobLabel, phoneLabel, addressLabel;


    public void setUserModel(UserModel user) {
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

        isEnabledLabel.setText(user.isEnabled() ? "Enable" : "Disable");
        if(user.isEnabled()){
            isEnabledLabel.getStyleClass().add("enable-button");
        }else {
            isEnabledLabel.getStyleClass().add("disable-button");
        }
        //        dobLabel.setText(user.getDob());
//        phoneLabel.setText(user.getPhone());
//        addressLabel.setText(user.getAddress());
    }
}