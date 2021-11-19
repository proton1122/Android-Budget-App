package no.hiof.trondkw.budgetapp.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import no.hiof.trondkw.budgetapp.R;
import no.hiof.trondkw.budgetapp.adapter.ExpenseRecyclerAdapter;
import no.hiof.trondkw.budgetapp.databinding.FragmentMonthDetailsBinding;
import no.hiof.trondkw.budgetapp.models.Expense;
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
        binding = FragmentMonthDetailsBinding.inflate(inflater, container, false);
        binding.setBudgetMonthViewModel(budgetMonthViewModel);
        requireActivity().setTitle("Monthly Details");

        // observe viewModel
        budgetMonthViewModel.getExpenseList().observe(requireActivity(), expenses -> binding.setBudgetMonthViewModel(budgetMonthViewModel));

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // set the adapter for recycler view and push list of expenses to be shown
        ExpenseRecyclerAdapter adapter = new ExpenseRecyclerAdapter();
        binding.recyclerView.setAdapter(adapter);
        adapter.setExpenses(budgetMonthViewModel.getExpenseList().getValue());

        // set navigation to addExpenseFragment with FAB, move this to .xml later
        binding.addExpenseFab.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_monthDetailsFragment_to_addExpenseFragment));

        // set the onclick for each card view, send data as bundle to fill in the input fields
        adapter.setOnItemClickListener(expense -> {
            Bundle args = new Bundle();

            args.putString(Expense.ID, expense.getId());
            args.putString(Expense.TITLE, expense.getTitle());
            args.putString(Expense.CATEGORY, "");
            args.putString(Expense.DATE, expense.getDate().toString());
            args.putDouble(Expense.SUM, expense.getSum());

            Navigation.findNavController(view).navigate(R.id.action_monthDetailsFragment_to_addExpenseFragment, args);
        });
    }

} // end MonthDetailsFragment class