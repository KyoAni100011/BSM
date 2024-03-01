package main.java.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class FXMLLoaderHelper {

    public static void loadFXML(Stage currentStage, String fxmlPath) throws IOException {
        if (currentStage == null || fxmlPath == null) {
            throw new IllegalArgumentException("currentStage and fxmlPath cannot be null");
        }

        Parent root = FXMLLoader
                .load(FXMLLoaderHelper.class.getClassLoader().getResource("main/resources/view/" + fxmlPath + ".fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(
                FXMLLoaderHelper.class.getClassLoader().getResource("main/resources/css/style.css").toExternalForm());
        currentStage.setTitle("Welcome to FXML");
        currentStage.setScene(scene);
        currentStage.show();
    }
}
