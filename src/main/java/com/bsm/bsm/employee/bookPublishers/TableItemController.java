package com.bsm.bsm.employee.bookPublishers;

import com.bsm.bsm.admin.userAccount.ToggleSwitch;
import com.bsm.bsm.publisher.Publisher;
import com.bsm.bsm.publisher.PublisherService;
import com.bsm.bsm.employee.bookPublishers.PublishersController;
import com.bsm.bsm.employee.bookPublishers.PublisherDetailController;
import com.bsm.bsm.utils.AlertUtils;
import com.bsm.bsm.utils.FXMLLoaderHelper;
import javafx.beans.property.BooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class TableItemController {
    private final PublisherService publisherService = new PublisherService();
    @FXML
    public ToggleButton toggleButton;
    @FXML
    public ToggleSwitch isOn;
    public Label introductionLabel, idLabel, nameLabel;

    private String id;
    private Publisher publisherModel;

    @FXML
    private void initialize() {
        PublishersController.handleTableItemSelection(null);
    }

    @FXML
    private void handleRadioButtonClick(){
        if(!toggleButton.isSelected()){
            PublishersController.handleTableItemSelection(null);
        } else {
            PublishersController.handleTableItemSelection(id);
            System.out.println("id: " + id);
        }
    }

    @FXML
    private void handleToggleSwitchClick() {
//        isOn.setUserId(id); // Pass the idLabel data to ToggleSwitch
        BooleanProperty oldState = isOn.switchedProperty();
        String confirmationMessage = "Are you sure you want to " + (!oldState.get() ? "enable" : "disable") + " this publisher?";
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation");
        confirmationAlert.setHeaderText(confirmationMessage);
        confirmationAlert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
//                if (oldState.get()) {
//                    if (!publisherService.disablePublisher(id)) {
//                        AlertUtils.showAlert("Error", "Failed to disable publisher", Alert.AlertType.ERROR);
//                        return;
//                    }
//                } else {
//                    if (!publisherService.enablePublisher(id)) {
//                        AlertUtils.showAlert("Error", "Failed to enable publisher", Alert.AlertType.ERROR);
//                        return;
//                    }
//                }
                AlertUtils.showAlert("Success", "Publisher has been " + (!oldState.get() ? "enabled" : "disabled"), Alert.AlertType.INFORMATION);
            } else {
                oldState.setValue(!oldState.get());
            }
        });
    }

    public void setToggleGroup(ToggleGroup toggleGroup) {
        toggleButton.setToggleGroup(toggleGroup);
    }

    @FXML
    void handleTableItemDoubleClick(MouseEvent event) throws IOException {
        if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
            if (id != null) {
                PublisherDetailController.handleTableItemSelection(id);
                FXMLLoaderHelper.loadFXML(new Stage(), "employee/bookPublishers/publisherDetail");
            } else {
                AlertUtils.showAlert("Error", "Can't find user", Alert.AlertType.ERROR);

            }
        }
    }

    public void setPublisherModel(Publisher publisher) {
        publisherModel = publisher;
        id = publisher.getId();
        idLabel.setText(publisher.getId());
        nameLabel.setText(publisher.getName());
        introductionLabel.setText(publisher.getAddress());
        isOn.setSwitchedProperty(publisher.isEnabled());
    }
}
