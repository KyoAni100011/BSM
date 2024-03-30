package com.bsm.bsm.publisher;

import com.bsm.bsm.database.DatabaseConnection;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class PublisherDAO {

    public boolean addPublisher(String name, String address) {
        String QUERY_PUBLISHER = "insert into publisher(name, address) values(?, ?)";
        int rowsAffected = DatabaseConnection.executeUpdate(QUERY_PUBLISHER, name, address);
        return rowsAffected > 0;
    }

    public boolean updatePublisher (String id, String newName, String address) {
        String QUERY_PUBLISHER = "update publisher set name = ?, address = ? where id = ?";
        int rowsAffected = DatabaseConnection.executeUpdate(QUERY_PUBLISHER, newName, address, id);
        return rowsAffected > 0;
    }

    public Publisher getPublisher(String id) {
        AtomicReference<Publisher> publisher = new AtomicReference<>();
        String QUERY_PUBLISHER = "select * from publisher where id = ?";
        DatabaseConnection.executeQuery(QUERY_PUBLISHER, resultSet -> {
            if (resultSet != null && resultSet.next()) {
                String name = resultSet.getString("name");
                String address = resultSet.getString("address");
                boolean isEnabled = resultSet.getBoolean("isEnabled");
                publisher.set(new Publisher(id, name, address, isEnabled));
            }
        }, id);
        return publisher.get();
    }

    public Publisher search(String keyword) {
        // Implement search logic
        return null;
    }

    public boolean checkPublisherExists(String name, String id) {
        AtomicBoolean hasExisted = new AtomicBoolean(false);
        String QUERY_PUBLISHER = "select 1 from publisher where name = ? and id != ?";

        DatabaseConnection.executeQuery(QUERY_PUBLISHER, resultSet -> {
            if (resultSet != null && resultSet.next()) {
                hasExisted.set(true);
            }
        }, name, id);
        return hasExisted.get();
    }
}
