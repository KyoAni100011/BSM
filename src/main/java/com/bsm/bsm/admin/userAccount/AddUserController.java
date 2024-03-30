package com.bsm.bsm.admin.userAccount;

import com.bsm.bsm.account.AccountService;
import com.bsm.bsm.user.UserSingleton;
import com.bsm.bsm.utils.*;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.controlsfx.validation.ValidateEvent;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class AddUserController {
    @FXML
    public Label emailErrorLabel, dobErrorLabel, nameErrorLabel, addressErrorLabel;

    @FXML
    public TextField emailField, customPassword, nameField, addressField;

    @FXML
    public DatePicker dobPicker;

    @FXML
    public Label textNote;

    @FXML
    public Button resetButton;

    private final AccountService accountService = new AccountService();

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
    private void handleAddButtonAction(ActionEvent event) throws IOException {
        clearErrorMessages();
        String email = emailField.getText().toLowerCase();
        String password = customPassword.getText();
        String name = nameField.getText();
        String dob = dobPicker.getEditor().getText();
        String address = addressField.getText();

        if (validateInputs(email, name, dob, address)) {
            //check user not admin by email
            String adminEmail = UserSingleton.getInstance().getUser().getEmail();
            if (adminEmail.equals(email)) {
                emailErrorLabel.setText("Admin cannot add themselves.");
                return;
            }

            if (accountService.hasUserExist(email)) {
                emailErrorLabel.setText("User already exists.");
                return;
            }

            dob = DateUtils.formatDOB(dob);
            if (accountService.addUser(name, dob, email, address, password)) {
                AlertUtils.showAlert("User Added", "User added successfully.", Alert.AlertType.INFORMATION);
                clearInputs();
                UserDetailController.handleTableItemSelection(email);
                FXMLLoaderHelper.loadFXML(new Stage(), "admin/userAccount/userDetail");
                closeWindow(event);
            } else {
                AlertUtils.showAlert("Error", "An error occurred while adding user.", Alert.AlertType.ERROR);
            }
        }
    }

    private void closeWindow(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    private boolean validateInputs(String email, String name, String dob, String address) {
        String emailError = ValidationUtils.validateEmail(email);
        String dobError = ValidationUtils.validateDOB(dob,"user");
        String nameError = ValidationUtils.validateFullName(name, "user");
        String addressError = ValidationUtils.validateAddress(address, "user");

        if (emailError != null) {
            emailErrorLabel.setText(emailError);
        }

        if (dobError != null) {
            dobErrorLabel.setText(dobError);
        }
        if (nameError != null) {
            nameErrorLabel.setText(nameError);
        }

        if (addressError != null) {
            addressErrorLabel.setText(addressError);
        }

        return emailError == null && dobError == null && nameError == null && addressError == null;
    }

    private void clearErrorMessages() {
        emailErrorLabel.setText("");
        dobErrorLabel.setText("");
        nameErrorLabel.setText("");
        addressErrorLabel.setText("");
    }


    private void clearInputs() {
        emailField.clear();
        customPassword.clear();
        nameField.clear();
        dobPicker.getEditor().clear();
        addressField.clear();
    }
}