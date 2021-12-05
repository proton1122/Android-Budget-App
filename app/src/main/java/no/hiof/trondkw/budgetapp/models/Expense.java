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

    // TODO: test this
    //private LocalDate date;
    private String date;


    private double sum;


    public Expense() {}

    public Expense(String title, double sum) {
        this.title = title;
        this.sum = sum;
    }

    public Expense(LocalDate date, String title, double sum) {
        this.id = UUID.randomUUID().toString();

        // TODO: test this
        //this.date = date;
        this.date = date.toString();

        this.title = title;
        this.sum = sum;
    }

    public Expense(LocalDate date, String title, Category category, double sum) {
        this.id = UUID.randomUUID().toString();

        // TODO: test this
        //this.date = date;
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

    // TODO: test this
    public String getDate() {

        return date;
    }
    // TODO: test this
    public void setDate(LocalDate date) {
        this.date = date.toString();
    }

    public void setDate(String date) {
        LocalDate localdate = LocalDate.parse(date);
        setDate(localdate);
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
