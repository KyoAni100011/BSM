package com.bsm.bsm.order;

import com.bsm.bsm.customer.Customer;
import com.bsm.bsm.employee.EmployeeModel;
import com.bsm.bsm.user.UserSingleton;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

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
}
