package com.bsm.bsm.book;

import com.bsm.bsm.generic.*;

import java.util.List;

public class BookService implements Activable<Book>, Searchable<Book>, Sortable<Book>, Updatable<Book>, Addable<Book> {
    private BookDAO bookDAO = null;

    public BookService() {
        this.bookDAO = new BookDAO();
    }

    @Override
    public void activate(Book item) {
        item.setEnabled(true);
        bookDAO.update(item);
    }

    @Override
    public void deactivate(Book item) {
        item.setEnabled(false);
        bookDAO.update(item);
    }

    @Override
    public boolean isActive(Book item) {
        return false;
    }

    @Override
    public void update(Book item) {
        bookDAO.update(item);
    }

    @Override
    public List<Book> sort(List<Book> items) {
        return null;
    }

    @Override
    public List<Book> search(String keyword) {
        return null;
    }

    @Override
    public void add(Book item) {

    }
}

