package com.bsm.bsm.employee.bookPublishers;

import com.bsm.bsm.admin.userAccount.UserDetailController;
import com.bsm.bsm.author.Author;
import com.bsm.bsm.author.AuthorService;
import com.bsm.bsm.publisher.Publisher;
import com.bsm.bsm.publisher.PublisherService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class PublisherDetailController {

    @FXML
    private static String id;
    private final PublisherService publisherService;
    @FXML
    private TextField IDField;
    @FXML
    private TextField introductionField;
    @FXML
    private Button isEnabledLabel;
    @FXML
    private TextField nameField;
    private Publisher publisherDetail;

    public PublisherDetailController() {
        publisherService = new PublisherService();
    }

    static void handleTableItemSelection(String authorID) {
        id = authorID;
    }

    @FXML
    public void initialize() {
        new UserDetailController();
        publisherDetail = publisherService.getPublisher(id);
        setPublisherInfo();
    }

    private void setPublisherInfo() {
        IDField.setText(publisherDetail.getId());
        nameField.setText(publisherDetail.getName());
        introductionField.setText(publisherDetail.getAddress());
        isEnabledLabel.setText(publisherDetail.isEnabled() ? "Enable" : "Disable");
        if (publisherDetail.isEnabled()) {
            isEnabledLabel.getStyleClass().add("enable-button");
        } else {
            isEnabledLabel.getStyleClass().add("disable-button");
        }
    }
}
