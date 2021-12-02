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

    private final MutableLiveData<BudgetMonth> currentMonth2;
    // --------------------------------------------------------

    //private BudgetMonth currentMonth;
    //private final MutableLiveData<Integer> id;
    //private final MutableLiveData<ArrayList<Expense>> expenseList;
    //private final MutableLiveData<Double> budget;
    //private final MutableLiveData<Double> totalExpenses;

    public BudgetMonthViewModel() {
        int year = LocalDate.now().getYear();
        int month = LocalDate.now().getMonth().getValue();

        repository = new BudgetMonthRepository();

        // ----------- NEW STUFF -------------------------

        // ----------- NEW STUFF -------------------------
        BudgetMonth defaultMonth = repository.getTestMonth(year, month);

        currentMonth2 = new MutableLiveData<>();
        currentMonth2.setValue(defaultMonth);

        // ------------ OLD STUFF --------------------

        // get current month
        //currentMonth = repository.getMonth(year, month);

        // get test data
        /*
        currentMonth = repository.getTestMonth(year, month);

        id = new MutableLiveData<>();
        id.setValue(currentMonth.getId());

        expenseList = new MutableLiveData<>();
        expenseList.setValue(currentMonth.getMonthlyExpensesList());

        budget = new MutableLiveData<>();
        budget.setValue(currentMonth.getBudget());

        totalExpenses = new MutableLiveData<>();
        totalExpenses.setValue(calculateExpenses());
        */
        // --------------------------------------------------------
    }



    // Get BudgetMonth -- Returns the LiveData for current month to be observed
    public LiveData<BudgetMonth> getCurrentBudgetMonth_2() {
        return currentMonth2;
    }

    // Get Budget -- Returns the budget for current month
    public Double getBudget_2() {
        return currentMonth2.getValue().getBudget();
    }

    // Set the viewModels budget
    public void setBudget_2(String b) {
        BudgetMonth test = currentMonth2.getValue();
        test.setBudget(Double.parseDouble(b));
        currentMonth2.setValue(test);

        //currentMonth2.getValue().setBudget(Double.parseDouble(b));
    }

    public ArrayList<Expense> getExpenseList_2() {
        return currentMonth2.getValue().getMonthlyExpensesList();
    }

    // Get the viewModels total expenses
    public Double getTotalExpenses_2() {
        return currentMonth2.getValue().getTotalExpenses();
    }

    // Get a specific Expense, used for the recycleView
    public Expense getExpense_2(String id) {
        ArrayList<Expense> list = currentMonth2.getValue().getMonthlyExpensesList();

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
    public void addNewExpense_2(LocalDate date, String title, String category, double sum) {
        BudgetMonth test = currentMonth2.getValue();

        Expense newExpense = new Expense(date, title, category, sum);

        test.getMonthlyExpensesList().add(newExpense);
        test.updateTotalExpenses();
        currentMonth2.setValue(test);

        //currentMonth2.getValue().getMonthlyExpensesList().add(newExpense);
        //currentMonth2.getValue().updateTotalExpenses();
    }

    // Edit an existing expense and update total expenses
    public void editExpense_2(String id, LocalDate date, String title, String category, double sum) {
        BudgetMonth test = currentMonth2.getValue();

        Expense expenseToEdit = getExpense_2(id);

        if (expenseToEdit != null) {
            expenseToEdit.setDate(date);
            expenseToEdit.setTitle(title);
            expenseToEdit.setCategory(category);
            expenseToEdit.setSum(sum);

            test.updateTotalExpenses();

            currentMonth2.setValue(test);

            //currentMonth2.getValue().updateTotalExpenses();
        } else {
            // throw error?
            System.out.println("Could not update expense, expense not found");
        }
    }

    // Remove one expense from the viewModels expenseList
    public void deleteExpense_2(Expense expense) {
        BudgetMonth test = currentMonth2.getValue();

        //ArrayList<Expense> expenseList = currentMonth2.getValue().getMonthlyExpensesList();
        ArrayList<Expense> expenseList = test.getMonthlyExpensesList();

        if(expenseList.contains(expense)) {
            expenseList.remove(expense);
        } else {
            // throw error?
            System.out.println("Could not delete expense, expense not found");
        }

        test.updateTotalExpenses();
        currentMonth2.setValue(test);

        //currentMonth2.getValue().updateTotalExpenses();
    }

    // Get current month year / month value - Used to set the year / month text in the YearMonth TextInput
    public int getBudgetMonthYearValue() {
        return currentMonth2.getValue().getYear();
    }
    public int getBudgetMonthMonthValue() {
        return currentMonth2.getValue().getMonth();
    }


    // Set new current month
    public void setBudgetMonth(int year, int month) {

        // save current month to database
        repository.saveMonth(currentMonth2.getValue());

        // get new month from database
        currentMonth2.setValue(repository.getMonth(year, month));

        // update viewModel
        currentMonth2.getValue().updateTotalExpenses();
    }


    public String getDateString() {
        // TODO: Remove old code
        //int year = currentMonth.getYear();
        //int month = currentMonth.getMonth();

        int year = currentMonth2.getValue().getYear();
        int month = currentMonth2.getValue().getMonth();

        String yearString = String.valueOf(year);
        String monthString = Month.of(month).toString();

        return yearString + " - " + monthString;
    }

} // end BudgetMonthViewModel class
