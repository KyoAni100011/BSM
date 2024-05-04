package com.bsm.bsm.utils;

import com.bsm.bsm.App;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
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

    public void sceneSwitchLogout(AnchorPane currentAnchorPane, String fxml, double paddingBottom) throws IOException {
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

        // Set padding for the currentAnchorPane to increase its height
        double additionalHeight = nextAnchorPane.getPrefHeight() - currentAnchorPane.getPrefHeight();
        double additionalWidth = nextAnchorPane.getPrefWidth() - currentAnchorPane.getPrefWidth();
        currentAnchorPane.setPadding(new Insets(0, additionalWidth, paddingBottom + additionalHeight, 0));

        // Resize the scene and stage based on the content
        resizeSceneAndStageLogout(nextAnchorPane);
    }

    private void resizeSceneAndStageLogout(AnchorPane anchorPane) {
        Stage stage = (Stage) anchorPane.getScene().getWindow();
        if (stage != null) {
            stage.setResizable(true); // Ensure the stage is resizable

            // Set the size of the stage
            stage.setWidth(anchorPane.getPrefWidth());
            stage.setHeight(anchorPane.getPrefHeight() + 20);

            // Calculate the center position of the screen
            double centerX = (Screen.getPrimary().getVisualBounds().getWidth() - stage.getWidth()) / 2;
            double centerY = (Screen.getPrimary().getVisualBounds().getHeight() - stage.getHeight()) / 2;

            // Set the position of the stage
            stage.setX(centerX);
            stage.setY(centerY);
        }
    }

    private void resizeSceneAndStage(AnchorPane anchorPane) {
        Stage stage = (Stage) anchorPane.getScene().getWindow();
        if (stage != null) {
            stage.setResizable(true); // Ensure the stage is resizable

            // Calculate the center position of the screen
            Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
            double centerX = primaryScreenBounds.getMinX() + (primaryScreenBounds.getWidth() - anchorPane.getPrefWidth()) / 2;
            double centerY = primaryScreenBounds.getMinY() + (primaryScreenBounds.getHeight() - anchorPane.getPrefHeight()) / 2;

            // Set the position and size of the stage
            stage.setX(centerX);
            stage.setY(centerY);
            stage.setWidth(anchorPane.getPrefWidth());
            stage.setHeight(anchorPane.getPrefHeight());
        }
    }
}
