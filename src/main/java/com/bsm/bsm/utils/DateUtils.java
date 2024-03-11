package com.bsm.bsm.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtils {
    public static String convertDOBFormat(String dob) {
        return LocalDate.parse(dob, DateTimeFormatter.ofPattern("yyyy-MM-dd")).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public static String formatDOB(String dob) {
        return LocalDate.parse(dob, DateTimeFormatter.ofPattern("dd/MM/yyyy")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

}
