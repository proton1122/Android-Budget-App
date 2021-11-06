package no.hiof.trondkw.budgetapp.models;

public class Expense {

    /**
     *      The Expense class is designed to hold the data
     *      for a single user expense.
     *
     */

    private int expenseId;
    private String expenseTitle;
    private String expenseDescription;
    //private ExpenseCategory category;
    private String expenseCategory;
    //private Date date;
    private double sum;


    public Expense() {}
    public Expense(String title, double sum) {
        this.expenseTitle = title;
        this.sum = sum;
    }


    public int getExpenseId() {
        return expenseId;
    }
    public String getExpenseTitle() {
        return expenseTitle;
    }
    public void setExpenseTitle(String expenseTitle) {
        this.expenseTitle = expenseTitle;
    }
    public String getExpenseDescription() {
        return expenseDescription;
    }
    public void setExpenseDescription(String expenseDescription) {
        this.expenseDescription = expenseDescription;
    }
    public String getExpenseCategory() {
        return expenseCategory;
    }
    public void setExpenseCategory(String expenseCategory) {
        this.expenseCategory = expenseCategory;
    }
    public double getSum() {
        return sum;
    }
    public void setSum(double sum) {
        this.sum = sum;
    }



} // end Expense class
