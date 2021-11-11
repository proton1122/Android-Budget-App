package no.hiof.trondkw.budgetapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import no.hiof.trondkw.budgetapp.R;
import no.hiof.trondkw.budgetapp.models.Expense;

public class ExpenseRecyclerAdapter extends RecyclerView.Adapter<ExpenseRecyclerAdapter.ExpenseViewHolder> {

    private List<Expense> expenseList;
    private LayoutInflater inflater;


    public ExpenseRecyclerAdapter() {

    }

    public ExpenseRecyclerAdapter(Context context, List<Expense> expenseList) {
        inflater = LayoutInflater.from(context);
        this.expenseList = expenseList;
    }

    @NonNull
    @Override
    public ExpenseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // from youtube example
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.expense_list_item, parent, false);

        // old from school example
        //View itemView = inflater.inflate(R.layout.expense_list_item, parent, false);

        return new ExpenseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseViewHolder holder, int position) {
        Expense expenseToDisplay = expenseList.get(position);

        // from youtube example
        holder.expenseTitleTextView.setText(expenseToDisplay.getExpenseTitle());
        holder.expenseSumTextView.setText(String.valueOf(expenseToDisplay.getSum()));

        // old from school example
        //holder.setExpense(expenseToDisplay);
    }

    @Override
    public int getItemCount() {
        return expenseList.size();
    }

    public void setExpenses(List<Expense> expenses) {
        this.expenseList = expenses;

        // will be changed later
        notifyDataSetChanged();
    }







    // Inner class for Adapter ---------------------------------------------------------------------
    public static class ExpenseViewHolder extends RecyclerView.ViewHolder {

        private final TextView expenseTitleTextView;
        private final TextView expenseSumTextView;

        public ExpenseViewHolder(@NonNull View itemView) {
            super(itemView);

            expenseTitleTextView = itemView.findViewById(R.id.ExpenseCardView_Title);
            expenseSumTextView = itemView.findViewById(R.id.ExpenseCardView_Sum);
        }

        public void setExpense(Expense expenseToDisplay) {
            expenseTitleTextView.setText(expenseToDisplay.getExpenseTitle());

            String sum = expenseToDisplay.getSum() + ",-";
            expenseSumTextView.setText(sum);
        }
    } // end ExpenseViewHolder class ---------------------------------------------------------------


} // end ExpenseRecyclerAdapter class
