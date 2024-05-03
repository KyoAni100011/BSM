package com.bsm.bsm.employee.importSheet;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class ItemSearch {
    @FXML
    public Button titleButton;
    private AddBookBatchController add;
    void addName(String title,AddBookBatchController parentadd){
        titleButton.setText(title);
        add = parentadd;
    }
    @FXML
    void handleSaveChanges(){
        add.handleChoiceAction(titleButton.getText());
    }
}
