package no.hiof.trondkw.budgetapp.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.Locale;

public class Utilities {

    public static final DecimalFormat decimalFormatter = new DecimalFormat("0.00");

    public static String getFormattedDate(int day, int month, int year) {
        return day + " / " + month + " / " + year;
    }

    // TODO: test this
    public static String getFormattedDate(String date) {
        LocalDate d = LocalDate.parse(date);

        //return date.getDayOfMonth() + "/" + date.getMonthValue() + "/" + date.getYear();
        return d.getDayOfMonth() + "/" + d.getMonthValue() + "/" + d.getYear();
    }

    public static String getFormattedSum(double sum) {
        // TODO: Use another locale for formatting?
        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.ENGLISH);
        numberFormat.setMinimumFractionDigits(2);
        numberFormat.setMaximumFractionDigits(2);

        return numberFormat.format(sum);
    }


    public static boolean checkNetworkStatus(Context context) {
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        return activeNetwork != null && activeNetwork.isConnected();
    }




}
