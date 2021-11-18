package no.hiof.trondkw.budgetapp.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import no.hiof.trondkw.budgetapp.databinding.FragmentMonthOverviewBinding;
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


        // test date picker for month/year

        binding.datePickerTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTestDatePicker();
            }
        });



        budgetMonthViewModel.getBudget().observe(requireActivity(), new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                binding.setCurrentMonth(budgetMonthViewModel);
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.editBudgetButton.setOnClickListener(view1 -> openEditBudgetDialog());
    }

    public void openEditBudgetDialog() {
        BudgetDialog budgetDialog = new BudgetDialog();

        String budget = budgetMonthViewModel.getBudget().getValue().toString();

        Bundle args = new Bundle();
        args.putString(BudgetDialog.BUDGET, budget);

        budgetDialog.setArguments(args);
        budgetDialog.show(requireActivity().getSupportFragmentManager(), null);
    }


    private void showTestDatePicker() {

        // show new fragment with number pickers...
        MonthYearPicker monthYearPicker = new MonthYearPicker();

        monthYearPicker.show(requireActivity().getSupportFragmentManager(), null);

    }


} // end MonthOverviewFragment class