package no.hiof.trondkw.budgetapp.ui.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import no.hiof.trondkw.budgetapp.R;
import no.hiof.trondkw.budgetapp.databinding.EditBudgetBinding;
import no.hiof.trondkw.budgetapp.databinding.FragmentMonthOverviewBinding;
import no.hiof.trondkw.budgetapp.interfaces.IBudgetDialogListener;

public class BudgetDialog extends DialogFragment {

    public static final String BUDGET = "budget";

    // TODO: how to use binding with alert dialog?
    //EditBudgetBinding binding;
    private EditText budgetInput;
    private IBudgetDialogListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        // TODO: how to use binding with alert dialog?
        //binding = FragmentMonthOverviewBinding.inflate(inflater, container, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.edit_budget, null);

        budgetInput = view.findViewById(R.id.edittext_edit_budget);
        budgetInput.setText(getArguments().get(BUDGET).toString());

        builder.setView(view)
                .setPositiveButton("Set", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String budget = budgetInput.getText().toString();

                        if (budget.length() > 0)
                            listener.setNewBudget(budget);

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });

        return builder.create();
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (IBudgetDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement IBudgetDialogListener.");
        }
    }

}
