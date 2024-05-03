package com.bsm.bsm.revenue;

import java.util.Date;

public class TimeRange {
    private String startDate;
    private String endDate;


    public TimeRange(String date, String date1) {
        this.startDate = date;
        this.endDate = date1;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }
}
