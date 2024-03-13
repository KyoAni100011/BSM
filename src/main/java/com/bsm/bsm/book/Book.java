package com.bsm.bsm.book;

import java.math.BigDecimal;
import java.util.Date;

public class Book {
    private String isbn;
    private String title;
    private String publisherId;
    private String authorId;
    private Date publishingDate;
    private String languages;
    private boolean isEnabled;
    private int quantity;
    private BigDecimal salePrice;

    public Book(String isbn, String title, String publisherId, String authorId, Date publishingDate, String languages, boolean isEnabled, int quantity, BigDecimal salePrice) {
        this.isbn = isbn;
        this.title = title;
        this.publisherId = publisherId;
        this.authorId = authorId;
        this.publishingDate = publishingDate;
        this.languages = languages;
        this.isEnabled = isEnabled;
        this.quantity = quantity;
        this.salePrice = salePrice;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(String publisherId) {
        this.publisherId = publisherId;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public Date getPublishingDate() {
        return publishingDate;
    }

    public void setPublishingDate(Date publishingDate) {
        this.publishingDate = publishingDate;
    }

    public String getLanguages() {
        return languages;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
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
        return "Book{" +
                "isbn='" + isbn + '\'' +
                ", title='" + title + '\'' +
                ", publisherId='" + publisherId + '\'' +
                ", authorId='" + authorId + '\'' +
                ", publishingDate=" + publishingDate +
                ", languages='" + languages + '\'' +
                ", isEnabled=" + isEnabled +
                ", quantity=" + quantity +
                ", salePrice=" + salePrice +
                '}';
    }
}