package com.bsm.bsm.book;

import com.bsm.bsm.author.Author;
import com.bsm.bsm.category.Category;
import com.bsm.bsm.publisher.Publisher;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class Book {
    private String isbn;
    private String title;
    private Publisher publisher;
    private String publishingDate;
    private String languages;
    private boolean isEnabled;
    private int quantity;
    private BigDecimal salePrice;
    private List<Author> authors;
    private List<Category> categories;

    public Book(String isbn, String title, Publisher publisher, String publishingDate, String languages, boolean isEnabled, int quantity, BigDecimal salePrice, List<Author> authors, List<Category> categories) {
        this.isbn = isbn;
        this.title = title;
        this.publisher = publisher;
        this.publishingDate = publishingDate;
        this.languages = languages;
        this.isEnabled = isEnabled;
        this.quantity = quantity;
        this.salePrice = salePrice;
        this.authors = authors;
        this.categories = categories;
    }

    public Book(String title, Publisher publisher, String publishingDate, String languages, boolean isEnabled, int quantity, BigDecimal salePrice, List<Author> authors, List<Category> categories) {
        this.title = title;
        this.publisher = publisher;
        this.publishingDate = publishingDate;
        this.languages = languages;
        this.isEnabled = isEnabled;
        this.quantity = quantity;
        this.salePrice = salePrice;
        this.authors = authors;
        this.categories = categories;
    }

    public Book(String title, Publisher publisher, String publishingDate, String languages, List<Author> authors, List<Category> categories) {
        this.title = title;
        this.publisher = publisher;
        this.publishingDate = publishingDate;
        this.languages = languages;
        this.authors = authors;
        this.categories = categories;
    }

    public Book(String title, int quantity, BigDecimal salePrice)
    {
        this.title = title;
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

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisherId(Publisher publisher) {
        this.publisher = publisher;
    }

    public String getPublishingDate() {
        return publishingDate;
    }

    public void setPublishingDate(String publishingDate) {
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

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    @Override
    public String toString() {
        return "Book{" +
                "isbn='" + isbn + '\'' +
                ", title='" + title + '\'' +
                ", publisher=" + publisher +
                ", publishingDate='" + publishingDate + '\'' +
                ", languages='" + languages + '\'' +
                ", isEnabled=" + isEnabled +
                ", quantity=" + quantity +
                ", salePrice=" + salePrice +
                ", authors=" + authors +
                '}';
    }
}