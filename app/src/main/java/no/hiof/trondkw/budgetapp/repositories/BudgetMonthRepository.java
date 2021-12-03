package no.hiof.trondkw.budgetapp.repositories;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import no.hiof.trondkw.budgetapp.database.LocalDatabase;
import no.hiof.trondkw.budgetapp.models.BudgetMonth;
import no.hiof.trondkw.budgetapp.models.Category;
import no.hiof.trondkw.budgetapp.models.Expense;

public class BudgetMonthRepository {


    public BudgetMonthRepository() {

        // TODO: make singleton
        // initiate data?
        // singleton?

    }


    public BudgetMonth getMonth(int year, int month) {

        int monthId = Integer.parseInt("" + year + month);

        // check local storage
        if(LocalDatabase.contains(monthId))
            return LocalDatabase.getMonth(monthId);


        // check firebase storage


        // if doesn't exist, create new
        return new BudgetMonth(year, month);
    }


    public void saveMonth(BudgetMonth month) {




        // save to local storage first
        LocalDatabase.addMonth(month.getId(), month);


        // save to firebase

    }




    // FireBase data
        // get, send etc





    // Local storage data




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
        ArrayList<Category> defaultCategories = Category.getDefaultCategories();
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
