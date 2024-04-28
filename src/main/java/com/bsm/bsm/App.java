package com.bsm.bsm;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class App extends Application {
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        loadFXML("login", "BSM Welcome");
    }

    public void loadFXML(String fxmlFile, String title) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/bsm/bsm/view/" + fxmlFile + ".fxml"));
        Parent root = fxmlLoader.load();

        // Load CSS
        String cssPath = "/com/bsm/bsm/style/style.css";
        if (getClass().getResource(cssPath) == null) {
            throw new IOException("CSS file not found");
        }
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource(cssPath).toExternalForm());

        primaryStage.setTitle(title);
        primaryStage.setScene(scene);
        primaryStage.setResizable(true); // Ensure the window is resizable
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
