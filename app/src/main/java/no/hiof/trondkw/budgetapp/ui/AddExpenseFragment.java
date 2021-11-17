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
import java.util.Locale;

import no.hiof.trondkw.budgetapp.R;
import no.hiof.trondkw.budgetapp.databinding.FragmentAddExpenseBinding;
import no.hiof.trondkw.budgetapp.models.Expense;
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
        binding = FragmentAddExpenseBinding.inflate(inflater, container, false);
        binding.setBudgetMonthViewModel(budgetMonthViewModel);

        // 1. Check if there is bundle...
        // 2. If NO bundle - ADD EXPENSE path
        // 3. If bundle - EDIT EXPENSE path
        if(getArguments() == null)
            addExpense();
        else
            editExpense();


        binding.dateInput.setOnClickListener(view -> showDatePickerDialog());
        
        binding.addExpenseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean validInput = validateInput();

                if(validInput) {
                    saveExpense();
                    Navigation.findNavController(view).navigate(R.id.action_addExpenseFragment_to_savingsOverviewFragment);
                }

            }
        });
        return binding.getRoot();
    }


    private void addExpense() {
        LocalDate now = LocalDate.now();
        setDate(now);

        String date1 = getDateFormat(now.getDayOfMonth(), now.getMonthValue(), now.getYear());
        binding.dateInput.setText(date1);

        // TODO: Set String variable
        binding.addExpenseButton.setText("Add Expense");
    }

    private void editExpense() {

        if (getArguments() != null) {

            LocalDate date = LocalDate.parse(getArguments().get(Expense.DATE).toString());
            setDate(date);

            String date2 = getDateFormat(date.getDayOfMonth(), date.getMonthValue(), date.getYear());
            binding.dateInput.setText(date2);

            binding.titleInput.setText(getArguments().get(Expense.TITLE).toString());

            String sum = Double.toString(getArguments().getDouble(Expense.SUM));
            binding.expenseSumInput.setText(sum);

            // TODO: Set String variable
            binding.addExpenseButton.setText("Save");

            // TODO: EDIT EXPENSE, NOT CREATE NEW ONE....

        }
    }


    /**
     *      Shows the DatePickerDialog.
     *      DatePickerDialog starts month from 0, so the month passed into it has to be increased by 1.
     */
    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), this, year, month - 1, dayOfMonth);
        datePickerDialog.show();
    }

    /**
     *      Gets the user selected date value from the DatePickerDialog.
     *      DatePickerDialog starts month from 0, so the month returned has to be decreased by 1.
     */
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        this.year = year;
        this.month = month + 1;
        this.dayOfMonth = dayOfMonth;
        String date = getDateFormat(dayOfMonth, month + 1, year);
        binding.dateInput.setText(date);
    }


    private void setDate(LocalDate date) {
        dayOfMonth = date.getDayOfMonth();
        month = date.getMonthValue();
        year = date.getYear();
    }


    // TODO: move to utils
    private String getDateFormat(int day, int month, int year) {
        // DatePickerDialog.onDateSet() starts month at 0!
        return day + " / " + month + " / " + year;
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


    private boolean validateInput() {
        if(binding.titleInput.getText().toString().isEmpty()) {
            binding.titleLayout.setErrorEnabled(true);
            binding.titleLayout.setError("Description required");
            return false;
        }
        else {
            binding.titleLayout.setErrorEnabled(false);
        }

        if(binding.expenseSumInput.getText().toString().isEmpty()) {
            binding.expenseSumLayout.setErrorEnabled(true);
            binding.expenseSumLayout.setError("Sum required");
            return false;
        }
        else {
            binding.expenseSumLayout.setErrorEnabled(false);
        }
        return true;
    }

    private void saveExpense() {
        // TODO: move adding new expense from onClickListener to here?
        Toast toast = Toast.makeText(getContext(), "Saved", Toast.LENGTH_LONG);
        toast.show();

        LocalDate expenseDate = LocalDate.of(year, month, dayOfMonth);
        String expenseDescription = binding.titleInput.getText().toString();
        double expenseSum =  Double.parseDouble(binding.expenseSumInput.getText().toString());

        Expense newExpense = new Expense(expenseDate, expenseDescription, expenseSum);
        budgetMonthViewModel.addExpense(newExpense);
    }




} // end AddExpenseFragment class