package no.hiof.trondkw.budgetapp.models;

import java.util.ArrayList;

public class BudgetMonth {

    /**
     *      The BudgetMonth class is designed to hold
     *      all the data needed to give the user a monthly overview
     *      for budget and expenses.
     *
     */

    private final int id;
    private int year;
    private int month;

    private ArrayList<Expense> monthlyExpenses;
    private double budget;
    private double totalExpenses;


    // Only for testing..
    public BudgetMonth(int year, int month) {
        this.monthlyExpenses = new ArrayList<>();
        this.budget = 0;
        this.totalExpenses = 0;

        this.year = year;
        this.month = month;

        this.id = Integer.parseInt("" + year + month);
    }

    public BudgetMonth(int year, int month, double budget, ArrayList<Expense> expenses) {
        this.monthlyExpenses = expenses;
        this.budget = budget;
        this.totalExpenses = calculateTotalExpenses();

        this.year = year;
        this.month = month;

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
    public ArrayList<Expense> getMonthlyExpensesList() {
        return monthlyExpenses;
    }
    public double getTotalExpenses() {
        return totalExpenses;
    }
    public void updateTotalExpenses() {
        this.totalExpenses = calculateTotalExpenses();
    }


    private double calculateTotalExpenses() {
        ArrayList<Expense> expenseList = this.monthlyExpenses;

        double totalExpenses = 0;

        if (expenseList.size() <= 0)
            return totalExpenses;

        for (Expense expense : expenseList)
            totalExpenses += expense.getSum();

        return totalExpenses;
    }



} // end BudgetMonth class
