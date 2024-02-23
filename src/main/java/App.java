package main.java;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        try {

            Parent root = FXMLLoader.load(getClass().getResource("../resources/view/login.fxml"));

            Scene scene = new Scene(root);

            scene.getStylesheets().add(getClass().getResource("../resources/css/style.css").toExternalForm());

            stage.setTitle("FXML Welcome");

            stage.setScene(scene);

            stage.show();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}