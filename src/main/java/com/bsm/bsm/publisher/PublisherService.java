package com.bsm.bsm.publisher;

import com.bsm.bsm.commonInterface.*;
import java.util.List;

public class PublisherService implements Activable, Searchable<Publisher>, Sortable<Publisher>, Updatable<Publisher>, Addable<Publisher> {
    private PublisherDAO publisherDAO = null;

    public PublisherService() {
        this.publisherDAO = new PublisherDAO();
    }

    @Override
    public void update(Publisher item) {
        publisherDAO.update(item);
    }

    @Override
    public List<Publisher> sort(List<Publisher> items) {
        // Implement sorting logic
        return null;
    }

    @Override
    public List<Publisher> search(String keyword) {
        // Implement search logic
        return null;
    }

    @Override
    public void add(Publisher item) {
        // Implement add logic
    }

    @Override
    public void setEnable(boolean state) {

    }
}
