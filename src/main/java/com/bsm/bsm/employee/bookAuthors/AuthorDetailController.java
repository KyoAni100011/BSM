package com.bsm.bsm.employee.bookAuthors;

import com.bsm.bsm.admin.userAccount.UserDetailController;
import com.bsm.bsm.auth.AuthService;
import com.bsm.bsm.author.Author;
import com.bsm.bsm.author.AuthorService;
import com.bsm.bsm.user.UserModel;
import com.bsm.bsm.utils.NumericValidationUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.bsm.bsm.utils.DateUtils.convertDOBFormat;

public class AuthorDetailController {

    @FXML
    private TextField IDField;

    @FXML
    private TextField introductionField;

    @FXML
    private Button isEnabledLabel;

    @FXML
    private TextField nameField;
    @FXML
    private static String name;
    private Author authorDetail = null;
    private AuthorService authorService = null;
    public AuthorDetailController()
    {
        authorService = new AuthorService();
    }

    @FXML
    public void initialize() {
        new UserDetailController();
        authorDetail = authorService.getAuthor(name);
        setAuthorInfo();
    }

    static  void handleTableItemSelection(String authorName) {
        name = authorName;
    }

    private void setAuthorInfo() {
        nameField.setText(authorDetail.getName());
        introductionField.setText(authorDetail.getIntroduction());
        isEnabledLabel.setText(authorDetail.isEnabled() ? "Enable" : "Disable");
        if(authorDetail.isEnabled()){
            isEnabledLabel.getStyleClass().add("enable-button");
        }else {
            isEnabledLabel.getStyleClass().add("disable-button");
        }
    }
}
