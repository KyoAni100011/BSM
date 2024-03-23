package com.bsm.bsm.employee.bookCategories;

import com.bsm.bsm.category.Category;
import com.bsm.bsm.category.CategorySingleton;
import com.bsm.bsm.category.UpdateCategoryService;
import com.bsm.bsm.utils.AlertUtils;
import com.bsm.bsm.utils.ValidationUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.text.ParseException;

public class UpdateCategoryController {
    @FXML
    private Label nameErrorLabel, descriptionErrorLabel;

    @FXML
    private TextField nameField, descriptionField;

    private final UpdateCategoryService updateCategoryService = new UpdateCategoryService();

    @FXML
    public void initialize() {
        //set temp id, need to get id from table view
        Category category = updateCategoryService.getCategory("55551111");
        setCategoryInfo(category);
    }

    private void setCategoryInfo(Category category) {
        nameField.setText(category.getName());
        descriptionField.setText(category.getDescription());
    }

    private void updateCategoryInformation(String name, String description) {
        Category category = CategorySingleton.getInstance().getCategory();
        category.setName(name);
        category.setDescription(description);
    }

    @FXML
    private void handleSaveChanges(ActionEvent event) throws ParseException {
        clearErrorMessages();
        //set temp id, need to get id from table view
        String id = "33331111";
        String name = nameField.getText();
        String description = descriptionField.getText();
        if (validateInputs(name, description)) {
            if (updateCategoryService.updateCategory(id, name, description)) {
                updateCategoryInformation(name, description);
                AlertUtils.showAlert("Success", "Category updated successfully.", Alert.AlertType.INFORMATION);
                clearInputs();
            }
            else {
                AlertUtils.showAlert("Error", "Category update failed.", Alert.AlertType.ERROR);
            }
            var category = updateCategoryService.getCategory(id);
            setCategoryInfo(category);
        }
    }

    private boolean validateInputs(String name, String description) {
        String nameError = ValidationUtils.validateCategoryName(name);
        String descriptionError = ValidationUtils.validateDescription(description, "category");

        if (nameError != null) {
            nameErrorLabel.setText(nameError);
        }
        if (descriptionError != null) {
            descriptionErrorLabel.setText(descriptionError);
        }
        return nameError == null && descriptionError == null;
    }

    private void clearErrorMessages() {
        nameErrorLabel.setText("");
        descriptionErrorLabel.setText("");
    }

    private void clearInputs() {
        nameField.setText("");
        descriptionField.setText("");
    }
}

