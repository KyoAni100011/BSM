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
        loadFXML(currentStage, fxmlPath, "Welcome to FXML", true); // Always resizable is true
    }

    public static void loadFXML(Stage currentStage, String fxmlPath, String title) throws IOException {
        loadFXML(currentStage, fxmlPath, title, true); // Always resizable is true
    }
    public static <T> T loadFXMLWithController(Stage currentStage, String fxmlPath, String title) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(FXMLLoaderHelper.class.getResource(fxmlPath));
        Parent root = fxmlLoader.load();

        // Load CSS file
        root.getStylesheets().add(Objects.requireNonNull(FXMLLoaderHelper.class.getResource("/com/bsm/bsm/style/style.css")).toExternalForm());

        Scene scene = new Scene(root);
        currentStage.setScene(scene);
        currentStage.setTitle(title);
        currentStage.setResizable(true);
        currentStage.show();

        return fxmlLoader.getController();
    }

    public static void loadFXML(Stage currentStage, String fxmlPath, String title, boolean resizable) throws IOException {
        if (currentStage == null || fxmlPath == null) {
            throw new IllegalArgumentException("currentStage and fxmlPath cannot be null");
        }

        Parent root = FXMLLoader.load(Objects.requireNonNull(FXMLLoaderHelper.class.getResource("/com/bsm/bsm/view/" + fxmlPath + ".fxml")));
        Scene scene = new Scene(root);

        scene.getStylesheets().add(Objects.requireNonNull(FXMLLoaderHelper.class.getResource("/com/bsm/bsm/style/style.css")).toExternalForm());

        currentStage.setTitle(title);
        currentStage.setScene(scene);
        currentStage.setResizable(resizable); // Set resizable property
        currentStage.show();
    }
}
