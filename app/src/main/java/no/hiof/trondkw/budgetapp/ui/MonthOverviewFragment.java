package no.hiof.trondkw.budgetapp.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentMonthOverviewBinding.inflate(inflater, container, false);
        binding.setCurrentMonth(budgetMonthViewModel);

        // observer viewModel...
        // budgetMonthViewModel.get(...).observer.....

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.editBudgetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new EditBudgetDialogFragment().show(getChildFragmentManager(), null);
            }
        });

    }
} // end MonthOverviewFragment class