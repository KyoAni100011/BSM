package com.bsm.bsm.employee.bookCategories;

import com.bsm.bsm.category.Category;
import com.bsm.bsm.category.CategoryService;
import com.bsm.bsm.utils.AlertUtils;
import com.bsm.bsm.utils.ValidationUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.text.ParseException;
import java.util.List;

public class UpdateCategoryController {
    @FXML
    private Label nameErrorLabel, descriptionErrorLabel;

    @FXML
    private TextField nameField;

    @FXML
    private TextArea descriptionField;
    private final CategoryService categoryService = new CategoryService();

    @FXML
    private static String id;

    public static void handleTableItemSelection(String userId) {
        id = userId;
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
            if (categoryService.checkCategoryExists(name, id)) {
                nameErrorLabel.setText("Category already exists.");
                return;
            }

            Category category = new Category(id, name, description, isEnabled);
            if (categoryService.update(category)) {
                AlertUtils.showAlert("Success", "Category updated successfully.", Alert.AlertType.INFORMATION);
                clearInputs();
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.close();
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
