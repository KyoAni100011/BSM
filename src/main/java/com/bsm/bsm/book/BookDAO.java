package com.bsm.bsm.book;

import com.bsm.bsm.author.Author;
import com.bsm.bsm.category.Category;
import com.bsm.bsm.database.DatabaseConnection;
import com.bsm.bsm.publisher.Publisher;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class BookDAO {

    private Publisher getBookPublisher(String isbn) {
        String QUERY_PUBLISHER = "select publisher_name from book where isbn = ?";
        AtomicReference<Publisher> publisher = new AtomicReference<>();

        DatabaseConnection.executeQuery(QUERY_PUBLISHER, resultSet -> {
            publisher.set(new Publisher(resultSet.getString("publisher_name")));
        }, isbn);

        return publisher.get();
    }

    private List<Author> getBookAuthors(String isbn) {
        List<Author> authors = new ArrayList<>();
        String QUERY_AUTHORS = "select a.name from book b join bookAuthor ba on b.isbn = ba.bookId join author a on ba.authorID = a.id where b.isbn = ?";

        DatabaseConnection.executeQuery(QUERY_AUTHORS, resultSet -> {
            if (resultSet != null) {
                while (resultSet.next()) {
                    authors.add(new Author(resultSet.getString("author_name")));
                }
            }
        }, isbn);

        return authors;
    }

    private List<Category> getBookCategories(String isbn) {
        List<Category> categories = new ArrayList<>();
        String QUERY_CATEGORIES = "select c.name from book b join bookCategory bc on b.isbn = bc.bookID join category c on bc.categoryID = c.id where b.isbn = ?";

        DatabaseConnection.executeQuery(QUERY_CATEGORIES, resultSet -> {
            if (resultSet != null) {
                while (resultSet.next()) {
                    categories.add(new Category(resultSet.getString("category_name")));
                }
            }
        }, isbn);

        return categories;
    }

    public Book getBookByISBN(String isbn) {
        List<Author> authors = getBookAuthors(isbn);
        List<Category> categories = getBookCategories(isbn);

        AtomicReference<Book> book = new AtomicReference<>();
        String QUERY_GET_BOOK_BY_ISBN = "select * from book where isbn = ?";
        DatabaseConnection.executeQuery(QUERY_GET_BOOK_BY_ISBN, resultSet -> {
            if (resultSet != null && resultSet.next()) {
                String title = resultSet.getString("title");
                Publisher publisher = new Publisher(resultSet.getString("publisher_name"));
                String publishingDate = resultSet.getString("publishingDate");
                String languages = resultSet.getString("languages");
                boolean isEnabled = resultSet.getBoolean("isEnabled");
                int quantity = resultSet.getInt("quantity");
                BigDecimal salePrice = resultSet.getBigDecimal("salePrice");

                book.set(new Book(isbn, title, publisher, publishingDate, languages, isEnabled, quantity, salePrice, authors, categories));
            }
        }, isbn);
        return book.get();
    }

    public boolean update(Book book) {
        try {
            // Implement update logic
            DatabaseConnection.autoCommit(false);

            // update information of book
            String QUERY_UPDATE_BOOK = """
                    update book
                    set title = ?, publisherID = ?, publishingDate = ?, languages = ?, quantity = ?, salePrice = ?
                    where isbn = ?
                    """;
            DatabaseConnection.executeUpdate(QUERY_UPDATE_BOOK,
                    book.getTitle(), book.getPublisher().getId(), book.getPublishingDate(),
                    book.getLanguages(), book.getQuantity(), book.getSalePrice(), book.getIsbn());


            // update information of book author
            String QUERY_DELETE_BOOK_AUTHOR = "delete from bookAuthor where bookID = ?";
            DatabaseConnection.executeUpdate(QUERY_DELETE_BOOK_AUTHOR, book.getIsbn());
            String QUERY_INSERT_BOOK_AUTHOR = "insert into bookAuthor (bookID, authorID) values (?, ?)";
            for (var author : book.getAuthors()) {
                DatabaseConnection.executeUpdate(QUERY_INSERT_BOOK_AUTHOR, book.getIsbn(), author.getId());
            }

            // update information of book category
            String QUERY_DELETE_BOOK_CATEGORY = "delete from bookCategory where bookID = ?";
            DatabaseConnection.executeUpdate(QUERY_DELETE_BOOK_CATEGORY, book.getIsbn());
            String QUERY_INSERT_BOOK_CATEGORY = "insert into bookCategory (bookID, categoryID) values (?, ?)";
            for (var category : book.getCategories()) {
                DatabaseConnection.executeUpdate(QUERY_INSERT_BOOK_CATEGORY, book.getIsbn(), category.getId());
            }

            DatabaseConnection.commit();
            return true;
        } catch (SQLException e) {
            DatabaseConnection.rollback();
            return false;
        } finally {
            try {
                DatabaseConnection.autoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void add(Book book) {
        // Implement add logic
    }

    public Book search(String keyword) {
        // Implement search logic
        return null;
    }
}