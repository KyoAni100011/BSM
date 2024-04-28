package com.bsm.bsm.order;

import com.bsm.bsm.customer.Customer;
import com.bsm.bsm.customer.CustomerDAO;
import com.bsm.bsm.database.DatabaseConnection;
import com.bsm.bsm.employee.EmployeeModel;
import com.bsm.bsm.utils.DateUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class OrderDAO {

    public static void main(String[] args) throws SQLException {
        OrderDAO orderDAO = new OrderDAO();
//            public Order(int id, EmployeeModel employee, Customer customer, String orderDate, BigDecimal totalPrice) {
        Order order = new Order(
                new EmployeeModel("22220002", "", "", "", "", "", true, ""),
                new Customer("1", "Phuong", "0917527783", true),
                "2021-09-01",
                null
        );
        List<OrderBooksDetails> orderBooksDetailsList = new ArrayList<>();
        orderDAO.createOrder(order, orderBooksDetailsList);
    }


    public boolean createOrder(Order order, List<OrderBooksDetails> orderBooksDetailsList) throws SQLException {

        Connection connection = DatabaseConnection.getConnection();
        connection.setAutoCommit(false);

        String employeeID = getEmployeeID(connection, order.getEmployee().getId());
        String customerID = CustomerDAO.getCustomerID(connection, order.getCustomer().getName(), order.getCustomer().getPhone());
        String orderID = getOrderID(connection, employeeID, customerID);

        System.out.println("Order ID: " + orderID);
        if (orderID == null) {
            connection.rollback();
            connection.setAutoCommit(true);
            return false;
        }

        for (var orderBooksDetails: orderBooksDetailsList) {
            //check quantity book > orderBookDetails.getQuantity
            String QUERY_GET_QUANTITY = "select quantity from book where isbn = ?";
            AtomicReference<Integer> quantity = new AtomicReference<>();
            DatabaseConnection.executeQuery(connection, QUERY_GET_QUANTITY, resultSet -> {
                if (resultSet != null && resultSet.next()) {
                    quantity.set(resultSet.getInt("quantity"));
                }
            }, orderBooksDetails.getBookBatchID());

            createOrderBooksDetailsList(connection, orderID, orderBooksDetails);
        }

        connection.setAutoCommit(true);
        return true;
    }

    private String getEmployeeID(Connection connection, String userID) throws SQLException {
        String QUERY_GET_EMPLOYEEID = "select e.id from user u join employee e on u.id = e.userID where u.id = ?";
        AtomicReference<String> employeeID = new AtomicReference<>();

        DatabaseConnection.executeQuery(connection, QUERY_GET_EMPLOYEEID, resultSet -> {
            if (resultSet != null && resultSet.next()) {
                employeeID.set(resultSet.getString("id"));
            }
        }, userID);

        return employeeID.get();
    }

    private String getOrderID(Connection connection, String employeeID, String customerID) throws SQLException {
        if (createOrderID(connection, employeeID, customerID)) {
            String QUERY_GET_ORDER_ID = """
                    select max(id) as id
                    from orderSheet
                    where employeeID = ? and customerID = ?
                    """;
            AtomicReference<String> orderID = new AtomicReference<>();

            DatabaseConnection.executeQuery(connection, QUERY_GET_ORDER_ID, resultSet -> {
                if (resultSet != null && resultSet.next()) {
                    orderID.set(resultSet.getString("id"));
                }
            }, employeeID, customerID);

            return orderID.get();
        } else {
            return null;
        }
    }

    private boolean createOrderID(Connection connection, String employeeID, String customerID) throws SQLException {
        String QUERY_CREATE_ORDER = "insert into orderSheet (employeeID, customerID) values (?, ?) ";

        int rowAffected = DatabaseConnection.executeUpdate(connection, QUERY_CREATE_ORDER, employeeID, customerID);

        return rowAffected > 0;
    }

    private boolean createOrderBooksDetailsList(Connection connection, String orderID, OrderBooksDetails orderBooksDetails) {
        orderBooksDetails.setOrderID(orderID);

        return false;
    }

    private boolean checkRowAffected(Connection connection, int rowAffected) throws SQLException {
        if (rowAffected == 0) {
            connection.rollback();
            connection.setAutoCommit(true);
            return false;
        }

        return true;
    }
}
