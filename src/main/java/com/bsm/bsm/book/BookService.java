package com.bsm.bsm.book;

import com.bsm.bsm.commonInterface.*;

import java.util.List;

public class BookService implements Activable, Searchable<Book>, Sortable<Book>, Updatable<Book>, Addable<Book> {
    private BookDAO bookDAO = null;

    public BookService() {
        this.bookDAO = new BookDAO();
    }

    @Override
    public boolean update(Book item) {
        return bookDAO.update(item);
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
}

