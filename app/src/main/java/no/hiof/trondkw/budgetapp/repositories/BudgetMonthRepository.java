package no.hiof.trondkw.budgetapp.repositories;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import no.hiof.trondkw.budgetapp.database.LocalDatabase;
import no.hiof.trondkw.budgetapp.interfaces.OnGetDataListener;
import no.hiof.trondkw.budgetapp.models.BudgetMonth;
import no.hiof.trondkw.budgetapp.models.Category;
import no.hiof.trondkw.budgetapp.models.Expense;

public class BudgetMonthRepository {


    private final FirebaseDatabase database;
    private static BudgetMonthRepository instance;


    // Constructor
    private BudgetMonthRepository() {
        database = FirebaseDatabase.getInstance("https://eksamen-budgetapp-default-rtdb.europe-west1.firebasedatabase.app");
    }

    // Get singleton instance
    public static BudgetMonthRepository getInstance() {
        if(instance == null) {
            instance = new BudgetMonthRepository();
        }
        return instance;
    }

    // Get one month from local DB or Firebase DB
    public void getMonth(int year, int month, OnGetDataListener listener) {
        String monthId = year + String.valueOf(month);


        // Check local storage first
        if(LocalDatabase.contains(monthId)) {
            listener.onSuccess(LocalDatabase.getMonth(monthId));
            return;
        } else {
            Log.d("BudgetMonthRepository", "Could not find month in local DB");
        }

        // Check firebase storage
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference reference = database.getReference("users").child(uid).child("months");

        reference.child(monthId).get().addOnCompleteListener(task -> {

            if (task.isSuccessful()) {
                BudgetMonth dbMonth = task.getResult().getValue(BudgetMonth.class);

                // If month is null, it was not found in Firebase DB, create new empty month
                if (dbMonth == null) {
                    BudgetMonth newMonth = new BudgetMonth(year, month);
                    newMonth.setMonthlyExpenses(new ArrayList<>());
                    listener.onSuccess(newMonth);
                    return;
                }
                else {
                    // TODO: Fix issue when saving month with empty arraylist to database
                    // Saving month with empty arraylist causes it to be null when retrieving
                    try {
                        int listSize = dbMonth.getMonthlyExpenses().size();
                    } catch (NullPointerException e) {
                        dbMonth.setMonthlyExpenses(new ArrayList<>());
                    }
                }

                listener.onSuccess(dbMonth);

            } else {
                // TODO: Handle exception from Firebase
                listener.onFailure(task.getException());
            }
        });

    } // end getMonth()


    // Save month to local and remote database
    public void saveMonth(BudgetMonth month) {

        // save to local storage first
        LocalDatabase.addMonth(String.valueOf(month.getId()), month);

        // save to firebase
        saveToFirebaseDatabase(month);
    }


    // Save to firebase
    private void saveToFirebaseDatabase(BudgetMonth month) {

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String monthId = String.valueOf(month.getId());

        DatabaseReference allMonthsRef = database.getReference("users").child(uid).child("months");
        DatabaseReference oneMonthRef = allMonthsRef.child(monthId);

        oneMonthRef.setValue(month);
    }

    // ---------------------------------------------------------------------------------------
    // Generate dummy data
    public BudgetMonth getTestMonth(int year, int month) {
        return createTestData(year, month);
    }

    private BudgetMonth createTestData(int year, int month) {
        double budget = 15000;
        ArrayList<Expense> list = new ArrayList<>(getTestExpenseList());
        return new BudgetMonth(year, month, budget, list);
    }


    public static List<Expense> getTestExpenseList() {
        ArrayList<Category> defaultCategories = Category.getDefaultCategoriesArray();
        List<Expense> list = new ArrayList<>();
        Category category;

        for (int i = 0; i < 5; i++) {
            String title = "Expense " + i;
            int sum = 1000;

            LocalDate date = LocalDate.now();
            category = defaultCategories.get(i);
            list.add(new Expense(date, title, category , sum));
        }
        return list;
    }

    // ---------------------------------------------------------------------------------------

    private void printMonth(BudgetMonth month) {
        System.out.println("ID: " + month.getId());
        System.out.println("Budget: " + month.getBudget());
        System.out.println("Total expenses: " + month.getTotalExpenses());
        System.out.println("------------------------------------------------");
        ArrayList<Expense> expenses = month.getMonthlyExpenses();
        for (Expense expense: expenses) {
            System.out.println("\tExpense ID: " + expense.getId());
            System.out.println("\tExpense Title: " + expense.getTitle());
            System.out.println("\tExpense Date: " + expense.getDate());
            System.out.println("\tExpense Category: " + expense.getCategory().getTitle());
            System.out.println("\tExpense Sum: " + expense.getSum());
            System.out.println("\t-----------------------------------------");
        }
    }

} // end BudgetMonthRepository class
