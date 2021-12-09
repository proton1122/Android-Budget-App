package no.hiof.trondkw.budgetapp.models;

import java.time.LocalDate;
import java.util.UUID;

public class Expense {

    public static final String ID = "id";
    public static final String TITLE = "title";
    public static final String CATEGORY = "category";
    public static final String DATE = "date";
    public static final String SUM = "sum";

    /**
     *      The Expense class is designed to hold the data
     *      for a single user expense.
     *
     */

    private String id;
    private String title;
    private Category category;
    private String date;
    private double sum;

    public Expense() {}

    public Expense(String title, double sum) {
        this.title = title;
        this.sum = sum;
    }

    public Expense(LocalDate date, String title, double sum) {
        this.id = UUID.randomUUID().toString();
        this.date = date.toString();
        this.title = title;
        this.sum = sum;
    }

    public Expense(LocalDate date, String title, Category category, double sum) {
        this.id = UUID.randomUUID().toString();
        this.date = date.toString();
        this.title = title;
        this.category = category;
        this.sum = sum;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

    public Category getCategory() {
        return category;
    }
    public void setCategory(Category category) {
        this.category = category;
    }

    public double getSum() {
        return sum;
    }
    public void setSum(double sum) {
        this.sum = sum;
    }

} // end Expense class
