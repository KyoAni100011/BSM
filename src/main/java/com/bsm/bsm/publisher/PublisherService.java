package com.bsm.bsm.publisher;

import com.bsm.bsm.commonInterface.*;
import java.util.List;

public class PublisherService implements Activable, Searchable<Publisher>, Sortable<Publisher>, Updatable<Publisher>, Addable<Publisher> {
    private final PublisherDAO publisherDAO;

    public PublisherService() {
        this.publisherDAO = new PublisherDAO();
    }

    public Publisher getPublisher(String id) {
        return publisherDAO.getPublisher(id);
    }

    public Publisher getPublisherByName(String name) {
        return publisherDAO.getPublisherByName(name);
    }

    public boolean isEnabled(String id) {
        return getPublisher(id).isEnabled();
    }

    @Override
    public boolean update(Publisher item) {
        return publisherDAO.updatePublisher(item.getId(), item.getName(), item.getAddress());
    }

    @Override
    public List<Publisher> sort(List<Publisher> publishers, boolean isAscending, String column) {
        // Implement sorting logic
        return null;
    }

    @Override
    public List<Publisher> search(String keyword) {
        // Implement search logic
        return null;
    }

    @Override
    public boolean add(Publisher item) {
        return publisherDAO.addPublisher(item.getName(), item.getAddress());
    }

    public boolean checkPublisherExists(String name, String id) {
        return publisherDAO.checkPublisherExists(name, id);
    }

    public boolean checkPublisherExists(String name) {
        return checkPublisherExists(name, "");
    }

    @Override
    public boolean setEnable(boolean state) {

        return state;
    }

}
