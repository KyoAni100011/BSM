package com.bsm.bsm.employee.bookAuthors;

import com.bsm.bsm.admin.userAccount.UserDetailController;
import com.bsm.bsm.author.Author;
import com.bsm.bsm.author.AuthorService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class AuthorDetailController {

    @FXML
    private static String name;
    private final AuthorService authorService;
    @FXML
    private TextField IDField;
    @FXML
    private TextField introductionField;
    @FXML
    private Button isEnabledLabel;
    @FXML
    private TextField nameField;
    private Author authorDetail;

    public AuthorDetailController() {
        authorService = new AuthorService();
    }

    static void handleTableItemSelection(String authorName) {
        name = authorName;
    }

    @FXML
    public void initialize() {
        new UserDetailController();
        authorDetail = authorService.getAuthor(name);
        setAuthorInfo();
    }

    private void setAuthorInfo() {
        IDField.setText(authorDetail.getId());
        nameField.setText(authorDetail.getName());
        introductionField.setText(authorDetail.getIntroduction());
        isEnabledLabel.setText(authorDetail.isEnabled() ? "Enable" : "Disable");
        if (authorDetail.isEnabled()) {
            isEnabledLabel.getStyleClass().add("enable-button");
        } else {
            isEnabledLabel.getStyleClass().add("disable-button");
        }
    }
}
