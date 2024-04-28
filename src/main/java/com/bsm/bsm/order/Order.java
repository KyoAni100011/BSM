package com.bsm.bsm.order;

import com.bsm.bsm.customer.Customer;
import com.bsm.bsm.employee.EmployeeModel;

import java.math.BigDecimal;

public class Order {
    private int id;
    private EmployeeModel employee;
    private Customer customer;
    private String orderDate;
    private BigDecimal totalPrice;

    public Order() {
        // Default constructor
    }

    public Order(int id, EmployeeModel employee, Customer customer, String orderDate, BigDecimal totalPrice) {
        this.id = id;
        this.employee = employee;
        this.customer = customer;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
    }

    public Order(EmployeeModel employee, Customer customer, String orderDate, BigDecimal totalPrice) {
        this(0, employee, customer, orderDate, totalPrice);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public EmployeeModel getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeModel employee) {
        this.employee = employee;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
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
        return "Order{" +
                "id=" + id +
                ", employee=" + employee +
                ", customer=" + customer +
                ", orderDate='" + orderDate + '\'' +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
