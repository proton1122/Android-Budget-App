package no.hiof.trondkw.budgetapp.interfaces;

import no.hiof.trondkw.budgetapp.models.BudgetMonth;

public interface OnGetDataListener {

    void onSuccess(BudgetMonth budgetMonth);

    void onFailure(Exception exception);

}
