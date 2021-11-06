package no.hiof.trondkw.budgetapp.repositories;

import no.hiof.trondkw.budgetapp.database.LocalDatabase;
import no.hiof.trondkw.budgetapp.models.BudgetMonth;

public class BudgetMonthRepository {


    public BudgetMonthRepository() {

        // initiate data?

    }





    public BudgetMonth getCurrentMonth(int monthId) {

        // check local storage
        if(LocalDatabase.contains(monthId))
            return LocalDatabase.getMonth(monthId);


        // check firebase storage



        // if doesn't exist, create new

        return new BudgetMonth();
    }

    // FireBase data
        // get, send etc





    // Local storage data


}
