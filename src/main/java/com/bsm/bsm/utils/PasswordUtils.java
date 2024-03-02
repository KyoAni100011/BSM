package com.bsm.bsm.utils;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.scene.input.KeyEvent;
import javafx.event.EventHandler;

public class PasswordUtils {
    public static void handleShowHidePassword(ActionEvent event, PasswordField passwordField, TextField textField, FontAwesomeIcon eyeIcon) {
        if (passwordField.isVisible()) {
            passwordField.setVisible(false);
            textField.setText(passwordField.getText());
            textField.setVisible(true);
            eyeIcon.setGlyphName("EYE");
        } else {
            passwordField.setText(textField.getText());
            passwordField.setVisible(true);
            textField.setVisible(false);
            eyeIcon.setGlyphName("EYE_SLASH");
        }
    }
}
