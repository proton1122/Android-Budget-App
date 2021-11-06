package no.hiof.trondkw.budgetapp.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.time.LocalDate;

import no.hiof.trondkw.budgetapp.R;
import no.hiof.trondkw.budgetapp.databinding.FragmentMonthDetailsBinding;
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

        // set this after adding <data> to xml
        // binding.setBudgetMonthViewModel(....)

        // observer viewModel...
        // budgetMonthViewModel.get(...).observer.....

        return binding.getRoot();
    }


} // end MonthOverviewFragment class