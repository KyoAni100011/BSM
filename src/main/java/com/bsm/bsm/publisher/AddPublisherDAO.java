package com.bsm.bsm.publisher;

import com.bsm.bsm.database.DatabaseConnection;

import java.util.concurrent.atomic.AtomicBoolean;

public class AddPublisherDAO {

    public boolean checkPublisherExists(String name) {
        AtomicBoolean hasExisted = new AtomicBoolean(false);
        String QUERY_PUBLISHER = "select 1 from publisher where name = ?";

        DatabaseConnection.executeQuery(QUERY_PUBLISHER, resultSet -> {
            if (resultSet != null && resultSet.next()) {
                hasExisted.set(true);
            }
        }, name);
        return hasExisted.get();
    }

    public boolean addPublisher(String name, String address) {
        String QUERY_PUBLISHER = "insert into publisher(name, address) values(?, ?)";
        int rowsAffected = DatabaseConnection.executeUpdate(QUERY_PUBLISHER, name, address);
        return rowsAffected > 0;
    }
}
