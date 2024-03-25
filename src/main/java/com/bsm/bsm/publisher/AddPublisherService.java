package com.bsm.bsm.publisher;

public class AddPublisherService {
    private final AddPublisherDAO addPublisherDAO;

    public AddPublisherService() {
        addPublisherDAO = new AddPublisherDAO();
    }

    public boolean checkPublisherExists(String name) {
        return addPublisherDAO.checkPublisherExists(name);
    }

    public boolean addPublisher(String name, String address) {
        return addPublisherDAO.addPublisher(name, address);
    }
}
