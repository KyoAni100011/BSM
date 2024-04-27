package com.bsm.bsm.book;

import com.bsm.bsm.author.Author;
import com.bsm.bsm.category.Category;
import com.bsm.bsm.database.DatabaseConnection;
import com.bsm.bsm.publisher.Publisher;
import com.bsm.bsm.utils.DateUtils;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class BookDAO {
    // get list of languages
    public List<String> getLanguages() {
        List<String> languages = new ArrayList<>();
        String QUERY_LANGUAGES = "select name from language";
        DatabaseConnection.executeQuery(QUERY_LANGUAGES, resultSet -> {
            if (resultSet != null) {
                while (resultSet.next()) {
                    languages.add(resultSet.getString("name"));
                }
            }
        });
        return languages;
    }

    // get publisher of a book
    private Publisher getBookPublisher(String isbn) {
        String QUERY_PUBLISHER = "select p.id as publisher_id, p.name as publisher_name, p.isEnabled  from book b join publisher p on b.publisherId = p.id where isbn = ?";
        AtomicReference<Publisher> publisher = new AtomicReference<>();

        DatabaseConnection.executeQuery(QUERY_PUBLISHER, resultSet -> {
            if (resultSet != null && resultSet.next()) {
                String id = resultSet.getString("publisher_id");
                String name = resultSet.getString("publisher_name");
                boolean isEnabled = resultSet.getBoolean("isEnabled");
                publisher.set(new Publisher(id, name, isEnabled));
            }
        }, isbn);

        return publisher.get();
    }

    // get list of authors of a book
    private List<Author> getBookAuthors(String isbn) {
        List<Author> authors = new ArrayList<>();
        String QUERY_AUTHORS = "select a.id as author_id, a.name as author_name, a.isEnabled from book b join bookAuthor ba on b.isbn = ba.bookId join author a on ba.authorID = a.id where b.isbn = ?";

        DatabaseConnection.executeQuery(QUERY_AUTHORS, resultSet -> {
            if (resultSet != null) {
                while (resultSet.next()) {
                    String id = resultSet.getString("author_id");
                    String name = resultSet.getString("author_name");
                    boolean isEnabled = resultSet.getBoolean("isEnabled");
                    authors.add(new Author(id, name, isEnabled));
                }
            }
        }, isbn);

        return authors;
    }

    // get list of categories of a book
    private List<Category> getBookCategories(String isbn) {
        List<Category> categories = new ArrayList<>();
        String QUERY_CATEGORIES = "select c.id as category_id, c.name as category_name, c.isEnabled from book b join bookCategory bc on b.isbn = bc.bookID join category c on bc.categoryID = c.id where b.isbn = ?";

        DatabaseConnection.executeQuery(QUERY_CATEGORIES, resultSet -> {
            if (resultSet != null) {
                while (resultSet.next()) {
                    String id = resultSet.getString("category_id");
                    String name = resultSet.getString("category_name");
                    boolean isEnabled = resultSet.getBoolean("isEnabled");
                    categories.add(new Category(id, name, isEnabled));
                }
            }
        }, isbn);

        return categories;
    }

    public Book getBookByISBN(String isbn) {
        List<Author> authors = getBookAuthors(isbn);
        List<Category> categories = getBookCategories(isbn);
        Publisher publisher = getBookPublisher(isbn);

        AtomicReference<Book> book = new AtomicReference<>();
        String QUERY_GET_BOOK_BY_ISBN = "select * from book where isbn = ?";
        DatabaseConnection.executeQuery(QUERY_GET_BOOK_BY_ISBN, resultSet -> {
            if (resultSet != null && resultSet.next()) {
                String title = resultSet.getString("title");
                String publishingDate = resultSet.getString("publishingDate");
                publishingDate = DateUtils.convertDOBFormat(publishingDate); // convert to dd/MM/yyyy
                String language = resultSet.getString("language");
                boolean isEnabled = resultSet.getBoolean("isEnabled");
                int quantity = resultSet.getInt("quantity");
                BigDecimal salePrice = resultSet.getBigDecimal("salePrice");

                book.set(new Book(isbn, title, publisher, publishingDate, language, isEnabled, quantity, salePrice, authors, categories));
            }
        }, isbn);
        return book.get();
    }

    public Book getBookByName(String name) {
        String QUERY_GET_BOOKID = "select isbn from book where title = ?";
        AtomicReference<String> bookID = new AtomicReference<>();

        DatabaseConnection.executeQuery(QUERY_GET_BOOKID, resultSet -> {
            if (resultSet != null && resultSet.next()) {
                bookID.set(resultSet.getString("isbn"));
            }
        }, name);

        return getBookByISBN(bookID.get());
    }

    private boolean checkRowAffected(Connection connection, int rowAffected) throws SQLException {
        if (rowAffected == 0) {
            connection.rollback();
            connection.setAutoCommit(true);
            return false;
        }
        return true;
    }

    public boolean update(Book book) throws SQLException {
        Connection connection = DatabaseConnection.getConnection();
        connection.setAutoCommit(false);

        String formattedDate = DateUtils.formatDOB(book.getPublishingDate()); // convert date to yyyy-MM-dd
        String QUERY_UPDATE_BOOK = "update book set title = ?, publisherID = ?, publishingDate = ?, language = ?, salePrice = ?  where isbn = ?";
        int rowAffected = DatabaseConnection.executeUpdate(connection, QUERY_UPDATE_BOOK,
                book.getTitle(), book.getPublisher().getId(), formattedDate,
                book.getLanguages(), book.getSalePrice(), book.getIsbn());
        if (!checkRowAffected(connection, rowAffected)) return false;

        String QUERY_DELETE_BOOK_AUTHOR = "delete from bookAuthor where bookID = ?";
        int rowAffectedAuthor = DatabaseConnection.executeUpdate(connection, QUERY_DELETE_BOOK_AUTHOR, book.getIsbn());
        if (!checkRowAffected(connection, rowAffectedAuthor)) return false;
        String QUERY_INSERT_BOOK_AUTHOR = "insert into bookAuthor (bookID, authorID) values (?, ?)";
        for (var author : book.getAuthors()) {
            int rowAffectedAuthorInsert = DatabaseConnection.executeUpdate(connection, QUERY_INSERT_BOOK_AUTHOR, book.getIsbn(), author.getId());
            if (!checkRowAffected(connection, rowAffectedAuthorInsert)) return false;
        }

        String QUERY_DELETE_BOOK_CATEGORY = "delete from bookCategory where bookID = ?";
        int rowAffectedCategory = DatabaseConnection.executeUpdate(connection, QUERY_DELETE_BOOK_CATEGORY, book.getIsbn());
        if (!checkRowAffected(connection, rowAffectedCategory)) return false;
        String QUERY_INSERT_BOOK_CATEGORY = "insert into bookCategory (bookID, categoryID) values (?, ?)";
        for (var category : book.getCategories()) {
            int rowAffectedCategoryInsert = DatabaseConnection.executeUpdate(connection, QUERY_INSERT_BOOK_CATEGORY, book.getIsbn(), category.getId());
            if (!checkRowAffected(connection, rowAffectedCategoryInsert)) return false;
        }

        connection.commit();
        connection.setAutoCommit(true);

        return true;
    }

    // get isbn all books
    public List<String> getAllISBN() {
        List<String> isbns = new ArrayList<>();
        String QUERY_ALL_ISBN = "select isbn from book";
        DatabaseConnection.executeQuery(QUERY_ALL_ISBN, resultSet -> {
            if (resultSet != null) {
                while (resultSet.next()) {
                    isbns.add(resultSet.getString("isbn"));
                }
            }
        });
        return isbns;
    }

    // get all books from list isbn
    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        List<String> isbns = getAllISBN();
        for (var isbn : isbns) {
            books.add(getBookByISBN(isbn));
        }
        return books;
    }


    public boolean add(Book book) throws SQLException {
        Connection connection = DatabaseConnection.getConnection();
        connection.setAutoCommit(false);

        // insert into table book
        System.out.println("here" + book.getPublishingDate());
        String formattedDate = (book.getPublishingDate().matches("\\d{4}-\\d{2}-\\d{2}"))
                ? book.getPublishingDate() : DateUtils.formatDOB(book.getPublishingDate());
        String QUERY_ADD_BOOK = "insert into book(title, publisherID, publishingDate, language) values (?, ?, ?, ?)";
        int rowAffected = DatabaseConnection.executeUpdate(connection, QUERY_ADD_BOOK,
                book.getTitle(), book.getPublisher().getId(),
                formattedDate, book.getLanguages());

        if (!checkRowAffected(connection, rowAffected)) return false;
        connection.commit(); // commit to save book before insert into bookAuthor and bookCategory

        // retrieve isbn
        String QUERY_GET_ISBN_BOOK = "select isbn from book where title = ?";
        AtomicReference<String> bookIsbn = new AtomicReference<>();
        DatabaseConnection.executeQuery(connection,QUERY_GET_ISBN_BOOK, resultSet -> {
            if (resultSet != null && resultSet.next()) {
                bookIsbn.set(resultSet.getString("isbn"));
            }
        }, book.getTitle());

        // insert into table bookAuthor
        String QUERY_ADD_BOOKAUTHOR = "insert into bookAuthor values (?, ?)";
        for (var item : book.getAuthors()) {
            rowAffected = DatabaseConnection.executeUpdate(connection, QUERY_ADD_BOOKAUTHOR, bookIsbn.get(), item.getId());
            if (!checkRowAffected(connection, rowAffected)) return false;
        }

        //insert into table bookCategory
        String QUERY_ADD_BOOKCATEGORY = "insert into bookCategory values (?, ?)";
        for (var item : book.getCategories()) {
            rowAffected = DatabaseConnection.executeUpdate(connection, QUERY_ADD_BOOKCATEGORY, bookIsbn.get(), item.getId());
            if (!checkRowAffected(connection, rowAffected)) return false;
        }

        connection.commit();
        connection.setAutoCommit(true);
        return true;
    }

    public boolean isNameExist(String id, String name) {
        String QUERY_CHECK_NAME = "select isbn from book where title = ? and isbn != ?";
        AtomicReference<Boolean> isExist = new AtomicReference<>(false);

        DatabaseConnection.executeQuery(QUERY_CHECK_NAME, resultSet -> {
            if (resultSet != null && resultSet.next()) {
                isExist.set(true);
            }
        }, name, id);

        return isExist.get();
    }

    // check sale price > import price * 1.1, with max import price of book
    public boolean isSalePriceValid(Book book, BigDecimal salePrice) {
        AtomicReference<Boolean> isValid = new AtomicReference<>(true);

        String QUERY_IMPORT_PRICE = "select max(importPrice) as import_price from bookBatch where bookID = ?";
        DatabaseConnection.executeQuery(QUERY_IMPORT_PRICE, resultSet -> {
            if (resultSet != null && resultSet.next()) {
                BigDecimal importPrice = resultSet.getBigDecimal("import_price");

                importPrice = (importPrice == null) ? new BigDecimal("0") : importPrice;
                if (salePrice.compareTo(importPrice.multiply(new BigDecimal("1.1"))) < 0) {
                    isValid.set(false);
                }
            }
        }, book.getIsbn());

        return isValid.get();
    }

    public boolean setEnable(String id, boolean state) {
        String QUERY_SET_ENABLE = "update book set isEnabled = ? where isbn = ?";
        int rowAffected = DatabaseConnection.executeUpdate(QUERY_SET_ENABLE, state, id);
        return rowAffected > 0;
    }
}