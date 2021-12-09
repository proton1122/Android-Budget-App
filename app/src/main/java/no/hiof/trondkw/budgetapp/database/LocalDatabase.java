package no.hiof.trondkw.budgetapp.database;

import java.util.HashMap;

import no.hiof.trondkw.budgetapp.models.BudgetMonth;

public class LocalDatabase {

    // Temporary local database TODO: implement Room database or offline FirebaseDatabase

    private static final HashMap<String, BudgetMonth> database = new HashMap<>();

    public static boolean contains(String monthId) {
        return database.containsKey(monthId);
    }

    public static BudgetMonth getMonth(String monthId) {
        return database.get(monthId);
    }

    public static void addMonth(String monthId, BudgetMonth month) {
        database.put(monthId, month);
    }

    public static void printHashMap() {
        database.forEach((key, value) -> {
            System.out.println(key + " " + value);
            System.out.println("\tID: " + value.getId() + " - BUDGET: " + value.getBudget() + " - List length: " + value.getMonthlyExpenses().size());
        });
    }

} // end LocalDatabase class



/*
private static final HashMap<Integer, BudgetMonth> database = new HashMap<>();

    public static boolean contains(int monthId) {
        return database.containsKey(monthId);
    }

    public static BudgetMonth getMonth(int monthId) {
        return database.get(monthId);
    }

    public static void addMonth(int monthId, BudgetMonth month) {
        database.put(monthId, month);
    }
*/