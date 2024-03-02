package main.java;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../resources/view/mainScreen.fxml"));

        Scene scene = new Scene(root);

        scene.getStylesheets().add(getClass().getResource("../resources/css/style.css").toExternalForm());

        stage.setTitle("FXML Welcome");

        stage.setScene(scene);

        stage.show();

    }

    public static void main(String[] args) {

        // Example query execution
        // String query = "SELECT * FROM customer";
        // DatabaseConnection.executeQuery(query, resultSet -> {
        // while (resultSet.next()) {
        // String name = resultSet.getString("name");

        // System.out.println("Customer Name: " + name);
        // }
        // });

        launch(args);
    }
}