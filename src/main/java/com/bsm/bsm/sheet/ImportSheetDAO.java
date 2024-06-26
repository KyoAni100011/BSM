package com.bsm.bsm.sheet;

import com.bsm.bsm.book.Book;
import com.bsm.bsm.book.BookBatch;
import com.bsm.bsm.book.BookDAO;
import com.bsm.bsm.database.DatabaseConnection;
import com.bsm.bsm.employee.EmployeeModel;
import com.bsm.bsm.user.UserDAO;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class ImportSheetDAO {

    private final UserDAO userDao = new UserDAO();

    public boolean updateSalePrice(List<BookBatch> bookBatches) throws SQLException {

        String QUERY_UPDATE_SALE_PRICE = "update book set salePrice = ? where isbn = ?";
        Connection connection = DatabaseConnection.getConnection();
        connection.setAutoCommit(false);

        for (var bookBatch: bookBatches) {
            String isbn = bookBatch.getBook().getIsbn();
            BigDecimal salePrice = bookBatch.getBook().getSalePrice();
            DatabaseConnection.executeUpdate(connection, QUERY_UPDATE_SALE_PRICE, salePrice, isbn);
        }

        connection.commit();
        connection.setAutoCommit(true);
        return true;
    };

    public boolean createImportSheet(ImportSheet importSheet) throws SQLException {
        Connection connection = DatabaseConnection.getConnection();
        connection.setAutoCommit(false);

        List<BookBatch> bookBatches = new ArrayList<>();
        for (var entry: importSheet.getImportBooks().entrySet()) {
            bookBatches.add(entry.getKey());
        }

        String employeeId = getEmployeeId(connection, importSheet.getEmployee().getId());
        String importSheetID = createImportSheet(connection, employeeId, importSheet);

        for (var bookBatch: bookBatches) {
            setBookIsbn(connection, bookBatch);
            insertBookBatch(connection, bookBatch, importSheetID);
        }

        connection.commit();
        connection.setAutoCommit(true);
        return true;
    }

    private void insertBookBatch(Connection connection, BookBatch bookBatch, String importSheetID) throws SQLException {
        String QUERY_INSERT_BOOK_BATCH = "insert into bookBatch(quantity, importPrice, importSheetID, bookID) values (?, ?, ?, ?)";
        int rowAffected = DatabaseConnection.executeUpdate(connection, QUERY_INSERT_BOOK_BATCH,
                bookBatch.getQuantity(), bookBatch.getImportPrice(), importSheetID, bookBatch.getBook().getIsbn());
        if (!checkRowAffected(connection, rowAffected)) throw new SQLException("Insert bookBatch failed");
    }

    public List<Book> getISheetBookDetails(String idSheet) {
        List<Book> bookDetails = new ArrayList<>();
        String query = """
                SELECT b.isbn as bookID, b.title as bookTitle, bBatch.quantity as quantity, bBatch.importPrice as price
                FROM importSheet isheet
                JOIN bookBatch bBatch ON isheet.id = bBatch.importSheetID
                JOIN book b ON bBatch.bookID = b.isbn
                WHERE isheet.id = ?
                """;


        DatabaseConnection.executeQuery(query, resultSet -> {
            if (resultSet != null) {
                while (resultSet.next()) {
                    String isbn = resultSet.getString("bookID");
                    String title = resultSet.getString("bookTitle");
                    BigDecimal price = resultSet.getBigDecimal("price");
                    int quantity = resultSet.getInt("quantity");
                    bookDetails.add(new Book(isbn, title, quantity ,price));
                }
            }
        }, idSheet);
        return bookDetails;
    }


    private void setBookIsbn(Connection connection, BookBatch bookBatch) throws SQLException {
        String QUERY_GET_BOOK_ID = "select isbn from book where title = ?";

        DatabaseConnection.executeQuery(connection, QUERY_GET_BOOK_ID, resultSet -> {
            if (resultSet != null && resultSet.next()) {
                bookBatch.getBook().setIsbn(resultSet.getString("isbn"));
            } else {
                System.out.println("Book not found");
                addBook(connection, bookBatch.getBook());
                connection.setAutoCommit(false);

                DatabaseConnection.executeQuery(connection, QUERY_GET_BOOK_ID, resultSet1 -> {
                    if (resultSet1 != null && resultSet1.next()) {
                        bookBatch.getBook().setIsbn(resultSet1.getString("isbn"));
                    }
                }, bookBatch.getBook().getTitle());
            }
        }, bookBatch.getBook().getTitle());
    }

    private void addBook(Connection connection, Book book) throws SQLException {
        BookDAO bookDAO = new BookDAO();
        bookDAO.add(book);
    }

    private String createImportSheet(Connection connection, String employeeID, ImportSheet importSheet) throws SQLException {
        String QUERY_CREATE_IMPORT_SHEET = "insert into importSheet(employeeID, importDate, quantity, totalPrice) values (?, ?, ?, ?)";

        int rowAffected = DatabaseConnection.executeUpdate(connection, QUERY_CREATE_IMPORT_SHEET,
                employeeID, importSheet.getImportDate(), importSheet.getQuantity(), importSheet.getTotalPrice());
        if (!checkRowAffected(connection, rowAffected)) return null;

        return getImportSheetID(connection, employeeID, importSheet);
    }

    public List<ImportSheet> getAllImportSheets() {
        List<ImportSheet> listImportSheets = new ArrayList<>();
        String QUERY_ALL_IMPORT_SHEET = """
            select sheet.*,  u.name as userName
            from importSheet sheet join employee e on sheet.employeeID = e.id
            join user u on e.userID = u.id
            """;

        DatabaseConnection.executeQuery(QUERY_ALL_IMPORT_SHEET, resultSet -> {
            if (resultSet != null) {
                while (resultSet.next()) {
                    String id = resultSet.getString("id");
                    BigDecimal totalPrice = resultSet.getBigDecimal("totalPrice");
                    int quantity = resultSet.getInt("quantity");
                    String importDate = resultSet.getString("importDate");
                    EmployeeModel employee = new EmployeeModel();
                    employee.setName(resultSet.getString("userName"));

                    listImportSheets.add(new ImportSheet(id, employee, importDate, quantity, totalPrice));
                }
            }
        });

        return listImportSheets;
    }

    private String getImportSheetID(Connection connection, String employeeID, ImportSheet importSheet) throws SQLException {
        String QUERY_GET_IMPORT_SHEET_ID = """
            select max(id) as id
            from importSheet
            where employeeID = ? and importDate = ? and quantity = ? and totalPrice = ?
            """;
        AtomicReference<String> importSheetID = new AtomicReference<>();

        DatabaseConnection.executeQuery(connection, QUERY_GET_IMPORT_SHEET_ID, resultSet -> {
            if (resultSet != null && resultSet.next()) {
                importSheetID.set(resultSet.getString("id"));
            }
        }, employeeID, importSheet.getImportDate(), importSheet.getQuantity(), importSheet.getTotalPrice());

        return importSheetID.get();
    }

    public String getImportSheetID (EmployeeModel employee,ImportSheet importSheet) throws SQLException {

        String employeeID = getEmployeeId(employee.getId());

        String QUERY_GET_IMPORT_SHEET_ID = """
            select max(id) as id
            from importSheet
            where employeeID = ? and importDate = ? and quantity = ? and totalPrice = ?
            """;
        AtomicReference<String> importSheetID = new AtomicReference<>();

        DatabaseConnection.executeQuery(QUERY_GET_IMPORT_SHEET_ID, resultSet -> {
            if (resultSet != null && resultSet.next()) {
                importSheetID.set(resultSet.getString("id"));
            }
        }, employeeID, importSheet.getImportDate(), importSheet.getQuantity(), importSheet.getTotalPrice());

        return importSheetID.get();
    }

    private String getEmployeeId(Connection connection, String userID) throws SQLException {
        String QUERY_GET_EMPLOYEEID = "select e.id from user u join employee e on u.id = e.userID where u.id = ?";
        AtomicReference<String> employeeID = new AtomicReference<>();

        DatabaseConnection.executeQuery(connection, QUERY_GET_EMPLOYEEID, resultSet -> {
            if (resultSet != null && resultSet.next()) {
                employeeID.set(resultSet.getString("id"));
            }
        }, userID);

        return employeeID.get();
    }

    private String getEmployeeId(String userID) throws SQLException {
        String QUERY_GET_EMPLOYEEID = "select e.id from user u join employee e on u.id = e.userID where u.id = ?";
        AtomicReference<String> employeeID = new AtomicReference<>();

        DatabaseConnection.executeQuery(QUERY_GET_EMPLOYEEID, resultSet -> {
            if (resultSet != null && resultSet.next()) {
                employeeID.set(resultSet.getString("id"));
            }
        }, userID);

        return employeeID.get();
    }

    private boolean checkRowAffected(Connection connection, int rowAffected) throws SQLException {
        if (rowAffected == 0) {
            connection.rollback();
            connection.setAutoCommit(true);
            return false;
        }
        return true;
    }
}
