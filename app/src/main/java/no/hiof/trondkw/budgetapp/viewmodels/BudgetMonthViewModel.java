package no.hiof.trondkw.budgetapp.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.time.LocalDate;
import java.util.ArrayList;

import no.hiof.trondkw.budgetapp.models.BudgetMonth;
import no.hiof.trondkw.budgetapp.models.Expense;
import no.hiof.trondkw.budgetapp.repositories.BudgetMonthRepository;

public class BudgetMonthViewModel extends ViewModel {

    private BudgetMonthRepository repository;
    private BudgetMonth currentMonth;

    private MutableLiveData<ArrayList<Expense>> expenseList;
    private MutableLiveData<Double> budget;
    private MutableLiveData<Double> totalExpenses;

    public BudgetMonthViewModel() {
        int monthId = getMonthId();

        repository = new BudgetMonthRepository();
        currentMonth = repository.getCurrentMonth(monthId);

        expenseList = new MutableLiveData<>();
        expenseList.setValue(currentMonth.getMonthlyExpenses());

        budget = new MutableLiveData<>();
        budget.setValue(currentMonth.getBudget());

        totalExpenses = new MutableLiveData<>();
        totalExpenses.setValue(calculateExpenses());

    }



    public LiveData<ArrayList<Expense>> getExpenseList() {
        return expenseList;
    }

    public void addExpense(Expense expense) {
        expenseList.getValue().add(expense);
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


    private int getMonthId() {
        int year = LocalDate.now().getYear();
        int month = LocalDate.now().getMonth().getValue();

        return Integer.parseInt("" + year + month);
    }



} // end BudgetMonthViewModel class
