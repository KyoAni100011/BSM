package com.bsm.bsm.revenue;

import com.bsm.bsm.book.Book;
import com.bsm.bsm.category.Category;
import com.bsm.bsm.customer.Customer;
import com.bsm.bsm.employee.EmployeeModel;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class RevenueStatisticService {
    private final RevenueStatisticDAO revenueStatisticDAO;

    public RevenueStatisticService()
    {
        revenueStatisticDAO = new RevenueStatisticDAO();
    }

    public Map<Book, BigDecimal> getBookDailyRevenue(String date) throws SQLException {
        TimeRange timeRange = new TimeRange(date, date);
        return revenueStatisticDAO.getTop10BooksRevenue(timeRange);
    }

    public Map<Book, BigDecimal> getBookMonthlyRevenue(int year, int month) throws SQLException {
        TimeRange timeRange = new TimeRange(year + "-" + month + "-01", year + "-" + month + "-31");
        return revenueStatisticDAO.getTop10BooksRevenue(timeRange);
    }

    public Map<Book, BigDecimal> getBookWeeklyRevenue(int year, int week) throws SQLException {
        TimeRange timeRange = new TimeRange(year + "-" + week + "-01", year + "-" + week + "-31");
        return revenueStatisticDAO.getTop10BooksRevenue(timeRange);
    }

    public Map<Book, BigDecimal> getBookDateToDateRevenue(String startDate, String endDate) throws SQLException {
        TimeRange timeRange = new TimeRange(startDate, endDate);
        return revenueStatisticDAO.getTop10BooksRevenue(timeRange);
    }

    public Map<Category, BigDecimal> getCategoryDailyRevenue(String date) throws SQLException {
        TimeRange timeRange = new TimeRange(date, date);
        return revenueStatisticDAO.getTop10CategoriesRevenue(timeRange);
    }

    public Map<Category, BigDecimal> getCategoryMonthlyRevenue(int year, int month) throws SQLException {
        TimeRange timeRange = new TimeRange(year + "-" + month + "-01", year + "-" + month + "-31");
        return revenueStatisticDAO.getTop10CategoriesRevenue(timeRange);
    }

    public Map<Category, BigDecimal> getCategoryWeeklyRevenue(int year, int week) throws SQLException {
        TimeRange timeRange = new TimeRange(year + "-" + week + "-01", year + "-" + week + "-31");
        return revenueStatisticDAO.getTop10CategoriesRevenue(timeRange);
    }

    public Map<Category, BigDecimal> getCategoryDateToDateRevenue(String startDate, String endDate) throws SQLException {
        TimeRange timeRange = new TimeRange(startDate, endDate);
        return revenueStatisticDAO.getTop10CategoriesRevenue(timeRange);
    }

    public Map<Customer, BigDecimal> getCustomerDailyRevenue(String date) throws SQLException {
        TimeRange timeRange = new TimeRange(date, date);
        return revenueStatisticDAO.getTop10CustomersRevenue(timeRange);
    }

    public Map<Customer, BigDecimal> getCustomerMonthlyRevenue(int year, int month) throws SQLException {
        TimeRange timeRange = new TimeRange(year + "-" + month + "-01", year + "-" + month + "-31");
        return revenueStatisticDAO.getTop10CustomersRevenue(timeRange);
    }

    public Map<Customer, BigDecimal> getCustomerWeeklyRevenue(int year, int week) throws SQLException {
        TimeRange timeRange = new TimeRange(year + "-" + week + "-01", year + "-" + week + "-31");
        return revenueStatisticDAO.getTop10CustomersRevenue(timeRange);
    }

    public Map<Customer, BigDecimal> getCustomerDateToDateRevenue(String startDate, String endDate) throws SQLException {
        TimeRange timeRange = new TimeRange(startDate, endDate);
        return revenueStatisticDAO.getTop10CustomersRevenue(timeRange);
    }

    public Map<EmployeeModel, BigDecimal> getEmployeeDailyRevenue(String date) throws SQLException {
        TimeRange timeRange = new TimeRange(date, date);
        return revenueStatisticDAO.getTop10EmployeeRevenue(timeRange);
    }

    public Map<EmployeeModel, BigDecimal> getEmployeeMonthlyRevenue(int year, int month) throws SQLException {
        TimeRange timeRange = new TimeRange(year + "-" + month + "-01", year + "-" + month + "-31");
        return revenueStatisticDAO.getTop10EmployeeRevenue(timeRange);
    }

    public Map<EmployeeModel, BigDecimal> getEmployeeWeeklyRevenue(int year, int week) throws SQLException {
        TimeRange timeRange = new TimeRange(year + "-" + week + "-01", year + "-" + week + "-31");
        return revenueStatisticDAO.getTop10EmployeeRevenue(timeRange);
    }

    public Map<EmployeeModel, BigDecimal> getEmployeeDateToDateRevenue(String startDate, String endDate) throws SQLException {
        TimeRange timeRange = new TimeRange(startDate, endDate);
        return revenueStatisticDAO.getTop10EmployeeRevenue(timeRange);
    }
}
