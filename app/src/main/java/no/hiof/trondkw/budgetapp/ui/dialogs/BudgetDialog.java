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
import no.hiof.trondkw.budgetapp.interfaces.IBudgetDialogListener;

public class BudgetDialog extends DialogFragment {


    private EditText budgetInput;
    private IBudgetDialogListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity(), R.style.DialogDarkStyle);
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.edit_budget, null);

        // Set the budget in the TextInputEditText
        String budget = getArguments().get(getResources().getString(R.string.BUDGET)).toString();
        budgetInput = view.findViewById(R.id.budget_input);
        budgetInput.setText(budget);
        budgetInput.requestFocus();

        builder.setView(view)
                .setPositiveButton(getResources().getString(R.string.dialog_set), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String budget = budgetInput.getText().toString();

                        if (budget.length() > 0)
                            listener.setNewBudget(budget);

                    }
                })
                .setNegativeButton(getResources().getString(R.string.dialog_cancel), new DialogInterface.OnClickListener() {
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

} // end BudgetDialog class
