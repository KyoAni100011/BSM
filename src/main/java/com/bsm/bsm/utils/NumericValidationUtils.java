package com.bsm.bsm.utils;

import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.KeyEvent;

import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public class NumericValidationUtils {
    public static final UnaryOperator<TextFormatter.Change> integerFilter = change -> {
        if (Pattern.matches("\\d*", change.getControlNewText())) {
            return change;
        } else {
            return null;
        }
    };

    public static EventHandler<KeyEvent> numericValidation(final Integer maxLength) {
        return e -> {
            TextField textField = (TextField) e.getSource();
            if (textField.getText().length() >= maxLength) {
                e.consume();
                return;
            }

            if (e.getCharacter().matches("[0-9]") || e.getCharacter().equals("/")) {
                String text = textField.getText();
                int caretPosition = textField.getCaretPosition();

                if (e.getCharacter().equals("/") && (caretPosition != 2 && caretPosition != 5)) {
                    e.consume();
                    return;
                }

                if (e.getCharacter().matches("[0-9]")) {
                    if ((caretPosition == 2 || caretPosition == 5) && !text.contains("/")) {
                        textField.appendText("/");
                    }

                    if (caretPosition == 2 || caretPosition == 5) {
                        e.consume();
                        return;
                    }
                }
            } else {
                e.consume();
            }
        };
    }
}
