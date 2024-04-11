package com.bsm.bsm.employee.bookCategories;

import com.bsm.bsm.admin.userAccount.ToggleSwitch;
import com.bsm.bsm.category.Category;
import com.bsm.bsm.category.CategoryService;
import com.bsm.bsm.employee.bookCategories.CategoriesController;
import com.bsm.bsm.employee.bookCategories.CategoryDetailController;
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
    private final CategoryService categoryService = new CategoryService();
    @FXML
    public ToggleButton toggleButton;
    @FXML
    public ToggleSwitch isOn;
    public Label introductionLabel, idLabel, nameLabel;

    private String id;
    private Category categoryModel;

    @FXML
    private void initialize() {
        CategoriesController.handleTableItemSelection(null);
    }

    @FXML
    private void handleRadioButtonClick(){
        if(!toggleButton.isSelected()){
            CategoriesController.handleTableItemSelection(null);
        } else {
            CategoriesController.handleTableItemSelection(id);
            System.out.println("id: " + id);
        }
    }

    @FXML
    private void handleToggleSwitchClick() {
        BooleanProperty oldState = isOn.switchedProperty();
        String confirmationMessage = "Are you sure you want to " + (!oldState.get() ? "enable" : "disable") + " this category?";
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation");
        confirmationAlert.setHeaderText(confirmationMessage);
        confirmationAlert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                if (oldState.get()) {
                    if (!categoryService.disableCategory(id)) {
                        AlertUtils.showAlert("Error", "Failed to disable category", Alert.AlertType.ERROR);
                        return;
                    }
                } else {
                    if (!categoryService.enableCategory(id)) {
                        AlertUtils.showAlert("Error", "Failed to enable category", Alert.AlertType.ERROR);
                        return;
                    }
                }
                isOn.setSwitchedProperty(!oldState.get());
                categoryModel.setEnabled(!oldState.get());
                AlertUtils.showAlert("Success", "Category has been " + (!oldState.get() ? "enabled" : "disabled"), Alert.AlertType.INFORMATION);
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
                CategoryDetailController.handleTableItemSelection(id);
                FXMLLoaderHelper.loadFXML(new Stage(), "employee/bookCategorys/categoryDetail");
            } else {
                AlertUtils.showAlert("Error", "Can't find user", Alert.AlertType.ERROR);

            }
        }
    }

    public void setCategoryModel(Category category) {
        categoryModel = category;
        id = category.getId();
        idLabel.setText(category.getId());
        nameLabel.setText(category.getName());
        introductionLabel.setText(category.getDescription());
        isOn.setSwitchedProperty(category.isEnabled());
    }
}
