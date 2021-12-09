package no.hiof.trondkw.budgetapp.repositories;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
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

        // check local storage
        if(LocalDatabase.contains(monthId)) {
            return LocalDatabase.getMonth(monthId);
        }

        // check firebase storage


        // if doesn't exist, create new empty BudgetMonth
        return new BudgetMonth(year, month);
    }


    public void saveMonth(BudgetMonth month) {

        // save to local storage first
        LocalDatabase.addMonth(String.valueOf(month.getId()), month);


        // save to firebase

        testSaveToDatabase(month);
    }

    // Test saving data to database
    private void testSaveToDatabase(BudgetMonth month) {
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String monthId = String.valueOf(month.getId());

        DatabaseReference allMonthsRef = database.getReference("users").child(uid).child("months");
        DatabaseReference oneMonthRef = allMonthsRef.child(monthId);

        oneMonthRef.setValue(month);
    }

    // Test getting data from database
    public void testGetAllDataFromDatabase() {

        DatabaseReference monthsRef = database.getReference("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("months");

        monthsRef.child("202112").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {

                BudgetMonth month = null;

                if (!task.isSuccessful()) {
                    Log.d("firebase", "Error getting data", task.getException());
                }

                else {
                    month = task.getResult().getValue(BudgetMonth.class);
                    printMonth(month);
                }
            }
        });
    }

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




    // other

    public BudgetMonth getTestMonth(int year, int month) {
        return createTestData(year, month);
    }

    private BudgetMonth createTestData(int year, int month) {
        double budget = 100000;
        ArrayList<Expense> list = new ArrayList<>(getTestExpenseList());
        return new BudgetMonth(year, month, budget, list);
    }

    // Generate dummy data
    public static List<Expense> getTestExpenseList() {
        ArrayList<Category> defaultCategories = Category.getDefaultCategoriesArray();
        List<Expense> list = new ArrayList<>();
        Category category = defaultCategories.get(4);

        for (int i = 0; i < 5; i++) {
            String title = "Expense " + i;
            //int sum = 1000 + i;
            int sum = 1000;

            LocalDate date = LocalDate.now();

            if(i<5) {
                category = defaultCategories.get(i);
            }
            // TODO: fix category
            /*
            if (i%2 == 0) {
                category = defaultCategories.get(i);
            }else {
                category = new Category("cat2");
            }
            */

            list.add(new Expense(date, title, category , sum));
        }
        return list;
    }

    private int generateMonthId() {
        int year = LocalDate.now().getYear();
        int month = LocalDate.now().getMonth().getValue();
        return Integer.parseInt("" + year + month);
    }









} // end BudgetMonthRepository class
