package com.bsm.bsm.customer;

import com.bsm.bsm.database.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicReference;

public class CustomerDAO {

    public static String getCustomerID(Connection connection, Customer customer) throws SQLException {
        String QUERY_GET_CUSTOMERID = "select id from customer where name = ? and phone = ?";
        AtomicReference<String> customerID = new AtomicReference<>();

        DatabaseConnection.executeQuery(connection, QUERY_GET_CUSTOMERID, resultSet -> {
            if (resultSet != null && resultSet.next()) {
                customerID.set(resultSet.getString("id"));
            }
        }, customer.getName(), customer.getPhone());

        if (customerID.get() == null) {
            return createCustomer(connection, customer);
        }

        return customerID.get();
    }

    private static String createCustomer(Connection connection, Customer customer) throws SQLException {
        String QUERY_CREATE_CUSTOMER = "insert into customer(name, phone) values (?, ?)";

        int rowAffected = DatabaseConnection.executeUpdate(connection, QUERY_CREATE_CUSTOMER, customer.getName(), customer.getPhone());
        if (!checkRowAffected(connection, rowAffected)) return null;

        return getCustomerID(connection, customer);
    }

    private static boolean checkRowAffected(Connection connection, int rowAffected) throws SQLException {
        if (rowAffected == 0) {
            connection.rollback();
            connection.setAutoCommit(true);
            return false;
        }
        return true;
    }
}
