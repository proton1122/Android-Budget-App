package no.hiof.trondkw.budgetapp.interfaces;

import no.hiof.trondkw.budgetapp.models.Expense;

/**
 *  Interface for implementing ClickListener for each item in a RecyclerView
 */

public interface IOnItemClickListener {
    void onItemClick(Expense expense);
}
