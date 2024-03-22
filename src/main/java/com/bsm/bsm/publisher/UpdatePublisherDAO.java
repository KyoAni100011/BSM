package com.bsm.bsm.publisher;

import com.bsm.bsm.database.DatabaseConnection;

import java.util.concurrent.atomic.AtomicReference;

public class UpdatePublisherDAO {
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
}
