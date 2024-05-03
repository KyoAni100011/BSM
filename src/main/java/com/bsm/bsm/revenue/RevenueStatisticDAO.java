package com.bsm.bsm.revenue;

import com.bsm.bsm.book.Book;
import com.bsm.bsm.category.Category;
import com.bsm.bsm.customer.Customer;
import com.bsm.bsm.database.DatabaseConnection;
import com.bsm.bsm.employee.EmployeeModel;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RevenueStatisticDAO {

    public Map<Book, BigDecimal> getTop10BooksRevenue(TimeRange date) throws SQLException {
        Map<Book, BigDecimal> bookRevenues = new HashMap<>();
        String query = "SELECT b.isbn, b.title, SUM(obd.quantity * obd.salePrice) AS revenue FROM orderBooksDetails obd JOIN bookBatch bb ON obd.bookBatchID = bb.id JOIN book b ON bb.bookID = b.isbn JOIN orderSheet os ON obd.orderID = os.id WHERE os.orderDate BETWEEN ? AND ? GROUP BY b.isbn ORDER BY revenue DESC LIMIT 10;";

        DatabaseConnection.executeQuery(query, resultSet -> {
            while (resultSet.next()) {
                String isbn = resultSet.getString("isbn");
                String title = resultSet.getString("title");
                BigDecimal revenue = resultSet.getBigDecimal("revenue");
                bookRevenues.put(new Book(isbn, title), revenue);
            }
        }, date.getStartDate(), date.getEndDate());

        return bookRevenues;
    }

    public Map<Category, BigDecimal> getTop10CategoriesRevenue(TimeRange date) throws SQLException {
        Map<Category, BigDecimal> categoryRevenues = new HashMap<>();
        String query = "SELECT c.id, c.name, SUM(obd.quantity * obd.salePrice) AS revenue FROM orderBooksDetails obd JOIN bookBatch bb ON obd.bookBatchID = bb.id JOIN book b ON bb.bookID = b.isbn JOIN bookCategory bc ON b.isbn = bc.bookID JOIN category c ON bc.categoryID = c.id JOIN orderSheet os ON obd.orderID = os.id WHERE os.orderDate BETWEEN ? AND ? GROUP BY c.id ORDER BY revenue DESC LIMIT 10;";

        DatabaseConnection.executeQuery(query, resultSet -> {
            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String name = resultSet.getString("name");
                BigDecimal revenue = resultSet.getBigDecimal("revenue");
                categoryRevenues.put(new Category(id, name), revenue);
            }
        }, date.getStartDate(), date.getEndDate());

        return categoryRevenues;
    }

    public Map<Customer, BigDecimal> getTop10CustomersRevenue(TimeRange date) throws SQLException {
        Map<Customer, BigDecimal> customerRevenues = new HashMap<>();
        String query = "SELECT cu.id, cu.name, SUM(odb.quantity * odb.salePrice) AS revenue FROM orderBooksDetails odb JOIN orderSheet os ON odb.orderID = os.id JOIN customer cu ON os.customerID = cu.id WHERE os.orderDate BETWEEN ? AND ? GROUP BY cu.id ORDER BY revenue DESC LIMIT 10;";

        DatabaseConnection.executeQuery(query, resultSet -> {
            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String name = resultSet.getString("name");
                BigDecimal revenue = resultSet.getBigDecimal("revenue");
                customerRevenues.put(new Customer(id, name), revenue);
            }
        }, date.getStartDate(), date.getEndDate());

        return customerRevenues;
    }

   public Map<EmployeeModel, BigDecimal> getTop10EmployeeRevenue(TimeRange date) throws SQLException {
        Map<EmployeeModel, BigDecimal> employeeRevenues = new HashMap<>();
        String query = "SELECT e.id, u.name, SUM(od.quantity * od.salePrice) AS revenue FROM orderBooksDetails od JOIN orderSheet os ON od.orderID = os.id JOIN employee e ON os.employeeID = e.id JOIN user u ON e.userID = u.id WHERE os.orderDate BETWEEN ? AND ? GROUP BY e.id ORDER BY revenue DESC LIMIT 10;";

        DatabaseConnection.executeQuery(query, resultSet -> {
            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String name = resultSet.getString("name");
                BigDecimal revenue = resultSet.getBigDecimal("revenue");
                employeeRevenues.put(new EmployeeModel(id, name), revenue);
            }
        }, date.getStartDate(), date.getEndDate());

        return employeeRevenues;
    }
}
