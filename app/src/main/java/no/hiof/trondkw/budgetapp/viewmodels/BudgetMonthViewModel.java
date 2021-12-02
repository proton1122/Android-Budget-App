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

    // ----------- NEW STUFF -------------------------
    private BudgetMonth defaultMonth;
    private final MutableLiveData<BudgetMonth> currentMonth2;
    // --------------------------------------------------------

    private BudgetMonth currentMonth;
    private final MutableLiveData<Integer> id;
    private final MutableLiveData<ArrayList<Expense>> expenseList;
    private final MutableLiveData<Double> budget;
    private final MutableLiveData<Double> totalExpenses;

    public BudgetMonthViewModel() {
        int year = LocalDate.now().getYear();
        int month = LocalDate.now().getMonth().getValue();

        repository = new BudgetMonthRepository();

        // ----------- NEW STUFF -------------------------

        defaultMonth = repository.getTestMonth(year, month);

        currentMonth2 = new MutableLiveData<>();
        currentMonth2.setValue(defaultMonth);

        // ------------ OLD STUFF --------------------

        // get current month
        //currentMonth = repository.getMonth(year, month);

        // get test data
        currentMonth = repository.getTestMonth(year, month);

        id = new MutableLiveData<>();
        id.setValue(currentMonth.getId());

        expenseList = new MutableLiveData<>();
        expenseList.setValue(currentMonth.getMonthlyExpensesList());

        budget = new MutableLiveData<>();
        budget.setValue(currentMonth.getBudget());

        totalExpenses = new MutableLiveData<>();
        totalExpenses.setValue(calculateExpenses());

        // --------------------------------------------------------
    }


    // ----------- NEW STUFF -------------------------

    // Values needed in app:
    // Budget, ExpenseList, Total Expenses(?)

    // Get BudgetMonth -- Returns the LiveData for current month to be observed
    public LiveData<BudgetMonth> getCurrentBudgetMonth_2() {
        return currentMonth2;
    }

    // Get Budget -- Returns the budget for current month, but is this needed?
    public Double getBudget_2() {
        return currentMonth2.getValue().getBudget();
    }

    public void setBudget_2(String b) {
        currentMonth2.getValue().setBudget(Double.parseDouble(b));
    }

    public ArrayList<Expense> getExpenseList_2() {
        return currentMonth2.getValue().getMonthlyExpensesList();
    }

    public Double getTotalExpenses_2() {
        return currentMonth2.getValue().getTotalExpenses();
    }

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

    public void addNewExpense_2(LocalDate date, String title, String category, double sum) {
        Expense newExpense = new Expense(date, title, category, sum);

        currentMonth2.getValue().getMonthlyExpensesList().add(newExpense);
        currentMonth2.getValue().updateTotalExpenses();
    }

    public void editExpense_2(String id, LocalDate date, String title, String category, double sum) {
        Expense expenseToEdit = getExpense_2(id);

        if (expenseToEdit != null) {
            expenseToEdit.setDate(date);
            expenseToEdit.setTitle(title);
            expenseToEdit.setCategory(category);
            expenseToEdit.setSum(sum);

            currentMonth2.getValue().updateTotalExpenses();
        } else {
            // throw error?
            System.out.println("Could not update expense, expense not found");
        }
    }

    public void deleteExpense_2(Expense expense) {
        ArrayList<Expense> expenseList = currentMonth2.getValue().getMonthlyExpensesList();

        if(expenseList.contains(expense)) {
            expenseList.remove(expense);
        } else {
            // throw error?
            System.out.println("Could not delete expense, expense not found");
        }

        currentMonth2.getValue().updateTotalExpenses();
    }

    public int getBudgetMonthYearValue() {
        return currentMonth2.getValue().getYear();
    }
    public int getBudgetMonthMonthValue() {
        return currentMonth2.getValue().getMonth();
    }


    // --------------------------------------------------------

    /*
    // Get ID
    public LiveData<Integer> getCurrentMonthId() {
        return id;
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
    public void addNewExpense(LocalDate date, String title, String category, double sum) {
        Expense newExpense = new Expense(date, title, category, sum);

        expenseList.getValue().add(newExpense);
        totalExpenses.setValue(calculateExpenses());
    }

    // Edit an existing expense and update total expenses
    public void editExpense(String id, LocalDate date, String title, String category, double sum) {
        Expense expenseToEdit = getExpense(id);

        if (expenseToEdit != null) {

            expenseToEdit.setDate(date);
            expenseToEdit.setTitle(title);
            expenseToEdit.setCategory(category);
            expenseToEdit.setSum(sum);

            totalExpenses.setValue(calculateExpenses());
        }
    }


    // Remove one expense from the viewModels expenseList
    public void deleteExpense(Expense expense) {

        if (expenseList.getValue().contains(expense)) {
            expenseList.getValue().remove(expense);
        }

        totalExpenses.setValue(calculateExpenses());
    }




    // Get the viewModels budget
    public LiveData<Double> getBudget() {
        return budget;
    }

    // Set the viewModels budget
    public void setBudget(String b) {
        budget.setValue(Double.parseDouble(b));
        currentMonth.setBudget(Double.parseDouble(b));
    }


    // Get the viewModels total expenses
    public LiveData<Double> getTotalExpenses() {
        return totalExpenses;
    }



    // Get current month year / month value - Used to set the year / month text in the YearMonth TextInput
    public int getCurrentYear() {
        return currentMonth.getYear();
    }
    public int getCurrentMonth() {
        return currentMonth.getMonth();
    }
    */


    // Set current month, get from Repository
    public void setBudgetMonth(int year, int month) {

        // save current month to database
        repository.saveMonth(currentMonth2.getValue());

        // get new month from database
        currentMonth2.setValue(repository.getMonth(year, month));

        // update viewModel
        currentMonth2.getValue().updateTotalExpenses();


        // ------------ OLD STUFF --------------------
        /*
        repository.saveMonth(currentMonth);
        currentMonth = repository.getMonth(year, month);
        expenseList.setValue(currentMonth.getMonthlyExpensesList());
        budget.setValue(currentMonth.getBudget());
        System.out.println("Setting new BudgetMonth in ViewModel...");
        double temp = calculateExpenses();
        System.out.println("calculateExpenses() results: " + temp);
        totalExpenses.setValue(calculateExpenses());
        System.out.println("totalExpenses has been set..");
        System.out.println("totalExpenses = " + totalExpenses.getValue());
        System.out.println("----------------------------------------------------");
        */
        // --------------------------------------------------------

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
