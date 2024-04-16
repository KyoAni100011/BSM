package com.bsm.bsm.sheet;

import com.bsm.bsm.employee.EmployeeModel;

import java.math.BigDecimal;

public class ImportSheet {
    private String id;
    private EmployeeModel employee;
    private String importDate;
    private int quantity;
    private BigDecimal totalPrice;

    public ImportSheet(String id, EmployeeModel employee, String importDate, int quantity, BigDecimal totalPrice) {
        this.id = id;
        this.employee = employee;
        this.importDate = importDate;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    public ImportSheet(EmployeeModel employee, String importDate, int quantity, BigDecimal totalPrice) {
        this(null, employee, importDate, quantity,totalPrice);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public EmployeeModel getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeModel employee) {
        this.employee = employee;
    }

    public String getImportDate() {
        return importDate;
    }

    public void setImportDate(String importDate) {
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
                "id='" + id + '\'' +
                ", employee=" + employee +
                ", importDate='" + importDate + '\'' +
                ", quantity=" + quantity +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
