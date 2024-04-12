package com.bsm.bsm.publisher;

import com.bsm.bsm.commonInterface.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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
        List<Publisher> sortedPublishers = new ArrayList<>(publishers);
        String toLowerColumn = column.toLowerCase();
        Comparator<Publisher> comparator = (publisher1, publisher2) -> {
            switch(toLowerColumn) {
                case "id" -> {
                    return Comparator.comparing(Publisher::getId).compare(publisher1, publisher2);
                }
                case "name" -> {
                    return Comparator.comparing(Publisher::getName).compare(publisher1, publisher2);
                }
                case "address" -> {
                    return Comparator.comparing(Publisher::getAddress).compare(publisher1, publisher2);
                }
                case "action" -> {
                    return Comparator.comparing(Publisher::isEnabled).compare(publisher1, publisher2);
                }
                default -> {
                    return 0;
                }
            }
        };

        if (!isAscending) {
            comparator = comparator.reversed();
        }

        return sortedPublishers.stream().sorted(comparator).collect(Collectors.toList());
    }

    @Override
    public List<Publisher> search(String keyword) {
        List<Publisher> publishers = getAllPublishers();
        String finalKeyword = keyword.toLowerCase();
        return publishers.stream()
                .filter(publisher ->
                        publisher.getId().contains(finalKeyword) ||
                        publisher.getName().toLowerCase().contains(finalKeyword) ||
                        publisher.getAddress().toLowerCase().contains(finalKeyword))
                .collect(Collectors.toList());
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
    public boolean setEnable(String id, boolean state) {

        return state;
    }

    public List<Publisher> getAllPublishers() {
        return publisherDAO.getAllPublisher();
    }
    public boolean disablePublisher(String publisherId) {
        try {
            return publisherDAO.disablePublisher(publisherId);
        } catch (Exception e) {
            return false;
        }
    }
    public boolean enablePublisher(String publisherId) {
        try {
            return publisherDAO.enablePublisher(publisherId);
        } catch (Exception e) {
            return false;
        }
    }
}
