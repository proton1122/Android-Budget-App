package no.hiof.trondkw.budgetapp.utils;

import java.time.LocalDate;

public class Utilities {


    public static String getFormattedDate(int day, int month, int year) {
        return day + " / " + month + " / " + year;
    }

    public static String getFormattedDate(LocalDate date) {
        return date.getDayOfMonth() + "/" + date.getMonthValue() + "/" + date.getYear();
    }

}
