package com.bsm.bsm.sheet;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ImportSheet {
    private String id;
    private int employeeID;
    private LocalDate importDate;
    private int quantity;
    private BigDecimal totalPrice;

    public ImportSheet() {
        // Default constructor
    }

    public ImportSheet(String id, int employeeID, LocalDate importDate, int quantity, BigDecimal totalPrice) {
        this.id = id;
        this.employeeID = employeeID;
        this.importDate = importDate;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public LocalDate getImportDate() {
        return importDate;
    }

    public void setImportDate(LocalDate importDate) {
        this.importDate = importDate;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "ImportSheet{" +
                "id=" + id +
                ", employeeID=" + employeeID +
                ", importDate=" + importDate +
                ", quantity=" + quantity +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
