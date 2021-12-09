package no.hiof.trondkw.budgetapp.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;

import no.hiof.trondkw.budgetapp.models.BudgetMonth;
import no.hiof.trondkw.budgetapp.models.Category;
import no.hiof.trondkw.budgetapp.models.Expense;
import no.hiof.trondkw.budgetapp.repositories.BudgetMonthRepository;

public class BudgetMonthViewModel extends ViewModel {


    private final BudgetMonthRepository repository;
    private final MutableLiveData<BudgetMonth> currentMonth;


    public BudgetMonthViewModel() {
        repository = BudgetMonthRepository.getInstance();

        // Set the default displaying month to current month
        int year = LocalDate.now().getYear();
        int month = LocalDate.now().getMonth().getValue();

        BudgetMonth defaultMonth = getDefaultMonth(year, month);
        currentMonth = new MutableLiveData<>();
        currentMonth.setValue(defaultMonth);

    } // end BudgetMonthViewModel constructor


    // Get BudgetMonth -- Returns the LiveData for current month to be observed
    public LiveData<BudgetMonth> getCurrentBudgetMonth() {
        return currentMonth;
    }

    // Set new current month based on month/year picker input
    public void setBudgetMonth(int year, int month) {

        // save current month to database
        repository.saveMonth(currentMonth.getValue());

        // get new month from database
        currentMonth.setValue(repository.getMonth(year, month));

        // update viewModel
        currentMonth.getValue().updateTotalExpenses();
    }

    // Get Budget -- Returns the budget for current month
    public Double getBudget() {
        return currentMonth.getValue().getBudget();
    }

    // Set the budget for current month
    public void setBudget(String b) {
        BudgetMonth current = currentMonth.getValue();
        current.setBudget(Double.parseDouble(b));
        currentMonth.setValue(current);
    }

    // Get the list of expenses from current month
    public ArrayList<Expense> getExpenseList() {
        return currentMonth.getValue().getMonthlyExpenses();
    }

    // Get current month total expenses
    public Double getTotalExpenses() {
        return currentMonth.getValue().getTotalExpenses();
    }

    // Get a specific Expense, used for the recycleView
    public Expense getExpense(String id) {
        ArrayList<Expense> list = currentMonth.getValue().getMonthlyExpenses();

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

    // Add new expense to current month expenseList
    public void addNewExpense(LocalDate date, String title, Category category, double sum) {
        BudgetMonth current = currentMonth.getValue();

        Expense newExpense = new Expense(date, title, category, sum);

        current.getMonthlyExpenses().add(newExpense);
        current.updateTotalExpenses();

        currentMonth.setValue(current);

    }

    // Edit an existing expense and update total expenses
    public void editExpense(String id, LocalDate date, String title, Category category, double sum) {
        BudgetMonth current = currentMonth.getValue();

        Expense expenseToEdit = getExpense(id);

        if (expenseToEdit != null) {
            expenseToEdit.setDate(date.toString());
            expenseToEdit.setTitle(title);
            expenseToEdit.setCategory(category);
            expenseToEdit.setSum(sum);

            current.updateTotalExpenses();

            currentMonth.setValue(current);

        } else {
            // throw error?
            System.out.println("Could not update expense, expense not found");
        }
    }

    // Remove one expense from the viewModels expenseList
    public void deleteExpense(Expense expense) {
        BudgetMonth current = currentMonth.getValue();

        //ArrayList<Expense> expenseList = currentMonth2.getValue().getMonthlyExpensesList();
        ArrayList<Expense> expenseList = current.getMonthlyExpenses();

        if(expenseList.contains(expense)) {
            expenseList.remove(expense);
        } else {
            // throw error?
            System.out.println("Could not delete expense, expense not found");
        }

        current.updateTotalExpenses();
        currentMonth.setValue(current);

    }

    // Get current month year / month value - Used to set the year / month text in the YearMonth TextInput
    public int getBudgetMonthYearValue() {
        return currentMonth.getValue().getYear();
    }
    public int getBudgetMonthMonthValue() {
        return currentMonth.getValue().getMonth();
    }


    public String getDateString() {
        int year = currentMonth.getValue().getYear();
        int month = currentMonth.getValue().getMonth();

        String yearString = String.valueOf(year);
        String monthString = Month.of(month).toString();

        return yearString + " - " + monthString;
    }


    // TEST DATABASE STUFF
    public void testSaveToDatabase() {
        repository.saveMonth(currentMonth.getValue());
    }
    public void testGetData() { repository.testGetAllDataFromDatabase();}



    private BudgetMonth getDefaultMonth(int year, int month) {

        return repository.getTestMonth(year, month);
    }


} // end BudgetMonthViewModel class
