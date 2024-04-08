package com.bsm.bsm.book;

import com.bsm.bsm.commonInterface.*;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public class BookService implements Activable, Searchable<Book>, Sortable<Book>, Updatable<Book>, Addable<Book> {
    private BookDAO bookDAO = null;

    public BookService() {
        this.bookDAO = new BookDAO();
    }

    @Override
    public boolean update(Book item) {
        try {
            return bookDAO.update(item);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Book> sort(List<Book> books, boolean isAscending, String column) {
        return null;
    }

    @Override
    public List<Book> search(String keyword) {
        return null;
    }

    @Override
    public boolean add(Book item) {
        return true;
    }

    @Override
    public boolean setEnable(boolean state) {

        return state;
    }

    public Book getBookByISBN(String isbn) {
        return bookDAO.getBookByISBN(isbn);
    }

    public boolean isNameExist(String name, String id) {
        return bookDAO.isNameExist(name, id);
    }

    public boolean isSalePriceValid(Book book, BigDecimal salePrice) {
        return bookDAO.isSalePriceValid(book, salePrice);
    }

    public List<String> getLanguages() {
        return bookDAO.getLanguages();
    }
}

