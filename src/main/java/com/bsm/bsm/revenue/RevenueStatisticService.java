package com.bsm.bsm.revenue;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class RevenueStatisticService {
    private final RevenueStatisticDAO revenueStatisticDAO;

    public RevenueStatisticService()
    {
        revenueStatisticDAO = new RevenueStatisticDAO();
    }

    public List<ResultStatistic> getBookDailyRevenue(String date) throws SQLException {
        return revenueStatisticDAO.getTop10BooksByDailyRevenue(date);
    }

    public List<ResultStatistic> getBookMonthlyRevenue(int year, int month) throws SQLException {
        return revenueStatisticDAO.getTop10BooksByMonthlyRevenue(year, month);
    }

    public List<ResultStatistic> getBookWeeklyRevenue(int year, int week) throws SQLException {
        return revenueStatisticDAO.getTop10BooksByWeeklyRevenue(year, week);
    }

    public List<ResultStatistic> getBookDateToDateRevenue(String startDate, String endDate) throws SQLException {
        return revenueStatisticDAO.getTop10BooksByRevenueDateToDate(startDate, endDate);
    }

    public List<ResultStatistic> getCategoryDailyRevenue(String date) throws SQLException {
        return revenueStatisticDAO.getTop10BooksByDailyRevenue(date);
    }

    public List<ResultStatistic> getCategoryMonthlyRevenue(int year, int month) throws SQLException {
        return revenueStatisticDAO.getTop10CategoriesByMonthlyRevenue(year, month);
    }

    public List<ResultStatistic> getCategoryWeeklyRevenue(int year, int week) throws SQLException {
        return revenueStatisticDAO.getTop10BooksByWeeklyRevenue(year, week);
    }

    public List<ResultStatistic> getCategoryDateToDateRevenue(String startDate, String endDate) throws SQLException {
        return revenueStatisticDAO.getTop10CategoriesByRevenueDateToDate(startDate, endDate);
    }

    public List<ResultStatistic> getCustomerDailyRevenue(String date) throws SQLException {
        return revenueStatisticDAO.getTop10CustomerByDailyRevenue(date);
    }

    public List<ResultStatistic> getCustomerMonthlyRevenue(int year, int month) throws SQLException {
        return revenueStatisticDAO.getTop10CustomerByMonthlyRevenue(year, month);
    }

    public List<ResultStatistic> getCustomerWeeklyRevenue(int year, int week) throws SQLException {
        return revenueStatisticDAO.getTop10CustomerByWeeklyRevenue(year, week);
    }

    public List<ResultStatistic> getCustomerDateToDateRevenue(String startDate, String endDate) throws SQLException {
        return revenueStatisticDAO.getTop10CustomersByRevenueDateToDate(startDate, endDate);
    }

    public List<ResultStatistic> getEmployeeDailyRevenue(String date) throws SQLException {
        return revenueStatisticDAO.getTop10EmployeeByDailyRevenue(date);
    }

    public List<ResultStatistic> getEmployeeMonthlyRevenue(int year, int month) throws SQLException {
        return revenueStatisticDAO.getTop10EmployeeByMonthlyRevenue(year, month);
    }

    public List<ResultStatistic> getEmployeeWeeklyRevenue(int year, int week) throws SQLException {
        return revenueStatisticDAO.getTop10EmployeeByWeeklyRevenue(year, week);
    }

    public List<ResultStatistic> getEmployeeDateToDateRevenue(String startDate, String endDate) throws SQLException {
        return revenueStatisticDAO.getTop10EmployeesByRevenueDateToDate(startDate, endDate);
    }
}
