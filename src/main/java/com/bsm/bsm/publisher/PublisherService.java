package com.bsm.bsm.publisher;

import com.bsm.bsm.commonInterface.*;
import java.util.List;

public class PublisherService implements Activable, Searchable<Publisher>, Sortable<Publisher>, Updatable<Publisher>, Addable<Publisher> {
    private PublisherDAO publisherDAO = null;

    public PublisherService() {
        this.publisherDAO = new PublisherDAO();
    }

    @Override
    public boolean update(Publisher item) {
        return publisherDAO.update(item);
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
    public void add(Publisher item) {
        // Implement add logic
    }

    @Override
    public boolean setEnable(boolean state) {

        return state;
    }
}
