package com.bsm.bsm.revenue;

import com.bsm.bsm.database.DatabaseConnection;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RevenueStatisticDAO {
    public List<ResultStatistic> getTop10BooksByDailyRevenue(String date) throws SQLException {
        List<ResultStatistic> bookRevenues = new ArrayList<>();
        String query = "SELECT b.title, SUM(obd.quantity * obd.salePrice) AS revenue FROM orderBooksDetails obd JOIN bookBatch bb ON obd.bookBatchID = bb.id JOIN book b ON bb.bookID = b.isbn JOIN orderSheet os ON obd.orderID = os.id WHERE os.orderDate = ? GROUP BY b.title ORDER BY revenue DESC LIMIT 10;";

        DatabaseConnection.executeQuery(query, resultSet -> {
            while (resultSet.next()) {
                String title = resultSet.getString("title");
                double revenue = resultSet.getDouble("revenue");
                bookRevenues.add(new ResultStatistic(title, revenue));
            }
        }, date);

        return bookRevenues;
    }

    public List<ResultStatistic> getTop10BooksByMonthlyRevenue(int year, int month) throws SQLException {
        List<ResultStatistic> bookRevenues = new ArrayList<>();
        String query = "SELECT b.title, SUM(obd.quantity * obd.salePrice) AS revenue FROM orderBooksDetails obd JOIN bookBatch bb ON obd.bookBatchID = bb.id JOIN book b ON bb.bookID = b.isbn JOIN orderSheet os ON obd.orderID = os.id WHERE YEAR(os.orderDate) = ? AND MONTH(os.orderDate) = ? GROUP BY b.title ORDER BY revenue DESC LIMIT 10;";

        DatabaseConnection.executeQuery(query, resultSet -> {
            while (resultSet.next()) {
                String title = resultSet.getString("title");
                double revenue = resultSet.getDouble("revenue");
                bookRevenues.add(new ResultStatistic(title, revenue));
            }
        }, year, month);

        return bookRevenues;
    }

    public List<ResultStatistic> getTop10BooksByWeeklyRevenue(int year, int week) throws SQLException {
        List<ResultStatistic> bookRevenues = new ArrayList<>();
        String query = "SELECT b.title, SUM(obd.quantity * obd.salePrice) AS revenue FROM orderBooksDetails obd JOIN bookBatch bb ON obd.bookBatchID = bb.id JOIN book b ON bb.bookID = b.isbn JOIN orderSheet os ON obd.orderID = os.id WHERE YEAR(os.orderDate) = ? AND WEEK(os.orderDate) = ? GROUP BY b.title ORDER BY revenue DESC LIMIT 10;";

        DatabaseConnection.executeQuery(query, resultSet -> {
            while (resultSet.next()) {
                String title = resultSet.getString("title");
                double revenue = resultSet.getDouble("revenue");
                bookRevenues.add(new ResultStatistic(title, revenue));
            }
        }, year, week);

        return bookRevenues;
    }

    public List<ResultStatistic> getTop10BooksByRevenueDateToDate(String startDate, String endDate) throws SQLException {
        List<ResultStatistic> bookRevenues = new ArrayList<>();
        String query = "SELECT b.title, SUM(obd.quantity * obd.salePrice) AS revenue FROM orderBooksDetails obd JOIN bookBatch bb ON obd.bookBatchID = bb.id JOIN book b ON bb.bookID = b.isbn JOIN orderSheet os ON obd.orderID = os.id WHERE os.orderDate BETWEEN ? AND ? GROUP BY b.title ORDER BY revenue DESC LIMIT 10;";

        DatabaseConnection.executeQuery(query, resultSet -> {
            while (resultSet.next()) {
                String title = resultSet.getString("title");
                double revenue = resultSet.getDouble("revenue");
                bookRevenues.add(new ResultStatistic(title, revenue));
            }
        }, startDate, endDate);

        return bookRevenues;
    }

    public List<ResultStatistic> getTop10CategoriesByDailyRevenue(String date) throws SQLException {
        List<ResultStatistic> categoryRevenues = new ArrayList<>();
        String query = "SELECT c.name AS title, SUM(od.quantity * od.salePrice) AS revenue FROM orderBooksDetails od JOIN bookBatch bb ON od.bookBatchID = bb.id JOIN book b ON bb.bookID = b.isbn JOIN bookCategory bc ON b.isbn = bc.bookID JOIN category c ON bc.categoryID = c.id JOIN orderSheet os ON od.orderID = os.id WHERE os.orderDate = ? GROUP BY c.name ORDER BY revenue DESC LIMIT 10;";

        DatabaseConnection.executeQuery(query, resultSet -> {
            while (resultSet.next()) {
                String categoryName = resultSet.getString("title");
                double revenue = resultSet.getDouble("revenue");
                categoryRevenues.add(new ResultStatistic(categoryName, revenue));
            }
        }, date);

        return categoryRevenues;
    }

    public List<ResultStatistic> getTop10CategoriesByMonthlyRevenue(int year, int month) throws SQLException {
        List<ResultStatistic> categoryRevenues = new ArrayList<>();
        String query = "SELECT c.name AS title, SUM(od.quantity * od.salePrice) AS revenue FROM orderBooksDetails od JOIN bookBatch bb ON od.bookBatchID = bb.id JOIN book b ON bb.bookID = b.isbn JOIN bookCategory bc ON b.isbn = bc.bookID JOIN category c ON bc.categoryID = c.id JOIN orderSheet os ON od.orderID = os.id WHERE YEAR(os.orderDate) = ? AND MONTH(os.orderDate) = ? GROUP BY c.name ORDER BY revenue DESC LIMIT 10;";

        DatabaseConnection.executeQuery(query, resultSet -> {
            while (resultSet.next()) {
                String categoryName = resultSet.getString("title");
                double revenue = resultSet.getDouble("revenue");
                categoryRevenues.add(new ResultStatistic(categoryName, revenue));
            }
        }, year, month);

        return categoryRevenues;
    }

    public List<ResultStatistic> getTop10CategoriesByWeeklyRevenue(int year, int week) throws SQLException {
        List<ResultStatistic> categoryRevenues = new ArrayList<>();
        String query = "SELECT c.name AS title, SUM(od.quantity * od.salePrice) AS revenue FROM orderBooksDetails od JOIN bookBatch bb ON od.bookBatchID = bb.id JOIN book b ON bb.bookID = b.isbn JOIN bookCategory bc ON b.isbn = bc.bookID JOIN category c ON bc.categoryID = c.id JOIN orderSheet os ON od.orderID = os.id WHERE YEAR(os.orderDate) = ? AND WEEK(os.orderDate) = ? GROUP BY c.name ORDER BY revenue DESC LIMIT 10;";

        DatabaseConnection.executeQuery(query, resultSet -> {
            while (resultSet.next()) {
                String categoryName = resultSet.getString("title");
                double revenue = resultSet.getDouble("revenue");
                categoryRevenues.add(new ResultStatistic(categoryName, revenue));
            }
        }, year, week);

        return categoryRevenues;
    }

    public List<ResultStatistic> getTop10CategoriesByRevenueDateToDate(String startDate, String endDate) throws SQLException {
        List<ResultStatistic> categoryRevenues = new ArrayList<>();
        String query = "SELECT c.name AS title, SUM(od.quantity * od.salePrice) AS revenue FROM orderBooksDetails od JOIN bookBatch bb ON od.bookBatchID = bb.id JOIN book b ON bb.bookID = b.isbn JOIN bookCategory bc ON b.isbn = bc.bookID JOIN category c ON bc.categoryID = c.id JOIN orderSheet os ON od.orderID = os.id WHERE os.orderDate BETWEEN ? AND ? GROUP BY c.name ORDER BY revenue DESC LIMIT 10;";

        DatabaseConnection.executeQuery(query, resultSet -> {
            while (resultSet.next()) {
                String categoryName = resultSet.getString("title");
                double revenue = resultSet.getDouble("revenue");
                categoryRevenues.add(new ResultStatistic(categoryName, revenue));
            }
        }, startDate, endDate);

        return categoryRevenues;
    }

    public List<ResultStatistic> getTop10CustomerByDailyRevenue(String date) throws SQLException {
        List<ResultStatistic> customerRevenues = new ArrayList<>();
        String query = "SELECT cu.name AS title, SUM(odb.quantity * odb.salePrice) AS revenue FROM orderBooksDetails odb JOIN orderSheet os ON odb.orderID = os.id JOIN customer cu ON os.customerID = cu.id WHERE os.orderDate = ? GROUP BY cu.name ORDER BY revenue DESC LIMIT 10;";

        DatabaseConnection.executeQuery(query, resultSet -> {
            while (resultSet.next()) {
                String customerName = resultSet.getString("title");
                double totalRevenue = resultSet.getDouble("revenue");
                customerRevenues.add(new ResultStatistic(customerName, totalRevenue));
            }
        }, date);

        return customerRevenues;
    }

    public List<ResultStatistic> getTop10CustomerByMonthlyRevenue(int month, int year) throws SQLException {
        List<ResultStatistic> customerRevenues = new ArrayList<>();

        String query = "SELECT cu.name AS title, SUM(odb.quantity * odb.salePrice) AS revenue FROM orderBooksDetails odb JOIN orderSheet os ON odb.orderID = os.id JOIN customer cu ON os.customerID = cu.id WHERE YEAR(os.orderDate) = ? AND MONTH(os.orderDate) = ? GROUP BY cu.name ORDER BY revenue DESC LIMIT 10;";

        DatabaseConnection.executeQuery(query, resultSet -> {
            while (resultSet.next()) {
                String customerName = resultSet.getString("title");
                double totalRevenue = resultSet.getDouble("revenue");
                customerRevenues.add(new ResultStatistic(customerName, totalRevenue));
            }
        }, year, month);

        System.out.println(customerRevenues);

        return customerRevenues;
    }

    public List<ResultStatistic> getTop10CustomerByWeeklyRevenue(int year, int week) throws SQLException {
        List<ResultStatistic> customerRevenues = new ArrayList<>();
        String query = "SELECT cu.name AS title, SUM(odb.quantity * odb.salePrice) AS revenue FROM orderBooksDetails odb JOIN orderSheet os ON odb.orderID = os.id JOIN customer cu ON os.customerID = cu.id WHERE YEAR(os.orderDate) = ? AND WEEK(os.orderDate) = ? GROUP BY cu.name ORDER BY revenue DESC LIMIT 10;";

        DatabaseConnection.executeQuery(query, resultSet -> {
            while (resultSet.next()) {
                String customerName = resultSet.getString("title");
                double totalRevenue = resultSet.getDouble("revenue");
                customerRevenues.add(new ResultStatistic(customerName, totalRevenue));
            }
        }, year, week);

        return customerRevenues;
    }

    public List<ResultStatistic> getTop10CustomersByRevenueDateToDate(String startDate, String endDate) throws SQLException {
        List<ResultStatistic> customerRevenues = new ArrayList<>();
        String query = "SELECT cu.name AS title, SUM(odb.quantity * odb.salePrice) AS revenue FROM orderBooksDetails odb JOIN orderSheet os ON odb.orderID = os.id JOIN customer cu ON os.customerID = cu.id WHERE os.orderDate BETWEEN ? AND ? GROUP BY cu.name ORDER BY revenue DESC LIMIT 10;";

        DatabaseConnection.executeQuery(query, resultSet -> {
            while (resultSet.next()) {
                String customerName = resultSet.getString("title");
                double totalRevenue = resultSet.getDouble("revenue");
                customerRevenues.add(new ResultStatistic(customerName, totalRevenue));
            }
        }, startDate, endDate);

        return customerRevenues;
    }

    public List<ResultStatistic> getTop10EmployeeByDailyRevenue(String date) throws SQLException {
        List<ResultStatistic> employeeRevenues = new ArrayList<>();
        String query = "SELECT u.name AS title, SUM(od.quantity * od.salePrice) AS revenue FROM orderBooksDetails od JOIN orderSheet os ON od.orderID = os.id JOIN employee e ON os.employeeID = e.id JOIN user u ON e.userID = u.id WHERE os.orderDate = ? GROUP BY u.name ORDER BY revenue DESC LIMIT 10;\n;";

        DatabaseConnection.executeQuery(query, resultSet -> {
            while (resultSet.next()) {
                String employeeName = resultSet.getString("title");
                double totalRevenue = resultSet.getDouble("revenue");
                employeeRevenues.add(new ResultStatistic(employeeName, totalRevenue));
            }
        }, date);

        return employeeRevenues;
    }

    public List<ResultStatistic> getTop10EmployeeByMonthlyRevenue(int year, int month) throws SQLException {
        List<ResultStatistic> employeeRevenues = new ArrayList<>();
        String query = "SELECT u.name AS title, SUM(od.quantity * od.salePrice) AS revenue FROM orderBooksDetails od JOIN orderSheet os ON od.orderID = os.id JOIN employee e ON os.employeeID = e.id JOIN user u ON e.userID = u.id WHERE YEAR(os.orderDate) = ? AND MONTH(os.orderDate) = ? GROUP BY u.name ORDER BY revenue DESC LIMIT 10;";

        DatabaseConnection.executeQuery(query, resultSet -> {
            while (resultSet.next()) {
                String employeeName = resultSet.getString("title");
                double totalRevenue = resultSet.getDouble("revenue");
                employeeRevenues.add(new ResultStatistic(employeeName, totalRevenue));
            }
        }, year, month);

        return employeeRevenues;
    }

    public List<ResultStatistic> getTop10EmployeeByWeeklyRevenue(int year, int week) throws SQLException {
        List<ResultStatistic> employeeRevenues = new ArrayList<>();
        String query = "SELECT u.name AS title, SUM(od.quantity * od.salePrice) AS revenue FROM orderBooksDetails od JOIN orderSheet os ON od.orderID = os.id JOIN employee e ON os.employeeID = e.id JOIN user u ON e.userID = u.id WHERE YEAR(os.orderDate) = ? AND WEEK(os.orderDate) = ? GROUP BY u.name ORDER BY revenue DESC LIMIT 10;";

        DatabaseConnection.executeQuery(query, resultSet -> {
            while (resultSet.next()) {
                String employeeName = resultSet.getString("title");
                double totalRevenue = resultSet.getDouble("revenue");
                employeeRevenues.add(new ResultStatistic(employeeName, totalRevenue));
            }
        }, year, week);

        return employeeRevenues;
    }

    public List<ResultStatistic> getTop10EmployeesByRevenueDateToDate(String startDate, String endDate) throws SQLException {
        List<ResultStatistic> employeeRevenues = new ArrayList<>();
        String query = "SELECT u.name AS title, SUM(od.quantity * od.salePrice) AS revenue FROM orderBooksDetails od JOIN orderSheet os ON od.orderID = os.id JOIN employee e ON os.employeeID = e.id JOIN user u ON e.userID = u.id WHERE os.orderDate BETWEEN ? AND ? GROUP BY u.name ORDER BY revenue DESC LIMIT 10;";

        DatabaseConnection.executeQuery(query, resultSet -> {
            while (resultSet.next()) {
                String employeeName = resultSet.getString("title");
                double totalRevenue = resultSet.getDouble("revenue");
                employeeRevenues.add(new ResultStatistic(employeeName, totalRevenue));
            }
        }, startDate, endDate);

        return employeeRevenues;
    }

}
