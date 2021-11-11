package no.hiof.trondkw.budgetapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;

import java.util.List;

import no.hiof.trondkw.budgetapp.models.Expense;

public class ExpenseRecyclerAdapter {

    private List<Expense> expenseList;
    private LayoutInflater inflater;


    public ExpenseRecyclerAdapter() {

    }

    public ExpenseRecyclerAdapter(Context context, List<Expense> expenseList) {
        inflater = LayoutInflater.from(context);
        this.expenseList = expenseList;
    }




}
