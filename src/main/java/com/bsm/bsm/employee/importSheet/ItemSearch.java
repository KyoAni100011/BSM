package com.bsm.bsm.employee.importSheet;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class ItemSearch {
    @FXML
    private Button titleButton;
    private AddBookBatchController add;
    public String getTitleButton(){
        return titleButton.getText();
    }
    void addName(String title,AddBookBatchController parentadd){
        titleButton.setText(title);
        add = parentadd;
    }
    @FXML
    void handleSaveChanges(){
        add.handleChoiceAction(titleButton.getText());
    }
}
