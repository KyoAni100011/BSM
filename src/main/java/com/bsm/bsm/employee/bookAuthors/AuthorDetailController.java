package com.bsm.bsm.employee.bookAuthors;

import com.bsm.bsm.admin.userAccount.UserDetailController;
import com.bsm.bsm.author.Author;
import com.bsm.bsm.author.AuthorService;
import com.bsm.bsm.publisher.Publisher;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class AuthorDetailController {

    @FXML
    private TextField IDField;
    @FXML
    private TextArea introductionField;

    @FXML
    private Button isEnabledLabel;
    @FXML
    private TextField nameField;
    private static String id ="33331111";
    private static Author authorDetail = null;
    private static final AuthorService authorService = new AuthorService();
    @FXML
    public void initialize() {
        new UserDetailController();
        setAuthorInfo();
    }

    static void handleTableItemSelection(String myId) {
        id = myId;
        authorDetail = authorService.getAuthor(id);
    }
    static void handleAfterAdd(Author author) {
        authorDetail = author;
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
