package no.hiof.trondkw.budgetapp.models;

import java.util.ArrayList;

public class BudgetMonth {

    /**
     *      The BudgetMonth class is designed to hold
     *      all the data needed to give the user a monthly overview
     *      for budget and expenses.
     *
     */

    private String id;
    private double budget;
    private ArrayList<Expense> monthlyExpenses;


    public BudgetMonth() {
        this.budget = 0;
    }


    public String getId() {
        return id;
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