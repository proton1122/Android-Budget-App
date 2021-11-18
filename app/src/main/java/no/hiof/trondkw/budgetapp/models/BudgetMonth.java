package no.hiof.trondkw.budgetapp.models;

import java.util.ArrayList;

public class BudgetMonth {

    /**
     *      The BudgetMonth class is designed to hold
     *      all the data needed to give the user a monthly overview
     *      for budget and expenses.
     *
     */

    private int id;
    private int year;
    private int month;
    private double budget;
    private ArrayList<Expense> monthlyExpenses;


    public BudgetMonth(int year, int month) {
        this.budget = 0;
        this.monthlyExpenses = new ArrayList<>();
        this.year = year;
        this.month = month;

        this.id = Integer.parseInt("" + year + month);
    }

    public BudgetMonth(int year, int month, double budget, ArrayList<Expense> expenses) {
        this.year = year;
        this.month = month;
        this.budget = budget;
        this.monthlyExpenses = expenses;

        this.id = Integer.parseInt("" + year + month);
    }


    public int getId() {
        return id;
    }

    public int getYear() {
        return year;
    }
    public void setYear(int year) {
        this.year = year;
    }
    public int getMonth() {
        return month;
    }
    public void setMonth(int month) {
        this.month = month;
    }

    public double getBudget() {
        return budget;
    }
    public void setBudget(double budget) {
        this.budget = budget;
    }
    public ArrayList<Expense> getMonthlyExpenses() {
        return monthlyExpenses;
    }
    public void setMonthlyExpenses(ArrayList<Expense> monthlyExpenses) {
        this.monthlyExpenses = monthlyExpenses;
    }



} // end BudgetMonth class
