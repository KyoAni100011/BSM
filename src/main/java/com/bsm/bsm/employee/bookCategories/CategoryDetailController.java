package com.bsm.bsm.employee.bookCategories;

import com.bsm.bsm.admin.userAccount.UserDetailController;
import com.bsm.bsm.author.AuthorService;
import com.bsm.bsm.category.Category;
import com.bsm.bsm.category.Category;
import com.bsm.bsm.category.CategoryService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class CategoryDetailController {
    @FXML
    public TextField nameField;
    @FXML
    public TextArea descriptionField;
    @FXML
    public TextField idField;
    @FXML
    public Button isEnabledLabel;

    private Category categoryDetail = null;

    private CategoryService categoryService = null;
    private static String id ="55551111";

    @FXML
    public void initialize() {
        new CategoryDetailController();
        categoryDetail = categoryService.getCategory(id);
        setCategoryInfo();
    }
    public CategoryDetailController()
    {
        categoryService = new CategoryService();
    }
    private void setCategoryInfo() {
        idField.setText(categoryDetail.getId());
        nameField.setText(categoryDetail.getName());
        descriptionField.setText(categoryDetail.getDescription());
        isEnabledLabel.setText(categoryDetail.isEnabled() ? "Enable" : "Disable");
        if (categoryDetail.isEnabled()) {
            isEnabledLabel.getStyleClass().add("enable-button");
        } else {
            isEnabledLabel.getStyleClass().add("disable-button");
        }
    }
}
