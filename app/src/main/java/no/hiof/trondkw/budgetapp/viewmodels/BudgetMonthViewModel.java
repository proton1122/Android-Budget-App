package no.hiof.trondkw.budgetapp.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import no.hiof.trondkw.budgetapp.models.BudgetMonth;
import no.hiof.trondkw.budgetapp.models.Expense;
import no.hiof.trondkw.budgetapp.repositories.BudgetMonthRepository;

public class BudgetMonthViewModel extends ViewModel {

    private BudgetMonthRepository repository;
    private BudgetMonth currentMonth;

    private MutableLiveData<ArrayList<Expense>> expenseList;
    private MutableLiveData<Double> budget;
    private MutableLiveData<Double> expenses;

    public BudgetMonthViewModel() {
        repository = new BudgetMonthRepository();
        currentMonth = repository.getCurrentMonth();

        expenseList = new MutableLiveData<>();
        expenseList.setValue(currentMonth.getMonthlyExpenses());

        budget = new MutableLiveData<>();
        budget.setValue(currentMonth.getBudget());

        expenses = new MutableLiveData<>();
        expenses.setValue(calculateExpenses());



    }




    public LiveData<ArrayList<Expense>> getExpenseList() {
        return expenseList;
    }

    public LiveData<Double> getBudget() {
        return budget;
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
