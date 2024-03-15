package com.bsm.bsm.employee.profileSetting.bookCategories;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class TableItemsController {
    @FXML
    public RadioButton radioButton;
    @FXML
    public Button employeeButton, isEnabledButton, addCategoryButton, updateCategoryButton;
    @FXML
    public Button previousPaginationButton, nextPaginationButton, firstPaginationButton, secondPaginationButton, thirdPaginationButton, fourthPaginationButton, fifthPaginationButton;
    @FXML
    private VBox pnItems = null;
    private int currentPage = 1;
    private final int itemsPerPage = 9;

    public void initialize(URL location, ResourceBundle resources) {
        employeeButton.getStyleClass().add("profile-setting-button");
    }
}
