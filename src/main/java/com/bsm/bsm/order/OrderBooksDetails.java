package com.bsm.bsm.order;

import com.bsm.bsm.book.BookBatch;

import java.math.BigDecimal;

public class OrderBooksDetails {
    private String orderID;
    private String bookBatchID;
    private int quantity;
    private BigDecimal salePrice;

    public OrderBooksDetails() {
        // Default constructor
    }

    public OrderBooksDetails(String orderID, String bookBatchID, int quantity, BigDecimal salePrice) {
        this.orderID = orderID;
        this.bookBatchID = bookBatchID;
        this.quantity = quantity;
        this.salePrice = salePrice;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getBookBatchID() {
        return bookBatchID;
    }

    public void setBookBatchID(String bookBatchID) {
        this.bookBatchID = bookBatchID;
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
                ", bookBatchID=" + bookBatchID +
                ", quantity=" + quantity +
                ", salePrice=" + salePrice +
                '}';
    }
}