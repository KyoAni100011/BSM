package com.bsm.bsm.employee.bookCategories;

import com.bsm.bsm.category.Category;
import com.bsm.bsm.category.CategoryService;
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

    @FXML
    private static String name;

    private final CategoryService categoryService = new CategoryService();

    String id = "55551111"; //set temp id, need to get id from table view

    public static void handleTableItemSelection(String name) {
        name = name;
    }

    @FXML
    public void initialize() {
        Category category = categoryService.getCategoryById(id);
        setCategoryInfo(category);
    }

    private void setCategoryInfo(Category category) {
        nameField.setText(category.getName());
        descriptionField.setText(category.getDescription());
    }

    @FXML
    private void handleSaveChanges(ActionEvent event) throws ParseException {
        clearErrorMessages();
        String name = nameField.getText();
        String description = descriptionField.getText();
        if (validateInputs(name, description)) {
            //check if category is disabled
            boolean isEnabled = categoryService.isEnabled(id);
            if (!isEnabled) {
                AlertUtils.showAlert("Error", "Category is disabled. Please enable it first.", Alert.AlertType.ERROR);
                return;
            }

            //check if category already exists
            if (categoryService.checkCategoryExists(name)) {
                nameErrorLabel.setText("Category already exists.");
                return;
            }

            if (categoryService.update(id, name, description)) {
                AlertUtils.showAlert("Success", "Category updated successfully.", Alert.AlertType.INFORMATION);
            } else {
                AlertUtils.showAlert("Error", "Category update failed.", Alert.AlertType.ERROR);
            }
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
