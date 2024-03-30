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
        System.out.println("User ID: " + userId); // Print the user ID for testing
    }

    public void setSwitchedProperty(Boolean a){
        switchedOn.set(a);
    }

    public ToggleSwitch(){
        Rectangle bg = new Rectangle(35, 20);
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
        trigger.setTranslateX(switchedOn.get() ? -20 : -36);

        fillAnimation.setFromValue(switchedOn.get() ? Color.WHITE : Color.valueOf("#20B042"));
        fillAnimation.setToValue(switchedOn.get() ? Color.valueOf("#20B042"): Color.WHITE);
        animation = new ParallelTransition(translateAnimation, fillAnimation);
        animation.play();

        switchedOn.addListener((obs, oldState, newState) -> {

            boolean isOn = newState;
            translateAnimation.setToX(isOn ? -20 : -36);


            fillAnimation.setFromValue(isOn ? Color.WHITE : Color.valueOf("#20B042"));
            fillAnimation.setToValue(isOn ? Color.valueOf("#20B042"): Color.WHITE);
            animation.play(); // Restart the animation
            System.out.println(oldState +" " + newState);
        });

        setOnMouseClicked(event -> {

            switchedOn.set(!switchedOn.get());
        });
    }
}

