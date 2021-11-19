package no.hiof.trondkw.budgetapp.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;

import no.hiof.trondkw.budgetapp.R;
import no.hiof.trondkw.budgetapp.databinding.FragmentMonthOverviewBinding;
import no.hiof.trondkw.budgetapp.models.Expense;
import no.hiof.trondkw.budgetapp.ui.dialogs.BudgetDialog;
import no.hiof.trondkw.budgetapp.ui.dialogs.MonthYearPickerDialog;
import no.hiof.trondkw.budgetapp.viewmodels.BudgetMonthViewModel;

public class MonthOverviewFragment extends Fragment {

    private BudgetMonthViewModel budgetMonthViewModel;
    private FragmentMonthOverviewBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        budgetMonthViewModel = new ViewModelProvider(requireActivity()).get(BudgetMonthViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMonthOverviewBinding.inflate(inflater, container, false);
        binding.setCurrentMonth(budgetMonthViewModel);
        requireActivity().setTitle("Monthly Overview");

        // set onclick listeners
        binding.currentYearMonth.setOnClickListener(view -> openDatePickerDialog());
        binding.currentBudget.setOnClickListener(view1 -> openEditBudgetDialog());

        // observe viewModel
        budgetMonthViewModel.getBudget().observe(requireActivity(), aDouble -> binding.setCurrentMonth(budgetMonthViewModel));
        budgetMonthViewModel.getExpenseList().observe(requireActivity(), expenses -> binding.setCurrentMonth(budgetMonthViewModel));

        //budgetMonthViewModel.getCurrentMonthId().observe(requireActivity(), integer -> binding.setCurrentMonth(budgetMonthViewModel));

        return binding.getRoot();
    }



    public void openEditBudgetDialog() {
        BudgetDialog budgetDialog = new BudgetDialog();

        String budget = budgetMonthViewModel.getBudget().getValue().toString();

        Bundle args = new Bundle();
        args.putString(getResources().getString(R.string.BUDGET), budget);

        budgetDialog.setArguments(args);
        budgetDialog.show(requireActivity().getSupportFragmentManager(), null);
    }


    private void openDatePickerDialog() {
        MonthYearPickerDialog monthYearPicker = new MonthYearPickerDialog();

        // get current year / month
        int year = budgetMonthViewModel.getCurrentYear();
        int month = budgetMonthViewModel.getCurrentMonth();

        // send current month to date picker
        Bundle args = new Bundle();
        args.putInt(getResources().getString(R.string.YEAR), year);
        args.putInt(getResources().getString(R.string.MONTH), month);

        monthYearPicker.setArguments(args);
        monthYearPicker.show(requireActivity().getSupportFragmentManager(), null);
    }


} // end MonthOverviewFragment class