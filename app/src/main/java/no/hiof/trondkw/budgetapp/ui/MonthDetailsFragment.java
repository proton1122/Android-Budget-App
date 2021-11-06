package no.hiof.trondkw.budgetapp.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import no.hiof.trondkw.budgetapp.R;
import no.hiof.trondkw.budgetapp.databinding.FragmentAddExpenseBinding;
import no.hiof.trondkw.budgetapp.databinding.FragmentMonthDetailsBinding;
import no.hiof.trondkw.budgetapp.viewmodels.BudgetMonthViewModel;

public class MonthDetailsFragment extends Fragment {

    private BudgetMonthViewModel budgetMonthViewModel;
    private FragmentMonthDetailsBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        budgetMonthViewModel = new ViewModelProvider(requireActivity()).get(BudgetMonthViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentMonthDetailsBinding.inflate(inflater, container, false);

        // set this after adding <data> to xml
        // binding.setBudgetMonthViewModel(....)

        // observer viewModel...
        // budgetMonthViewModel.get(...).observer.....

        return binding.getRoot();
    }



} // end MonthDetailsFragment class