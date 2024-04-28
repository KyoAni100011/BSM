package com.bsm.bsm.order;

import com.bsm.bsm.customer.Customer;
import com.bsm.bsm.employee.EmployeeModel;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class Order {
    private String id;
    private EmployeeModel employee;
    private Customer customer;
    private String orderDate;
    private BigDecimal totalPrice;

    public Order() {
        // Default constructor
    }

    public Order(String id, EmployeeModel employee, Customer customer, String orderDate, BigDecimal totalPrice) {
        this.id = id;
        this.employee = employee;
        this.customer = customer;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
    }

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public EmployeeModel getEmployee() {
        return employee;
    }

    public void setEmployeeID(EmployeeModel employee) {
        this.employee = employee;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomerID(Customer customer) {
        this.customer = customer;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "OrderSheet{" +
                "id=" + id +
                ", employee=" + employee +
                ", customerID=" + customer +
                ", orderDate=" + orderDate +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
