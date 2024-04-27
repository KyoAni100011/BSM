package com.bsm.bsm.revenue;

import com.bsm.bsm.admin.AdminModel;

import java.util.HashMap;
import java.util.Map;

public class RevenueStatistic<T> {
    private final TimeRange date;
    private final AdminModel inchargeAdmin;
    private final Map<T, Double> revenues;

    public RevenueStatistic(TimeRange date, AdminModel inchargeAdmin) {
        this.date = date;
        this.inchargeAdmin = inchargeAdmin;
        this.revenues = new HashMap<>();
    }

    public void addRevenue(T key, double price) {
        revenues.put(key, price);
    }

    public Map<T, Double> getRevenues() {
        return revenues;
    }
}

