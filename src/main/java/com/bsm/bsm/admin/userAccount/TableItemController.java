package com.bsm.bsm.admin.userAccount;

import com.bsm.bsm.account.AccountService;
import com.bsm.bsm.user.UserModel;
import com.bsm.bsm.utils.AlertUtils;
import com.bsm.bsm.utils.FXMLLoaderHelper;
import javafx.beans.property.BooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TableItemController {
    private AccountService accountService = new AccountService();
    @FXML
    public RadioButton radioButton;
    @FXML
    public ToggleSwitch isOn;
    @FXML
    private Label idLabel, nameLabel, emailLabel, lastLoginLabel,dobLabel, phoneLabel, addressLabel;
    @FXML
    private String email;
    @FXML
    private UserModel userModel;

    @FXML
    private void handleRadioButtonClick() {
        UserAccountController.handleTableItemSelection(email);
    }

    @FXML
    private void initialize() {

    }

    @FXML
    private void handleToggleSwitchClick() {
        isOn.setUserId(idLabel.getText()); // Pass the idLabel data to ToggleSwitch
        BooleanProperty oldState = isOn.switchedProperty();
        String confirmationMessage = "Are you sure you want to " + (!oldState.get() ? "enable" : "disable") + " this user?";
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation");
        confirmationAlert.setHeaderText(confirmationMessage);
        confirmationAlert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                if (oldState.get()) {
                    if (!accountService.disableUser(idLabel.getText())) {
                        AlertUtils.showAlert("Error", "Failed to disable user", Alert.AlertType.ERROR);
                        return;
                    }
                    isOn.setSwitchedProperty(!oldState.get());
                    userModel.setEnabled(oldState.get()); // Update userModel's property
                    System.out.println("User disabled successfully" + !oldState.get());
                    AlertUtils.showAlert("Success", "User disabled successfully", Alert.AlertType.INFORMATION);
                } else {
                    if (!accountService.enableUser(idLabel.getText())) {
                        AlertUtils.showAlert("Error", "Failed to enable user", Alert.AlertType.ERROR);
                        return;
                    }
                    isOn.setSwitchedProperty(!oldState.get());
                    userModel.setEnabled(oldState.get()); // Update userModel's property
                    System.out.println("User enabled successfully" + !oldState.get());
                    AlertUtils.showAlert("Success", "User enabled successfully", Alert.AlertType.INFORMATION);
                }
            }
        });
    }

    public void setToggleGroup(ToggleGroup toggleGroup) {
        radioButton.setToggleGroup(toggleGroup);
    }
    @FXML
    private void handleTableItemDoubleClick(MouseEvent event) throws IOException {
        if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
            if (email != null) {
                UserDetailController.handleTableItemSelection(email);
                FXMLLoaderHelper.loadFXML(new Stage(), "admin/userAccount/userDetail");
            } else {
                AlertUtils.showAlert("Error", "Can't find user", Alert.AlertType.ERROR);

            }
        }
    }


    public void setUserModel(UserModel user) {
        userModel = user;
        email = user.getEmail();
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
        isOn.setSwitchedProperty(user.isEnabled());
    }
}