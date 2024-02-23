package main.java.utils;

import java.io.IOException;
import java.util.Objects;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

public class SwitchScreen {
    public static void switchScene(AnchorPane currentAnchorPane, String fxml) throws IOException {
        AnchorPane nextAnchorPane = FXMLLoader
                .load(Objects.requireNonNull(Application.class.getResource("/path/to/your/fxml/" + fxml)));
        currentAnchorPane.getChildren().clear();
        currentAnchorPane.getChildren().add(nextAnchorPane);
    }
}
