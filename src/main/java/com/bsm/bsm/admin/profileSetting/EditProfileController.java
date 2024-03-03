package com.bsm.bsm.admin.profileSetting;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.util.StringConverter;
import javafx.scene.input.KeyEvent;
import javafx.event.EventHandler;

public class EditProfileController {
    @FXML
    public Button saveChangesButton;
    @FXML
    private Label fullNameErrorLabel, emailErrorLabel, dobErrorLabel;
    @FXML
    private TextField fullNameField, emailTextField;
    @FXML
    private DatePicker dobPicker;

    private final EditProfileService editProfileService = new EditProfileService();

    @FXML
    public void initialize() {
        // Set the prompt text for the DatePicker
        dobPicker.setPromptText("dd/mm/yyyy");

        // Set the date format for the DatePicker
        dobPicker.setConverter(new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        });

        // Add event filter to allow only numbers to be entered in the dobPicker
        dobPicker.getEditor().addEventFilter(KeyEvent.KEY_TYPED, numericValidation(10));
    }

    @FXML
    private void handleSaveChanges(ActionEvent event) {
        clearErrorMessages();
        String fullName = fullNameField.getText();
        String email = emailTextField.getText();
        String dob = dobPicker.getEditor().getText();

        System.out.println("dob: " + dob);

        if (validateFullName(fullName) & validateEmail(email) & validateDOB(dob)) {

            if (EditProfileDAO.updateProfile(email, fullName, dob)) {
                showAlert("Success", "Profile updated successfully.", Alert.AlertType.INFORMATION);

                fullNameField.setText("");
                emailTextField.setText("");
                dobPicker.getEditor().setText("");
            } else {
                showAlert("Error", "Profile update failed.", Alert.AlertType.ERROR);
            }
        }
    }

    private boolean validateEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        if (email.isEmpty()) {
            emailErrorLabel.setText("Please enter your email.");
            return false;
        } else if (!email.matches(emailRegex)) {
            emailErrorLabel.setText("Invalid email format.");
            return false;
        }
        return true;
    }

    private boolean validateFullName(String fullName) {
        if (fullName.isEmpty()) {
            fullNameErrorLabel.setText("Please enter your full name.");
            System.out.println("Full name is empty");
            return false;
        }
        return true;
    }

    private boolean validateDOB(String dob) {
        String dobRegex = "^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/\\d{4}$";
        if (dob.isEmpty()) {
            dobErrorLabel.setText("Please enter your date of birth.");
            return false;
        }
        if (!dob.matches(dobRegex)) {
            dobErrorLabel.setText("Invalid date format. Please use dd/mm/yyyy.");
            return false;
        }

        return true;
    }


    private void clearErrorMessages() {
        emailErrorLabel.setText("");
        fullNameErrorLabel.setText("");
        dobErrorLabel.setText("");
    }

    private void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    // Method to filter key events and allow only numeric input
    private EventHandler<KeyEvent> numericValidation(final Integer maxLength) {
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
