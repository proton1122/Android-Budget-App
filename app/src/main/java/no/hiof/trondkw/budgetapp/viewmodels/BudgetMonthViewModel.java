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

    private MutableLiveData<ArrayList<Expense>> expenseList;
    private MutableLiveData<Double> budget;
    private MutableLiveData<Double> totalExpenses;

    public BudgetMonthViewModel() {
        int year = LocalDate.now().getYear();
        int month = LocalDate.now().getMonth().getValue();
        //int monthId = generateMonthId();

        repository = new BudgetMonthRepository();
        currentMonth = repository.getCurrentMonth(year, month);

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
        totalExpenses.setValue(calculateExpenses());

    }


    public void setBudget(String b) {
        budget.setValue(Double.parseDouble(b));
    }

    public LiveData<Double> getBudget() {
        return budget;
    }

    public LiveData<Double> getTotalExpenses() {
        return totalExpenses;
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
