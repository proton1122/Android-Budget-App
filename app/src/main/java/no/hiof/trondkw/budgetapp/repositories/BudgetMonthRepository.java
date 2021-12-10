package no.hiof.trondkw.budgetapp.repositories;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import no.hiof.trondkw.budgetapp.database.LocalDatabase;
import no.hiof.trondkw.budgetapp.models.BudgetMonth;
import no.hiof.trondkw.budgetapp.models.Category;
import no.hiof.trondkw.budgetapp.models.Expense;

public class BudgetMonthRepository {

    private final FirebaseDatabase database;
    private static BudgetMonthRepository instance;

    private BudgetMonth test;

    private BudgetMonthRepository() {
        database = FirebaseDatabase.getInstance("https://eksamen-budgetapp-default-rtdb.europe-west1.firebasedatabase.app");
    }

    public static BudgetMonthRepository getInstance() {
        if(instance == null) {
            instance = new BudgetMonthRepository();
        }
        return instance;
    }


    public BudgetMonth getMonth(int year, int month) {
        //int monthId = Integer.parseInt("" + year + month);
        String monthId = year + String.valueOf(month);

        System.out.println("Repository.getMonth(): ");

        // check local storage
        if(LocalDatabase.contains(monthId)) {
            System.out.println("Found month in local database");
            return LocalDatabase.getMonth(monthId);
        } else {
            System.out.println("Could not find month in local database");
        }

        // check firebase storage
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference reference = database.getReference("users").child(uid).child("months");

        reference.child(monthId).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                System.out.println("database task is successful...");
                test = task.getResult().getValue(BudgetMonth.class);
            } else {
                System.out.println("Could get month from DB");
            }
        });

        // getMonthFromDatabase() with listener
        /*
        getMonthFromDatabase(monthId, new OnGetDataListener() {
            @Override
            public void onSuccess(BudgetMonth budgetMonth) {
                System.out.println("onSuccess finished");
                test = budgetMonth;
            }

            @Override
            public void onFailure(Exception exception) {

                test = new BudgetMonth(year, month);
            }
        });
        */

        if(test != null) {
            System.out.println("Could get month from DB");
            System.out.println("BudgetMonth from DB: " + test);
            return test;
        }
        else {
            System.out.println("Could not get month from DB");
            System.out.println("Creating new month");
            return new BudgetMonth(year, month);
        }
    } // end getMonth()


    public void saveMonth(BudgetMonth month) {

        // save to local storage first
        LocalDatabase.addMonth(String.valueOf(month.getId()), month);

        // save to firebase
        testSaveToDatabase(month);
    }


    private void getMonthFromDatabase(String monthId, OnGetDataListener listener) {

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference reference = database.getReference("users").child(uid).child("months");

        reference.child(monthId).get().addOnCompleteListener(task -> {
            if(task.isSuccessful()) {
                listener.onSuccess(task.getResult().getValue(BudgetMonth.class));
            }
            else {
                listener.onFailure(task.getException());
            }
        });
    }


    // ---------------------------------------------------------------------------------------
    // Test stuff

    // Test saving data to database
    private void testSaveToDatabase(BudgetMonth month) {

        System.out.println("testSaveToDatabase()");
        System.out.println(month);
        System.out.println(month.getMonthlyExpenses());
        System.out.println("_-----------------------------_");


        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String monthId = String.valueOf(month.getId());

        DatabaseReference allMonthsRef = database.getReference("users").child(uid).child("months");
        DatabaseReference oneMonthRef = allMonthsRef.child(monthId);

        oneMonthRef.setValue(month);
    }

    // Test getting data from database
    public void testGetAllDataFromDatabase(OnGetDataListener repositoryChange) {

        DatabaseReference monthsRef = database.getReference("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("months");

        monthsRef.child("202112").get().addOnCompleteListener(task -> {

            // TODO: test
            //repositoryChange.onChanged(true);

            BudgetMonth month;

            if (!task.isSuccessful()) {
                Log.d("firebase", "Error getting data", task.getException());
            }
            else {
                month = task.getResult().getValue(BudgetMonth.class);
                printMonth(month);
            }
        });
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
