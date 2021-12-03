package no.hiof.trondkw.budgetapp.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import no.hiof.trondkw.budgetapp.R;
import no.hiof.trondkw.budgetapp.interfaces.IOnItemClickListener;
import no.hiof.trondkw.budgetapp.models.Expense;
import no.hiof.trondkw.budgetapp.utils.Utilities;

public class ExpenseRecyclerAdapter extends RecyclerView.Adapter<ExpenseRecyclerAdapter.ExpenseViewHolder> {

    private List<Expense> expenseList;
    private LayoutInflater inflater;
    private IOnItemClickListener listener;


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
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.expense_list_item_2, parent, false);

        // old from school example
        //View itemView = inflater.inflate(R.layout.expense_list_item, parent, false);

        return new ExpenseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseViewHolder holder, int position) {
        Expense expenseToDisplay = expenseList.get(position);

        // TODO: fix category
        // from youtube example
        holder.expenseTitleTextView.setText(expenseToDisplay.getTitle());
        holder.expenseSumTextView.setText(Utilities.getFormattedSum(expenseToDisplay.getSum()));
        holder.expenseDateTextView.setText(Utilities.getFormattedDate(expenseToDisplay.getDate()));
        holder.expenseCategoryTextView.setText(expenseToDisplay.getCategory().getTitle());

        int categoryColor = expenseToDisplay.getCategory().getColor();
        holder.expenseCategoryColorBlobImageView.setColorFilter(categoryColor);
        //holder.expenseCategoryColorBlobImageView.setColorFilter();


        // old from school example
        //holder.setExpense(expenseToDisplay);
    }

    @Override
    public int getItemCount() {
        return expenseList.size();
    }

    public Expense getExpenseAt(int position) {
        return expenseList.get(position);
    }

    public void setExpenses(List<Expense> expenses) {
        this.expenseList = expenses;

        // will be changed later
        notifyDataSetChanged();
    }


    public void setOnItemClickListener(IOnItemClickListener listener) {
        this.listener = listener;
    }



    // Inner class for Adapter ---------------------------------------------------------------------
    public class ExpenseViewHolder extends RecyclerView.ViewHolder {

        private final TextView expenseTitleTextView;
        private final TextView expenseSumTextView;
        private final TextView expenseDateTextView;
        private final TextView expenseCategoryTextView;
        private final ImageView expenseCategoryColorBlobImageView;

        public ExpenseViewHolder(@NonNull View itemView) {
            super(itemView);

            expenseTitleTextView = itemView.findViewById(R.id.ExpenseCardView_Title);
            expenseSumTextView = itemView.findViewById(R.id.ExpenseCardView_Sum);
            expenseDateTextView = itemView.findViewById(R.id.ExpenseCardView_Date);
            expenseCategoryTextView = itemView.findViewById(R.id.ExpenseCardView_Category);
            expenseCategoryColorBlobImageView = itemView.findViewById(R.id.category_color_image);

            // handle onclick for each card view
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();

                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(expenseList.get(position));
                    }
                }
            });
        }

    } // end ExpenseViewHolder class ---------------------------------------------------------------

} // end ExpenseRecyclerAdapter class
