package com.bsm.bsm.order;

import java.math.BigDecimal;

public class OrderBooksDetails {
    private String orderID;
    private String bookID;
    private String importSheetID;
    private int quantity;
    private BigDecimal salePrice;

    public OrderBooksDetails() {
        // Default constructor
    }

    public OrderBooksDetails(String orderID, String bookID, String importSheetID, int quantity, BigDecimal salePrice) {
        this.orderID = orderID;
        this.bookID = bookID;
        this.importSheetID = importSheetID;
        this.quantity = quantity;
        this.salePrice = salePrice;
    }

    // Getters and setters
    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getBookID() {
        return bookID;
    }

    public void setBookID(String bookID) {
        this.bookID = bookID;
    }

    public String getImportSheetID() {
        return importSheetID;
    }

    public void setImportSheetID(String importSheetID) {
        this.importSheetID = importSheetID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }

    @Override
    public String toString() {
        return "OrderBooksDetails{" +
                "orderID=" + orderID +
                ", bookID='" + bookID + '\'' +
                ", importSheetID=" + importSheetID +
                ", quantity=" + quantity +
                ", salePrice=" + salePrice +
                '}';
    }
}
