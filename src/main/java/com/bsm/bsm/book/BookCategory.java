package com.bsm.bsm.book;

public class BookCategory {
    private String bookID;
    private String categoryID;

    public BookCategory() {
        // Default constructor
    }

    public BookCategory(String bookID, String categoryID) {
        this.bookID = bookID;
        this.categoryID = categoryID;
    }

    public String getBookID() {
        return bookID;
    }

    public void setBookID(String bookID) {
        this.bookID = bookID;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    @Override
    public String toString() {
        return "BookCategory{" +
                "bookID='" + bookID + '\'' +
                ", categoryID=" + categoryID +
                '}';
    }
}
