package com.bsm.bsm.book;

import java.math.BigDecimal;
import java.util.UUID;

public class BookBatch {
    private String id;
    private int quantity;
    private BigDecimal importPrice;
    private String importSheetID;

    public BookBatch() {
        // Default constructor
    }

    public BookBatch(String id, int quantity, BigDecimal importPrice, String importSheetID) {
        this.id = id;
        this.quantity = quantity;
        this.importPrice = importPrice;
        this.importSheetID = importSheetID;
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

    public String getImportSheetID() {
        return importSheetID;
    }

    public void setImportSheetID(String importSheetID) {
        this.importSheetID = importSheetID;
    }

    @Override
    public String toString() {
        return "BookBatch{" +
                "id='" + id + '\'' +
                ", quantity=" + quantity +
                ", importPrice=" + importPrice +
                ", importSheetID=" + importSheetID +
                '}';
    }
}
