package no.hiof.trondkw.budgetapp.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;

import no.hiof.trondkw.budgetapp.models.BudgetMonth;
import no.hiof.trondkw.budgetapp.models.Expense;
import no.hiof.trondkw.budgetapp.repositories.BudgetMonthRepository;

public class BudgetMonthViewModel extends ViewModel {

    private BudgetMonthRepository repository;
    private BudgetMonth currentMonth;
    //private MutableLiveData<BudgetMonth> currentMonth;

    private MutableLiveData<ArrayList<Expense>> expenseList;
    private MutableLiveData<Double> budget;
    private MutableLiveData<Double> totalExpenses;

    public BudgetMonthViewModel() {
        int year = LocalDate.now().getYear();
        int month = LocalDate.now().getMonth().getValue();
        //int monthId = generateMonthId();

        repository = new BudgetMonthRepository();
        currentMonth = repository.getMonth(year, month);
        //currentMonth.setValue(repository.getMonth(year, month));

        expenseList = new MutableLiveData<>();
        expenseList.setValue(currentMonth.getMonthlyExpenses());

        budget = new MutableLiveData<>();
        budget.setValue(currentMonth.getBudget());

        totalExpenses = new MutableLiveData<>();
        totalExpenses.setValue(calculateExpenses());

    }


    // Get the viewModels expenseList
    public LiveData<ArrayList<Expense>> getExpenseList() {
        return expenseList;
    }


    // Get an expense from the viewModels expenseList
    public Expense getExpense(String id) {

        ArrayList<Expense> list = expenseList.getValue();

        if (list != null) {
            for (Expense expense: list) {

                if (expense.getId().equalsIgnoreCase(id)) {
                    return expense;
                }

            }
        }

        return null;
    }


    // Add new expense to the viewModels expenseList
    public void addNewExpense(Expense expense) {
        expenseList.getValue().add(expense);
        totalExpenses.setValue(calculateExpenses());
    }

    // Remove one expense from the viewModels expenseList
    public void removeExpense(Expense expense) {

        ArrayList<Expense> list = expenseList.getValue();

        if (list.contains(expense)) {
            list.remove(expense);
            expenseList.setValue(list);
        }
        else {
            System.out.println("Could not remove expense..");
        }
    }



    // Get the viewModels budget
    public LiveData<Double> getBudget() {
        return budget;
    }

    // Set the viewModels budget
    public void setBudget(String b) {
        budget.setValue(Double.parseDouble(b));
    }

    // Get the viewModels total expenses
    public LiveData<Double> getTotalExpenses() {
        return totalExpenses;
    }

    // Get current month year / month value
    public int getCurrentYear() {
        return currentMonth.getYear();
    }
    public int getCurrentMonth() {
        return currentMonth.getMonth();
    }

    // Set current month, get from Repository
    public void setCurrentMonth(int year, int month) {

        System.out.println("VIEWMODEL.SETCURRENTMONTH BEFORE: " + currentMonth);
        System.out.println(currentMonth.getYear() + " / " + currentMonth.getMonth() );
        currentMonth = repository.getMonth(year, month);
        System.out.println("VIEWMODEL.SETCURRENTMONTH AFTER: " + currentMonth);
        System.out.println(currentMonth.getYear() + " / " + currentMonth.getMonth() );


    }


    public String getDateString() {
        int year = currentMonth.getYear();
        int month = currentMonth.getMonth();

        System.out.println("year: " + year);
        System.out.println("month: " + month);

        String yearString = String.valueOf(year);
        String monthString = Month.of(month).toString();

        return yearString + " - " + monthString;
    }


    // methods for changing the values...

    // set
    // update
    // delete



    // load from Repository




    // private methods
    private double calculateExpenses() {
        double totalExpenses = 0;

        if (expenseList.getValue() == null)
            return totalExpenses;

        for (Expense expense : expenseList.getValue())
            totalExpenses += expense.getSum();

        return totalExpenses;
    }



} // end BudgetMonthViewModel class
