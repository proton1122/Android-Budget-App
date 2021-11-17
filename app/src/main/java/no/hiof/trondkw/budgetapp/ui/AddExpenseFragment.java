package no.hiof.trondkw.budgetapp.ui;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import java.time.LocalDate;
import java.util.Calendar;

import no.hiof.trondkw.budgetapp.R;
import no.hiof.trondkw.budgetapp.databinding.FragmentAddExpenseBinding;
import no.hiof.trondkw.budgetapp.models.Expense;
import no.hiof.trondkw.budgetapp.viewmodels.BudgetMonthViewModel;

public class AddExpenseFragment extends Fragment implements DatePickerDialog.OnDateSetListener {

    private BudgetMonthViewModel budgetMonthViewModel;
    private FragmentAddExpenseBinding binding;

    private boolean isDateSet = false;
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
        binding = FragmentAddExpenseBinding.inflate(inflater, container, false);
        binding.setBudgetMonthViewModel(budgetMonthViewModel);

        binding.selectDateView.setOnClickListener(view -> showDatePickerDialog());
        
        binding.addExpenseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LocalDate expenseDate = LocalDate.of(year, month, dayOfMonth);
                String expenseDescription = binding.descriptionInput.getText().toString();
                double expenseSum =  Double.parseDouble(binding.expenseSumInput.getText().toString());

                Expense newExpense = new Expense(expenseDate, expenseDescription, expenseSum);
                budgetMonthViewModel.addExpense(newExpense);

                Navigation.findNavController(view).navigate(R.id.action_addExpenseFragment_to_savingsOverviewFragment);
            }
        });
        return binding.getRoot();
    }


    private void showDatePickerDialog() {
        if (!isDateSet)
            setDateToday();

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), this, year, month, dayOfMonth);
        datePickerDialog.show();
    }


    // gets the input from the DatePickerDialog and displays it
    // TODO
    // Set a more fitting date format for display
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        this.year = year;
        this.month = month;
        this.dayOfMonth = dayOfMonth;
        String date = "day/month/year: " + dayOfMonth + "/" + (month + 1) + "/" + year;
        binding.selectDateView.setText(date);
    }

    // sets the start date of the DatePickerDialog
    // if no date has already been picked, set as 'today'
    private void setDateToday() {
            year = Calendar.getInstance().get(Calendar.YEAR);
            month = Calendar.getInstance().get(Calendar.MONTH);
            dayOfMonth = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
            isDateSet = true;
    }


    // TODO: remove if not using toolbar menu
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        //MenuInflater menuInflater = requireActivity().getMenuInflater();
        inflater.inflate(R.menu.add_expense_menu, menu);

        super.onCreateOptionsMenu(menu, inflater);
    }

    // TODO: remove if not using toolbar menu
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.save_expense) {
            saveExpense();
            return true;
        }
        else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void saveExpense() {
        // TODO: move adding new expense from onClickListener to here?
        Toast toast = Toast.makeText(getContext(), "Saved", Toast.LENGTH_LONG);
        toast.show();
    }



} // end AddExpenseFragment class