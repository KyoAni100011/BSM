package main.java;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.fxml.FXML;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class TitleControl {

    private double xOffset = 0;
    private double yOffset = 0;

    @FXML
    private AnchorPane TopPane;

    @FXML
    private FontAwesomeIcon btnClose;

    @FXML
    private FontAwesomeIcon btnMinus;

    @FXML
    void handleCloseAction(MouseEvent event) {
        closeStage();
    }

    @FXML
    void handleMinusAction(MouseEvent event) {
        minimizeStage();
    }

    @FXML
    void handleClickAction(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        }
    }

    @FXML
    void handleMovementAction(MouseEvent event) {
        if (event.isPrimaryButtonDown()) {
            moveStage(event);
        }
    }

    private Stage getStage() {
        return (Stage) TopPane.getScene().getWindow();
    }

    private void closeStage() {
        getStage().close();
    }

    private void minimizeStage() {
        getStage().setIconified(true);
    }

    private void moveStage(MouseEvent event) {
        Stage stage = getStage();
        stage.setX(event.getScreenX() - xOffset);
        stage.setY(event.getScreenY() - yOffset);
    }
}
