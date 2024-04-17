package com.bsm.bsm.revenue;

public class ResultStatistic {
    private String title;
    private double statisticValue;

    public ResultStatistic(String title, double statisticValue) {
        this.title = title;
        this.statisticValue = statisticValue;
    }

    public String getTitle() {
        return title;
    }

    public double getStatisticValue() {
        return statisticValue;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setStatisticValue(double statisticValue) {
        this.statisticValue = statisticValue;
    }
}

