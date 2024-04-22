package com.bsm.bsm;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // Load FXML
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/bsm/bsm/view/login.fxml")));
        if (root == null) {
            throw new IOException("FXML file not found");
        }

        // Load CSS
        String cssPath = "/com/bsm/bsm/style/style.css";
        if (getClass().getResource(cssPath) == null) {
            throw new IOException("CSS file not found");
        }
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource(cssPath).toExternalForm());

        stage.setTitle("BSM Welcome");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
