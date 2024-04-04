package com.bsm.bsm.employee.book;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class ChoiceBoxListController implements Initializable {

    @FXML
    private VBox container;

    public void addChoiceBox() {
        ChoiceBox<String> choiceBox = new ChoiceBox<>();
        choiceBox.getItems().addAll("Option 1", "Option 2", "Option 3");

        Button closeButton = new Button("X");
        closeButton.setOnAction(event -> {
            HBox choiceBoxContainer = (HBox) closeButton.getParent();
            container.getChildren().remove(choiceBoxContainer);
        });

        HBox choiceBoxContainer = new HBox(choiceBox, closeButton);
        container.getChildren().add(choiceBoxContainer);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialization code
    }
}


