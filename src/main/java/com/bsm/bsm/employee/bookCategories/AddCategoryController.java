package com.bsm.bsm.employee.bookCategories;

import com.bsm.bsm.category.CategoryService;
import com.bsm.bsm.utils.AlertUtils;
import com.bsm.bsm.utils.ValidationUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import java.text.ParseException;

public class AddCategoryController {
    @FXML
    private Label nameErrorLabel, descriptionErrorLabel;
    @FXML
    private TextField nameField;

    @FXML
    private TextArea descriptionTextField;

    private final CategoryService categoryService = new CategoryService();
    @FXML
    public void initialize() {

    }
    @FXML
    private void handleSaveChanges(ActionEvent event) throws ParseException {
        clearErrorMessages();
        String name = nameField.getText();
        String description = descriptionTextField.getText();

        if (validateInputs(name, description)) {
            if (categoryService.checkCategoryExists(name)) {
                nameErrorLabel.setText("Category already exists.");
            } else {
                if (categoryService.addCategory(name, description)) {
                    AlertUtils.showAlert("Success", "Add category successfully.", Alert.AlertType.INFORMATION);
                    clearInputs();
                } else {
                    AlertUtils.showAlert("Error", "An error occurred while adding the category.", Alert.AlertType.ERROR);
                }
            }
        }
    }

    private boolean validateInputs(String name, String description) {
        String nameError = ValidationUtils.validateFullName(name,"category");
        String descriptionError = ValidationUtils.validateDescription(description,"category");

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
        nameField.clear();
        descriptionTextField.clear();
    }

}
