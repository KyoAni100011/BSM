package com.bsm.bsm.order;

import com.bsm.bsm.book.BookBatch;

import java.math.BigDecimal;

public class OrderBooksDetails {
    private int orderID;
    private BookBatch bookBatch;
    private int quantity;
    private BigDecimal salePrice;

    public OrderBooksDetails() {
        // Default constructor
    }

    public OrderBooksDetails(int orderID, BookBatch bookBatch, int quantity, BigDecimal salePrice) {
        this.orderID = orderID;
        this.bookBatch = bookBatch;
        this.quantity = quantity;
        this.salePrice = salePrice;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public BookBatch getBookBatch() {
        return bookBatch;
    }

    public void setBookBatch(BookBatch bookBatch) {
        this.bookBatch = bookBatch;
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
                ", bookBatch=" + bookBatch +
                ", quantity=" + quantity +
                ", salePrice=" + salePrice +
                '}';
    }
}