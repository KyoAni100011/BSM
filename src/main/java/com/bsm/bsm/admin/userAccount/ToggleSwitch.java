package com.bsm.bsm.admin.userAccount;
import javafx.animation.FillTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class ToggleSwitch extends HBox {
    private String userId; // Store the user ID

    private BooleanProperty switchedOn = new SimpleBooleanProperty(false);
    private FillTransition fillAnimation = new FillTransition(Duration.seconds(0.25));
    private TranslateTransition translateAnimation = new TranslateTransition(Duration.seconds(0.25));
    private ParallelTransition animation;

    public BooleanProperty switchedProperty(){
        return switchedOn;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setSwitchedProperty(Boolean a){
        switchedOn.set(a);
    }

    public ToggleSwitch(){
        Rectangle bg = new Rectangle(45, 20);
        bg.setArcWidth(20);
        bg.setArcHeight(20);
        bg.setFill(Color.WHITE);
        bg.setStroke(Color.LIGHTGRAY);

        Circle trigger = new Circle(10);
        trigger.setFill(Color.WHITE);
        trigger.setStroke(Color.LIGHTGRAY);

        translateAnimation.setNode(trigger);
        fillAnimation.setShape(bg);
        getChildren().addAll(bg, trigger);

        // Set initial position
        translateAnimation.setToX(switchedOn.get() ? -20 : -46);
        fillAnimation.setFromValue(switchedOn.get() ? Color.WHITE : Color.LIGHTGREEN);
        fillAnimation.setToValue(switchedOn.get() ? Color.LIGHTGREEN: Color.WHITE);
        animation = new ParallelTransition(translateAnimation, fillAnimation);
        animation.play();

        switchedOn.addListener((obs, oldState, newState) -> {

            boolean isOn = newState;
            translateAnimation.setToX(isOn ? -20 : -46);


            fillAnimation.setFromValue(isOn ? Color.WHITE : Color.LIGHTGREEN);
            fillAnimation.setToValue(isOn ? Color.LIGHTGREEN: Color.WHITE);
            animation.play();
        });

        setOnMouseClicked(event -> {
            switchedOn.set(!switchedOn.get());
        });
    }
}

