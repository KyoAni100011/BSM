package com.bsm.bsm.employee.bookCategories;

import com.bsm.bsm.admin.userAccount.UserDetailController;
import com.bsm.bsm.author.Author;
import com.bsm.bsm.author.AuthorService;
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

    private static Category categoryDetail = null;

    private static final CategoryService categoryService =  new CategoryService();;
    private static String id ="55551111";

    @FXML
    public void initialize() {
        new CategoryDetailController();
        setCategoryInfo();
    }
    static void handleTableItemSelection(String myId) {
        id = myId;
        categoryDetail = categoryService.getCategory(id);

    }
    static void handleAfterAdd(Category category) {
        categoryDetail = category;
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
