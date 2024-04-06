package com.bsm.bsm.employee.bookCategories;


import com.bsm.bsm.author.Author;
import com.bsm.bsm.employee.bookAuthors.AuthorController;
import com.bsm.bsm.publisher.Publisher;
import com.bsm.bsm.publisher.PublisherService;
import com.bsm.bsm.utils.AlertUtils;
import com.bsm.bsm.utils.FXMLLoaderHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;


public class TableItemController {
    private final PublisherService publisherService = new PublisherService();

    @FXML
    private Label introductionlLabel;

    @FXML
    private Label idLabel;

    @FXML
    private Button isEnabledButton;

    @FXML
    private Label nameLabel;

    @FXML
    private RadioButton radioButton;

    @FXML
    private String name;

    public void setToggleGroup(ToggleGroup toggleGroup) {
        radioButton.setToggleGroup(toggleGroup);
    }

    @FXML
    void handleIsEnabledButtonClick(ActionEvent event) {
        String action = isEnabledButton.getText().equals("Enable") ? "disable" : "enable";
        String confirmationMessage = "Are you sure you want to " + action + " this user?";

        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation");
        confirmationAlert.setHeaderText(confirmationMessage);
//        confirmationAlert.setContentText("Click OK to confirm.");

        confirmationAlert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                if (isEnabledButton.getText().equals("Enable")) {
                    // test active author
                    if (!publisherService.setEnable(true)) {
                        AlertUtils.showAlert("Error", "Failed to disable user", Alert.AlertType.ERROR);
                        return;
                    }

                    isEnabledButton.setText("Disable");
                    isEnabledButton.getStyleClass().remove("enable-button");
                    isEnabledButton.getStyleClass().add("disable-button");
                    AlertUtils.showAlert("Success", "User disable successfully", Alert.AlertType.INFORMATION);

                } else {
                    // test active author
                    if (!publisherService.setEnable(true)) {
                        AlertUtils.showAlert("Error", "Failed to enable user", Alert.AlertType.ERROR);
                        return;
                    }

                    isEnabledButton.setText("Enable");
                    isEnabledButton.getStyleClass().remove("disable-button");
                    isEnabledButton.getStyleClass().add("enable-button");
                    AlertUtils.showAlert("Success", "User enable successfully", Alert.AlertType.INFORMATION);
                }
            }
        });
    }

    @FXML
    void handleRadioButtonClick(ActionEvent event) {
        BookCategoriesController.handleTableItemSelection(name);
    }

    @FXML
    void handleTableItemDoubleClick(MouseEvent event) throws IOException {
        if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
            if (name != null) {
                BookCategoriesController.handleTableItemSelection(name);
                FXMLLoaderHelper.loadFXML(new Stage(), "admin/userAccount/userDetail");
            } else {
                AlertUtils.showAlert("Error", "Can't find user", Alert.AlertType.ERROR);

            }
        }
    }

    public void setPublisherModel(Publisher publisher) {
        idLabel.setText(publisher.getId());
        nameLabel.setText(publisher.getName());
        introductionlLabel.setText(publisher.getAddress());

        isEnabledButton.setText(publisher.isEnabled() ? "Enable" : "Disable");
        if(publisher.isEnabled()){
            isEnabledButton.getStyleClass().add("enable-button");
        }else {
            isEnabledButton.getStyleClass().add("disable-button");
        }
    }

}
