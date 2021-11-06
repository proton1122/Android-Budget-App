package no.hiof.trondkw.budgetapp.repositories;

import java.time.LocalDate;

import no.hiof.trondkw.budgetapp.database.LocalDatabase;
import no.hiof.trondkw.budgetapp.models.BudgetMonth;

public class BudgetMonthRepository {


    public BudgetMonthRepository() {

        // initiate data?
        // singleton?

    }





    public BudgetMonth getCurrentMonth(int year, int month) {

        int monthId = Integer.parseInt("" + year + month);

        // check local storage
        if(LocalDatabase.contains(monthId))
            return LocalDatabase.getMonth(monthId);


        // check firebase storage



        // if doesn't exist, create new

        return new BudgetMonth(year, month);
    }

    // FireBase data
        // get, send etc





    // Local storage data




    // other
    private int generateMonthId() {
        int year = LocalDate.now().getYear();
        int month = LocalDate.now().getMonth().getValue();

        return Integer.parseInt("" + year + month);
    }


}
