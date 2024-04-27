package com.bsm.bsm.sheet;

import com.bsm.bsm.book.Book;
import com.bsm.bsm.book.BookBatch;
import com.bsm.bsm.book.BookDAO;
import com.bsm.bsm.database.DatabaseConnection;
import com.bsm.bsm.employee.EmployeeModel;
import com.bsm.bsm.user.UserDAO;
import com.bsm.bsm.user.UserModel;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class ImportSheetDAO {

    private final UserDAO userDao = new UserDAO();

    public boolean createImportSheet(ImportSheet importSheet, List<BookBatch> bookBatches) throws SQLException {
        Connection connection = DatabaseConnection.getConnection();
        connection.setAutoCommit(false);

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

    public List<ImportSheet> getAllImportSheets() throws SQLException {
        List<ImportSheet> listImportSheets = new ArrayList<>();
        String QUERY_ALL_IMPORT_SHEET = "select sheet.*, e.userID " +
                "from importsheet sheet join employee e on sheet.employeeID = e.id";
        List<String> listUserId = new ArrayList<>();
        int indexListUserId = 0;
        DatabaseConnection.executeQuery(QUERY_ALL_IMPORT_SHEET, resultSet -> {
            if (resultSet != null) {
                while (resultSet.next()) {
                    String id = resultSet.getString("id");
                    BigDecimal totalPrice = resultSet.getBigDecimal("totalPrice");
                    int quantity = resultSet.getInt("quantity");
                    String importDate = resultSet.getString("importDate");
                    String userID = resultSet.getString("userID");
                    listUserId.add(userID);
                    listImportSheets.add(new ImportSheet(id,null, importDate, quantity, totalPrice));
                }
            }
        });


        for(ImportSheet sheet : listImportSheets)
        {
            EmployeeModel employee = (EmployeeModel) userDao.getUserInfo(listUserId.get(indexListUserId));
            sheet.setEmployee(employee);
            indexListUserId++;
        }

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

    private boolean checkRowAffected(Connection connection, int rowAffected) throws SQLException {
        if (rowAffected == 0) {
            connection.rollback();
            connection.setAutoCommit(true);
            return false;
        }
        return true;
    }
}
