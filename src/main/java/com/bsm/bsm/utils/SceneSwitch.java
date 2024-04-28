package com.bsm.bsm.utils;

import com.bsm.bsm.App;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class SceneSwitch {
    public SceneSwitch() {

    }

    public void SceneSwitchDifferSize(AnchorPane currentAnchorPane, String fxml) throws IOException {
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

    }

    private void resizeSceneAndStage(AnchorPane anchorPane) {
        // Set the resizable property for the stage
        Stage stage = (Stage) anchorPane.getScene().getWindow();
        if (stage != null) {
            stage.setResizable(true); // Ensure the stage is resizable

            // Calculate the center position of the screen
            Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
            double centerX = primaryScreenBounds.getMinX() + (primaryScreenBounds.getWidth() - anchorPane.getPrefWidth()) / 2;
            double centerY = primaryScreenBounds.getMinY() + (primaryScreenBounds.getHeight() - anchorPane.getPrefHeight()) / 2;

            // Set the position of the stage
            stage.setX(centerX);
            stage.setY(centerY);

            // Set preferred width and height based on the content
            double prefWidth = anchorPane.getPrefWidth();
            double prefHeight = anchorPane.getPrefHeight();

            // Ensure the preferred width and height are not zero
            if (prefWidth > 0 && prefHeight > 0) {
                stage.setMinWidth(prefWidth);
                stage.setMinHeight(prefHeight);
                stage.setMaxWidth(prefWidth);
                stage.setMaxHeight(prefHeight);
            }

            // Resize the scene and stage based on the content
            Scene scene = anchorPane.getScene();
            if (scene != null) {
                scene.getWindow().sizeToScene();
            }

            stage.sizeToScene();
        }
    }
}
