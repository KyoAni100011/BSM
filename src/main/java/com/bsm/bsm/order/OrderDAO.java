package com.bsm.bsm.order;

import java.util.List;

public class OrderDAO {
    public List<Order> getAllOrdersDateToDate()
    {
        String QUERY_GET_ALL_ORDERS_DATE_TO_DATE = "SELECT \n" +
                "    o.id AS orderID,\n" +
                "    o.employeeID,\n" +
                "    e.employeeName,\n" +
                "    o.customerID,\n" +
                "    c.customerName,\n" +
                "    u.username AS createdBy,\n" +
                "    o.orderDate,\n" +
                "    o.totalPrice\n" +
                "FROM \n" +
                "    `order` o\n" +
                "JOIN \n" +
                "    employee e ON o.employeeID = e.employeeID\n" +
                "JOIN \n" +
                "    customer c ON o.customerID = c.customerID\n" +
                "JOIN \n" +
                "    user u ON o.createdBy = u.userID";
        return null;
    }
}
