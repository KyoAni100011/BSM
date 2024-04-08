package com.bsm.bsm.utils;

import com.bsm.bsm.user.UserModel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class FXMLLoaderHelper {

    public static void loadFXML(Stage currentStage, String fxmlPath) throws IOException {
        if (currentStage == null || fxmlPath == null) {
            throw new IllegalArgumentException("currentStage and fxmlPath cannot be null");
        }

        Parent root = FXMLLoader.load(Objects.requireNonNull(FXMLLoaderHelper.class.getResource("/com/bsm/bsm/view/" + fxmlPath + ".fxml")));
        Scene scene = new Scene(root);

        scene.getStylesheets().add(Objects.requireNonNull(FXMLLoaderHelper.class.getResource("/com/bsm/bsm/style/style.css")).toExternalForm());

        currentStage.setTitle("Welcome to FXML");
        currentStage.setScene(scene);
        currentStage.show();
    }


}
