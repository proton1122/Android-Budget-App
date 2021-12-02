package no.hiof.trondkw.budgetapp;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;


import no.hiof.trondkw.budgetapp.models.BudgetMonth;
import no.hiof.trondkw.budgetapp.models.Expense;

public class BudgetMonthUnitTest {

    @Test
    public void budgetMonth_calculateExpenses_returnsTotalExpenses() {

        //List<Expense> expenseList = new ArrayList<>();
        Expense expense1 = new Expense("Test Expense 1", 500);
        Expense expense2 = new Expense("Test Expense 2", 500);
        Expense expense3 = new Expense("Test Expense 3", 500);
        ArrayList<Expense> expenseList = new ArrayList<>(Arrays.asList(expense1, expense2, expense3));

        BudgetMonth budgetMonth = new BudgetMonth(2021, 12, 20000, expenseList);


        double expectedTotalExpenses = 1500;
        double actualTotalExpenses = budgetMonth.getTotalExpenses();

        assertEquals(expectedTotalExpenses, actualTotalExpenses, 0.0001);
    }
}
