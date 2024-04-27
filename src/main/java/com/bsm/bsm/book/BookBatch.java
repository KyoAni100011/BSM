package com.bsm.bsm.book;

import com.bsm.bsm.sheet.ImportSheet;

import java.math.BigDecimal;

public class BookBatch {
    private String id;
    private int quantity;
    private BigDecimal importPrice;
    private ImportSheet importSheet;
    private Book book;

    public BookBatch(String id, int quantity, BigDecimal importPrice, ImportSheet importSheet, Book book) {
        this.id = id;
        this.quantity = quantity;
        this.importPrice = importPrice;
        this.importSheet = importSheet;
        this.book = book;
    }

    public BookBatch(int quantity, BigDecimal importPrice, ImportSheet importSheet, Book book) {
       this(null, quantity, importPrice, importSheet, book);
    }

    public BookBatch(int quantity, BigDecimal importPrice, Book book) {
        this(null, quantity, importPrice, null, book);
    }

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getImportPrice() {
        return importPrice;
    }

    public void setImportPrice(BigDecimal importPrice) {
        this.importPrice = importPrice;
    }

    public ImportSheet getImportSheet() {
        return importSheet;
    }

    public void setImportSheet(ImportSheet importSheet) {
        this.importSheet = importSheet;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    @Override
    public String toString() {
        return "BookBatch{" +
                "id='" + id + '\'' +
                ", quantity=" + quantity +
                ", importPrice=" + importPrice +
                ", importSheet=" + importSheet +
                ", book=" + book +
                '}';
    }
}
