package no.hiof.trondkw.budgetapp.ui.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.Spinner;

import no.hiof.trondkw.budgetapp.R;
import no.hiof.trondkw.budgetapp.interfaces.IBudgetDialogListener;
import no.hiof.trondkw.budgetapp.interfaces.IMonthYearPickerDialogListener;

public class MonthYearPickerDialog extends DialogFragment {

    private IMonthYearPickerDialogListener listener;

    private NumberPicker yearPicker;
    private NumberPicker monthPicker;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.month_year_picker_dialog, null);

        String[] months = new String[]{"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

        yearPicker = view.findViewById(R.id.year_number_picker);
        yearPicker.setMinValue(2010);
        yearPicker.setMaxValue(2030);
        monthPicker = view.findViewById(R.id.month_number_picker);
        monthPicker.setMinValue(1);
        monthPicker.setMaxValue(12);


        Bundle args = getArguments();

        if (args != null) {
            yearPicker.setValue(args.getInt("YEAR"));
            monthPicker.setValue(args.getInt("MONTH"));
        }

        monthPicker.setDisplayedValues(months);

        builder.setView(view)
                .setPositiveButton("Set", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        int year = yearPicker.getValue();
                        int month = monthPicker.getValue();

                        listener.loadMonth(year, month);
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
            listener = (IMonthYearPickerDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement IMonthYearPickerDialogListener.");
        }
    }


} // end MonthYearPicker class