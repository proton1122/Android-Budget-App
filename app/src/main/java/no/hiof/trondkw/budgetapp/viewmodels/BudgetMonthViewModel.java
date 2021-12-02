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


    private final BudgetMonthRepository repository;
    private final MutableLiveData<BudgetMonth> currentMonth;


    public BudgetMonthViewModel() {
        int year = LocalDate.now().getYear();
        int month = LocalDate.now().getMonth().getValue();

        repository = new BudgetMonthRepository();

        BudgetMonth defaultMonth = repository.getTestMonth(year, month);

        currentMonth = new MutableLiveData<>();
        currentMonth.setValue(defaultMonth);
    }



    // Get BudgetMonth -- Returns the LiveData for current month to be observed
    public LiveData<BudgetMonth> getCurrentBudgetMonth() {
        return currentMonth;
    }

    // Get Budget -- Returns the budget for current month
    public Double getBudget() {
        return currentMonth.getValue().getBudget();
    }

    // Set the viewModels budget
    public void setBudget(String b) {
        BudgetMonth test = currentMonth.getValue();
        test.setBudget(Double.parseDouble(b));
        currentMonth.setValue(test);

        //currentMonth2.getValue().setBudget(Double.parseDouble(b));
    }

    public ArrayList<Expense> getExpenseList() {
        return currentMonth.getValue().getMonthlyExpensesList();
    }

    // Get the viewModels total expenses
    public Double getTotalExpenses() {
        return currentMonth.getValue().getTotalExpenses();
    }

    // Get a specific Expense, used for the recycleView
    public Expense getExpense(String id) {
        ArrayList<Expense> list = currentMonth.getValue().getMonthlyExpensesList();

        if (list != null) {
            for (Expense expense: list) {

                if (expense.getId().equalsIgnoreCase(id)) {
                    return expense;
                }
            }
        }
        // TODO: Fix this, shouldn't return null
        return null;
    }

    // Add new expense to the viewModels expenseList
    public void addNewExpense(LocalDate date, String title, String category, double sum) {
        BudgetMonth test = currentMonth.getValue();

        Expense newExpense = new Expense(date, title, category, sum);

        test.getMonthlyExpensesList().add(newExpense);
        test.updateTotalExpenses();

        currentMonth.setValue(test);

        //currentMonth2.getValue().getMonthlyExpensesList().add(newExpense);
        //currentMonth2.getValue().updateTotalExpenses();
    }

    // Edit an existing expense and update total expenses
    public void editExpense(String id, LocalDate date, String title, String category, double sum) {
        BudgetMonth test = currentMonth.getValue();

        Expense expenseToEdit = getExpense(id);

        if (expenseToEdit != null) {
            expenseToEdit.setDate(date);
            expenseToEdit.setTitle(title);
            expenseToEdit.setCategory(category);
            expenseToEdit.setSum(sum);

            test.updateTotalExpenses();

            currentMonth.setValue(test);

            //currentMonth2.getValue().updateTotalExpenses();
        } else {
            // throw error?
            System.out.println("Could not update expense, expense not found");
        }
    }

    // Remove one expense from the viewModels expenseList
    public void deleteExpense(Expense expense) {
        BudgetMonth test = currentMonth.getValue();

        //ArrayList<Expense> expenseList = currentMonth2.getValue().getMonthlyExpensesList();
        ArrayList<Expense> expenseList = test.getMonthlyExpensesList();

        if(expenseList.contains(expense)) {
            expenseList.remove(expense);
        } else {
            // throw error?
            System.out.println("Could not delete expense, expense not found");
        }

        test.updateTotalExpenses();
        currentMonth.setValue(test);

        //currentMonth2.getValue().updateTotalExpenses();
    }

    // Get current month year / month value - Used to set the year / month text in the YearMonth TextInput
    public int getBudgetMonthYearValue() {
        return currentMonth.getValue().getYear();
    }
    public int getBudgetMonthMonthValue() {
        return currentMonth.getValue().getMonth();
    }


    // Set new current month
    public void setBudgetMonth(int year, int month) {
        // save current month to database
        repository.saveMonth(currentMonth.getValue());

        // get new month from database
        currentMonth.setValue(repository.getMonth(year, month));

        // update viewModel
        currentMonth.getValue().updateTotalExpenses();
    }


    public String getDateString() {
        int year = currentMonth.getValue().getYear();
        int month = currentMonth.getValue().getMonth();

        String yearString = String.valueOf(year);
        String monthString = Month.of(month).toString();

        return yearString + " - " + monthString;
    }

} // end BudgetMonthViewModel class
