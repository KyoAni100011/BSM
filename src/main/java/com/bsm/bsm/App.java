package com.bsm.bsm;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Objects;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
//        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/bsm/bsm/view/admin/adminMainScreen.fxml")));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/bsm/bsm/view/login.fxml"));
        Parent root = loader.load();

        // Check if the loaded FXML file is the login screen
        if (loader.getLocation().getPath().contains("login.fxml")) {
            stage.initStyle(StageStyle.DECORATED); // Hide the title bar for the login screen
        } else {
            stage.setTitle("FXML Welcome");
        }

        Scene scene = new Scene(root);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/bsm/bsm/style/style.css")).toExternalForm());
        stage.setTitle("FXML Welcome");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
