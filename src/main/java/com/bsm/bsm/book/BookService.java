package com.bsm.bsm.book;

import com.bsm.bsm.commonInterface.*;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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
        List<Book> sortedBooks = new ArrayList<>(books);
        Comparator<Book> comparator = (book1, book2) -> {
            switch (column) {
                case "isbn" -> {
                    return Comparator.comparing(Book::getIsbn).compare(book1, book2);
                }
                case "book name" -> {
                    return Comparator.comparing(Book::getTitle).compare(book1, book2);
                }
                case "quantity" -> {
                    return Comparator.comparing(Book::getQuantity).compare(book1, book2);
                }
                case "price" -> {
                    return Comparator.comparing(Book::getSalePrice).compare(book1, book2);
                }
                case "enable/disable" -> {
                    return Comparator.comparing(Book::isEnabled).compare(book1, book2);
                }
                default -> {
                    return 0;
                }
            }
        };

        if (!isAscending) {
            comparator = comparator.reversed();
        }

        return sortedBooks.stream().sorted(comparator).collect(Collectors.toList());
    }

    @Override
    public List<Book> search(String keyword) {
        List<Book> books = getAllBooks();
        String finalKeyword = keyword.toLowerCase();
        return books.stream()
                .filter(book ->
                        book.getTitle().toLowerCase().contains(finalKeyword) ||
                                book.getIsbn().contains(finalKeyword))
                .collect(Collectors.toList());
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

    public boolean isNameExist(String id, String name) {
        return bookDAO.isNameExist(id, name);
    }

    public boolean isSalePriceValid(Book book, BigDecimal salePrice) {
        return bookDAO.isSalePriceValid(book, salePrice);
    }

    public List<String> getLanguages() {
        return bookDAO.getLanguages();
    }

    public List<Book> getAllBooks() {
        return bookDAO.getAllBooks();
    }
}

