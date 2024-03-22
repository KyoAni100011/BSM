package com.bsm.bsm.publisher;

import com.bsm.bsm.publisher.Publisher;
import com.bsm.bsm.publisher.UpdatePublisherDAO;

public class UpdatePublisherService {
    private final UpdatePublisherDAO updatePublisherDAO;

    public UpdatePublisherService() {
        updatePublisherDAO = new UpdatePublisherDAO();
    }

    public boolean updatePublisher(String id, String newName, String introduction) {
        return updatePublisherDAO.updatePublisher(id, newName, introduction);
    }

    public Publisher getPublisher(String id) {
        return updatePublisherDAO.getPublisher(id);
    }
}
