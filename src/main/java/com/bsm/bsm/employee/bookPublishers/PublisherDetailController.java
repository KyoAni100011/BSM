package com.bsm.bsm.employee.bookPublishers;

import com.bsm.bsm.publisher.Publisher;
import com.bsm.bsm.publisher.PublisherService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class PublisherDetailController {
    @FXML
    public TextField fullNameField;
    @FXML
    public TextArea addressField;
    @FXML
    public Button isEnabledLabel;
    @FXML
    public TextField iDField;
    private static Publisher publisherDetail = null;

    private static final PublisherService publisherService = new PublisherService();;
    private static String id ="44441111";

    @FXML
    public void initialize() {
        new PublisherDetailController();
        setPublisherInfo();
    }
    static void handleTableItemSelection(String myId) {
        id = myId;
        publisherDetail = publisherService.getPublisher(id);
    }
    static void handleAfterAdd(Publisher p) {
        publisherDetail = p;
    }
    private void setPublisherInfo() {
        iDField.setText(publisherDetail.getId());
        fullNameField.setText(publisherDetail.getName());
        addressField.setText(publisherDetail.getAddress());
        isEnabledLabel.setText(publisherDetail.isEnabled() ? "Enable" : "Disable");
        if (publisherDetail.isEnabled()) {
            isEnabledLabel.getStyleClass().add("enable-button");
        } else {
            isEnabledLabel.getStyleClass().add("disable-button");
        }
    }
}
