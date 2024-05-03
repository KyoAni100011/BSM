package com.bsm.bsm.sheet;

import java.math.BigDecimal;

public class BookSheetDetail {
    private String title;

    private BigDecimal price;
    private int quantity;

    public BookSheetDetail() {
    }

    public BookSheetDetail(String title, BigDecimal price, int quantity) {
        this.title = title;
        this.price = price;
        this.quantity = quantity;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
