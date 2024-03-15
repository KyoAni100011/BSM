package com.bsm.bsm.admin.userAccount;

import com.bsm.bsm.user.UserSingleton;
import com.bsm.bsm.utils.AlertUtils;
import com.bsm.bsm.utils.NumericValidationUtils;
import com.bsm.bsm.utils.ValidationUtils;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class AddUserController {
    @FXML
    public Label emailErrorLabel, passwordErrorLabel, dobErrorLabel, nameErrorLabel;

    @FXML
    public TextField emailField, customPassword, nameField;

    @FXML
    public DatePicker dobPicker;

    @FXML
    public Label textNote;

    @FXML
    public Button resetButton;

//    private final AddUserService addUserService = new AddUserService();

    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @FXML
    public void initialize() {
        clearErrorMessages();
        setupDatePicker();
        textNote.setVisible(true);
        customPassword.setOnMouseClicked(event -> textNote.setVisible(false)); // Add event handler to hide textNote when customPassword is clicked

        dobPicker.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
            // Update password field when the value of the editor changes
            updatePasswordField();
        });
    }
    @FXML
    private void updatePasswordField() {
        String dob = dobPicker.getEditor().getText();
        if (dob.length() != 10) {
            return;
        }
        String password = dob.replaceAll("/", "");
        customPassword.setText(password);
    }

    private void setupDatePicker() {
        dobPicker.setPromptText("dd/mm/yyyy");

        dobPicker.setConverter(new StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate date) {
                return date != null ? dateFormatter.format(date) : "";
            }

            @Override
            public LocalDate fromString(String string) {
                try {
                    return LocalDate.parse(string, dateFormatter);
                } catch (DateTimeParseException e) {
                    return null;
                }
            }
        });

        dobPicker.getEditor().addEventFilter(KeyEvent.KEY_TYPED, NumericValidationUtils.numericValidation(10));
    }


    @FXML
    private void handleAddButtonAction(ActionEvent event) {
        clearErrorMessages();
        String email = emailField.getText();
        String password = customPassword.getText();
        String name = nameField.getText();
        String dob = dobPicker.getEditor().getText();

        if (validateInputs(email, name, password, dob)) {
            //check user not admin by email
            String adminEmail = UserSingleton.getInstance().getUser().getEmail();
//            if (adminEmail.equals(email)) {
//                emailErrorLabel.setText("Admin cannot add themselves.");
//                return;
//            }
//
//            if (addUserService.hasUserExist(email)) {
//                emailErrorLabel.setText("User already exists.");
//                return;
//            }

//            String adminID = UserSingleton.getInstance().getUser().getId();
//            addUserService.addUser(adminID, email, password, firstName, dob);
            System.out.println("User added successfully.");
            System.out.println("Email: " + email);
            System.out.println("Password: " + password);
            System.out.println("Name: " + name);
            System.out.println("DOB: " + dob);

            AlertUtils.showAlert("User Added", "User added successfully.", Alert.AlertType.INFORMATION);
            clearInputs();
            closeWindow(event);
        }
        else {
            AlertUtils.showAlert("Invalid Input", "Please check your input.", Alert.AlertType.ERROR);
        }
    }

    private void closeWindow(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    private boolean validateInputs(String email, String name, String password, String dob) {
        String emailError = ValidationUtils.validateEmail(email);
        String passwordError = ValidationUtils.validatePassword(password);
        String dobError = ValidationUtils.validateDOB(dob);
        String nameError = ValidationUtils.validateFullName(name);

        if (emailError != null) {
            emailErrorLabel.setText(emailError);
        }
        if (password.isEmpty()) {
            passwordError = null;
        }
        else if (passwordError != null) {
            passwordErrorLabel.setText(passwordError);
        }

        if (dobError != null) {
            dobErrorLabel.setText(dobError);
        }
        if (nameError != null) {
            nameErrorLabel.setText(nameError);
        }

        return emailError == null && passwordError == null && dobError == null && nameError == null;
    }

    private void clearErrorMessages() {
        emailErrorLabel.setText("");
        passwordErrorLabel.setText("");
        dobErrorLabel.setText("");
        nameErrorLabel.setText("");
    }


    private void clearInputs() {
        emailField.clear();
        customPassword.clear();
        nameField.clear();
        dobPicker.getEditor().clear();
    }
}
