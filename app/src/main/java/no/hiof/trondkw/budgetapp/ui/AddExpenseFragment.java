package no.hiof.trondkw.budgetapp.ui;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import no.hiof.trondkw.budgetapp.databinding.FragmentAddExpenseBinding;
import no.hiof.trondkw.budgetapp.viewmodels.BudgetMonthViewModel;

public class AddExpenseFragment extends Fragment implements DatePickerDialog.OnDateSetListener {

    private BudgetMonthViewModel budgetMonthViewModel;
    private FragmentAddExpenseBinding binding;

    private int dayOfMonth;
    private int month;
    private int year;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        budgetMonthViewModel = new ViewModelProvider(requireActivity()).get(BudgetMonthViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAddExpenseBinding.inflate(inflater, container, false);

        // set view model in binding
        binding.setBudgetMonthViewModel(budgetMonthViewModel);

        // set onclick listener for date picker dialog
        binding.selectDateView.setOnClickListener(view -> showDatePickerDialog());


        // observer viewModel...
        // budgetMonthViewModel.get(...).observer.....

        return binding.getRoot();
    }


    // opens the DatePickerDialog
    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), this, year, month, dayOfMonth);
        datePickerDialog.show();
    }


    // gets the input from the DatePickerDialog and displays it
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        this.year = year;
        this.month = month;
        this.dayOfMonth = dayOfMonth;
        String date = "day/month/year: " + dayOfMonth + "/" + (month + 1) + "/" + year;
        binding.selectDateView.setText(date);
    }



    // -------------------------------------------------------------
    // needed?
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    // -------------------------------------------------------------


} // end AddExpenseFragment class