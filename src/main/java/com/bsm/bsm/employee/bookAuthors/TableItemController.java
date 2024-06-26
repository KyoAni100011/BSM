package com.bsm.bsm.employee.bookAuthors;


import com.bsm.bsm.admin.userAccount.ToggleSwitch;
import com.bsm.bsm.author.Author;
import com.bsm.bsm.author.AuthorService;
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
    private final AuthorService authorService = new AuthorService();
    @FXML
    public ToggleButton toggleButton;
    @FXML
    public ToggleSwitch isOn;
    public Label introductionLabel, idLabel, nameLabel;

    private String id;
    private Author authorModel;

    @FXML
    private void initialize() {
        AuthorController.handleTableItemSelection(null);
    }

    @FXML
    private void handleRadioButtonClick(){
        if(!toggleButton.isSelected()){
            AuthorController.handleTableItemSelection(null);
        } else {
            AuthorController.handleTableItemSelection(id);
    }
}

    @FXML
    private void handleToggleSwitchClick() {
        BooleanProperty oldState = isOn.switchedProperty();
        String confirmationMessage = "Are you sure you want to " + (!oldState.get() ? "enable" : "disable") + " this author?";
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation");
        confirmationAlert.setHeaderText(confirmationMessage);
        confirmationAlert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                if (oldState.get()) {
                    if (!authorService.setEnable(id, false)) {
                        AlertUtils.showAlert("Error", "Failed to disable author", Alert.AlertType.ERROR);
                        return;
                    }
                } else {
                    if (!authorService.setEnable(id, true)) {
                        AlertUtils.showAlert("Error", "Failed to enable author", Alert.AlertType.ERROR);
                        return;
                    }
                }
                isOn.setSwitchedProperty(!oldState.get());
                authorModel.setEnabled(!oldState.get());
                AlertUtils.showAlert("Success", "Author has been " + (oldState.get() ? "enabled" : "disabled"), Alert.AlertType.INFORMATION);
            } else {
                isOn.setSwitchedProperty(oldState.get());
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
                if (!authorService.isEnabled(id)) {
                    AlertUtils.showAlert("Error", "Need to enable author to view details", Alert.AlertType.ERROR);
                    return;
                }
                AuthorDetailController.handleTableItemSelection(id);
                FXMLLoaderHelper.loadFXML(new Stage(), "employee/bookAuthors/authorDetail", "Author Detail");
            } else {
                AlertUtils.showAlert("Error", "Can't find user", Alert.AlertType.ERROR);

            }
        }
    }

    public void setAuthorModel(Author author) {
        authorModel = author;
        id = author.getId();
        idLabel.setText(author.getId());
        nameLabel.setText(author.getName());
        introductionLabel.setText(author.getIntroduction());
        isOn.setSwitchedProperty(author.isEnabled());
    }

}
