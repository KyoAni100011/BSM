package com.bsm.bsm.employee.bookCategories;

import com.bsm.bsm.category.Category;
import com.bsm.bsm.category.CategoryService;
import com.bsm.bsm.publisher.Publisher;
import com.bsm.bsm.publisher.PublisherService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class CategoryDetailController {

    @FXML
    private static String id;
    private final CategoryService categoryService;
    @FXML
    private TextField IDField;
    @FXML
    private TextField introductionField;
    @FXML
    private Button isEnabledLabel;
    @FXML
    private TextField nameField;
    private Category categoryDetail;

    public CategoryDetailController() {
        categoryService = new CategoryService();
    }

    static void handleTableItemSelection(String authorID) {
        id = authorID;
    }

    @FXML
    public void initialize() {
        new CategoryDetailController();
        categoryDetail = categoryService.getCategory(id);
        setPublisherInfo();
    }

    private void setPublisherInfo() {
        IDField.setText(categoryDetail.getId());
        nameField.setText(categoryDetail.getName());
        introductionField.setText(categoryDetail.getDescription());
        isEnabledLabel.setText(categoryDetail.isEnabled() ? "Enable" : "Disable");
        if (categoryDetail.isEnabled()) {
            isEnabledLabel.getStyleClass().add("enable-button");
        } else {
            isEnabledLabel.getStyleClass().add("disable-button");
        }
    }
}
