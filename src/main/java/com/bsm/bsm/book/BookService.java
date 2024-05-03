package com.bsm.bsm.book;

import com.bsm.bsm.commonInterface.*;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class BookService implements Activable, Searchable<Book>, Sortable<Book>, Updatable<Book>, Addable<Book> {
    private BookDAO bookDAO;

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
                    int bookID1 = Integer.parseInt(book1.getIsbn());
                    int bookID2 = Integer.parseInt(book2.getIsbn());
                    return Integer.compare(bookID1, bookID2);
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
                                book.getIsbn().contains(finalKeyword)
                            )
                .toList();
    }

    @Override
    public boolean add(Book item) {
        try {
            return bookDAO.add(item);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean setEnable(String id, boolean state) {
        return bookDAO.setEnable(id, state);
    }

    public Book getBookByISBN(String isbn) {
        try {
            return bookDAO.getBookByKeyword(isbn);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public Book getBookByName(String name) {
        try {
            return bookDAO.getBookByKeyword(name);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    // use this for check update book name
    public boolean isNameExist(String id, String name) {
        return bookDAO.isNameExist(id, name);
    }

    // use this for check add book name
    public boolean isNameExist(String name) {
        return bookDAO.isNameExist("", name);
    }

    public boolean isSalePriceValid(Book book, BigDecimal salePrice) {
        return bookDAO.isSalePriceValid(book, salePrice);
    }

    public List<String> getLanguages() {
        return bookDAO.getLanguages();
    }

    public List<Book> getAllBooks() {
        try {
            return bookDAO.getAllBooks();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean checkIfBookCanBeEnabled(String isbn) {
        return bookDAO.checkIfBookCanBeEnabled(isbn);
    }
}

