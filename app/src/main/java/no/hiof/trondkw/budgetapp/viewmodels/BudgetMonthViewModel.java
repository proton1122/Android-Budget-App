package no.hiof.trondkw.budgetapp.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;

import no.hiof.trondkw.budgetapp.models.Expense;

public class BudgetMonthViewModel {


    private MutableLiveData<ArrayList<Expense>> mExpenseList;
    private MutableLiveData<Integer> mBudget;

    public BudgetMonthViewModel() {
        mExpenseList = new MutableLiveData<>();
        //mExpenseList.setValue();

        mBudget = new MutableLiveData<>();
        //mBudget.setValue();
    }




    public LiveData<ArrayList<Expense>> getExpenseList() {
        return mExpenseList;
    }

    public LiveData<Integer> getBudget() {
        return mBudget;
    }



    // methods for changing the values...

    // set
    // update
    // delete






}
