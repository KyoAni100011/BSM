package com.bsm.bsm.order;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class Order {
    private UUID id;
    private int employeeID;
    private UUID customerID;
    private LocalDate orderDate;
    private BigDecimal totalPrice;

    public Order() {
        // Default constructor
    }

    public Order(UUID id, int employeeID, UUID customerID, LocalDate orderDate, BigDecimal totalPrice) {
        this.id = id;
        this.employeeID = employeeID;
        this.customerID = customerID;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
    }

    // Getters and setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public int getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public UUID getCustomerID() {
        return customerID;
    }

    public void setCustomerID(UUID customerID) {
        this.customerID = customerID;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
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
                ", employeeID=" + employeeID +
                ", customerID=" + customerID +
                ", orderDate=" + orderDate +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
