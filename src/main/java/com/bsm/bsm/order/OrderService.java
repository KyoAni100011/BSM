package com.bsm.bsm.order;

import com.bsm.bsm.customer.Customer;
import com.bsm.bsm.employee.EmployeeModel;
import com.bsm.bsm.user.UserSingleton;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class OrderService {

    public OrderDAO orderDAO = new OrderDAO();

    public boolean createOrder(List<String> selectedBooks, List<Integer> quantities, List<BigDecimal> salePrices, Customer customer) {

        EmployeeModel employee = (EmployeeModel) UserSingleton.getInstance().getUser();
        try {
            return orderDAO.createOrder(employee, selectedBooks, quantities, salePrices, customer);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public List<Order> getAllOrders(String condition) {
        return orderDAO.getOrderInfo(condition);
    }

    public List<Order> search(String keyword, String condition) {
        List<Order> orders = getAllOrders(condition);
        String finalKeyword = keyword.toLowerCase();
        return orders.stream()
                .filter(order ->
                        order.getCustomer().getName().toLowerCase().contains(finalKeyword) ||
                                order.getEmployee().getName().toLowerCase().contains(finalKeyword) ||
                                String.valueOf(order.getId()).contains(finalKeyword) ||
                                order.getOrderDate().contains(finalKeyword) ||
                                String.valueOf(order.getTotalPrice()).contains(finalKeyword))
                .collect(Collectors.toList());
    }

    public List<Order> sort(List<Order> orders, boolean isAscending, String column) {
        List<Order> sortedOrders = new ArrayList<>(orders);

        Comparator<Order> comparator = null;

        switch (column.toLowerCase()) {
            case "id" -> comparator = Comparator.comparing(Order::getId);
            case "customer" -> comparator = Comparator.comparing(o -> o.getCustomer().getName());
            case "order date" -> comparator = Comparator.comparing(Order::getOrderDate);
            case "employee" -> comparator = Comparator.comparing(o -> o.getEmployee().getName());
            case "total price" -> comparator = Comparator.comparing(Order::getTotalPrice);
            default -> {
                return sortedOrders;
            }
        }

        if (!isAscending) {
            comparator = comparator.reversed();
        }

        return sortedOrders.stream()
                .sorted(comparator)
                .collect(Collectors.toList());
    }

    public Order getOrderByCustomer(Customer customer) throws SQLException {
        EmployeeModel employee = (EmployeeModel) UserSingleton.getInstance().getUser();
        return orderDAO.getOrderByCustomer(employee, customer);
    }

    public List<OrderBooksDetails> getOrderBookDetails(int orderID) {
        return orderDAO.getOrderBookDetails(orderID);
    }

}
