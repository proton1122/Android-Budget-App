package no.hiof.trondkw.budgetapp.utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.Locale;

public class Utilities {

    public static final DecimalFormat decimalFormatter = new DecimalFormat("0.00");

    public static String getFormattedDate(int day, int month, int year) {
        return day + " / " + month + " / " + year;
    }

    public static String getFormattedDate(LocalDate date) {
        return date.getDayOfMonth() + "/" + date.getMonthValue() + "/" + date.getYear();
    }

    public static String getFormattedSum(double sum) {
        // TODO: Use another locale for formatting?
        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.ENGLISH);
        numberFormat.setMinimumFractionDigits(2);
        numberFormat.setMaximumFractionDigits(2);

        return numberFormat.format(sum);
    }



}
