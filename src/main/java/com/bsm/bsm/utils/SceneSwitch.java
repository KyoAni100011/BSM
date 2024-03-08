package com.bsm.bsm.utils;

import com.bsm.bsm.App;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class SceneSwitch {
    public SceneSwitch(AnchorPane currentAnchorPane, String fxml) throws IOException {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(App.class.getResource(fxml)));
        AnchorPane nextAnchorPane = loader.load();

        // Set constraints for currentAnchorPane to resize according to nextAnchorPane
        AnchorPane.setTopAnchor(currentAnchorPane, 0.0);
        AnchorPane.setRightAnchor(currentAnchorPane, 0.0);
        AnchorPane.setBottomAnchor(currentAnchorPane, 0.0);
        AnchorPane.setLeftAnchor(currentAnchorPane, 0.0);

        // Clear and add the new AnchorPane to the currentAnchorPane
        currentAnchorPane.getChildren().clear();
        currentAnchorPane.getChildren().add(nextAnchorPane);

        // Resize the scene and stage based on the content
        resizeSceneAndStage(nextAnchorPane);
    }

    private void resizeSceneAndStage(AnchorPane anchorPane) {
        Scene scene = anchorPane.getScene();
        if (scene != null) {
            scene.getWindow().sizeToScene();
        }

        Stage stage = (Stage) anchorPane.getScene().getWindow();
        if (stage != null) {
            stage.sizeToScene();
        }
    }
}