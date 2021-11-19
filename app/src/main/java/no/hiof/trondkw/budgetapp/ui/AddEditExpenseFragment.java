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

import no.hiof.trondkw.budgetapp.R;
import no.hiof.trondkw.budgetapp.databinding.FragmentAddEditExpenseBinding;
import no.hiof.trondkw.budgetapp.models.Expense;
import no.hiof.trondkw.budgetapp.viewmodels.BudgetMonthViewModel;

public class AddEditExpenseFragment extends Fragment implements DatePickerDialog.OnDateSetListener {

    private BudgetMonthViewModel budgetMonthViewModel;
    private FragmentAddEditExpenseBinding binding;

    private int dayOfMonth;
    private int month;
    private int year;

    private boolean editExpense;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        budgetMonthViewModel = new ViewModelProvider(requireActivity()).get(BudgetMonthViewModel.class);

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAddEditExpenseBinding.inflate(inflater, container, false);
        binding.setBudgetMonthViewModel(budgetMonthViewModel);

        // If NO bundle - ADD EXPENSE path
        // If bundle - EDIT EXPENSE path
        if(getArguments() == null)
            addExpenseFragment();
        else
            editExpenseFragment();


        binding.dateInput.setOnClickListener(view -> showDatePickerDialog());
        binding.addExpenseButton.setOnClickListener(view -> {

            boolean validInput = validateInput();

            if(validInput) {

                if (editExpense) {
                    saveEditedExpense();
                }
                else {
                    saveNewExpense();
                }

                Navigation.findNavController(view).navigate(R.id.action_addExpenseFragment_to_monthDetailsFragment);
            }
        });
        return binding.getRoot();
    }


    /**
     *      Setup for creating a new expense
     */
    private void addExpenseFragment() {
        requireActivity().setTitle("New Expense");

        LocalDate now = LocalDate.now();
        setDate(now);

        String dateString = getDateFormat(now.getDayOfMonth(), now.getMonthValue(), now.getYear());
        binding.dateInput.setText(dateString);

        binding.addExpenseButton.setText(getResources().getString(R.string.add_new_expense));
    }

    /**
     *      Setup for editing an existing expense
     */
    private void editExpenseFragment() {
        requireActivity().setTitle("Edit Expense");

        if (getArguments() != null) {

            editExpense = true;

            LocalDate date = LocalDate.parse(getArguments().get(Expense.DATE).toString());
            setDate(date);

            String dateString = getDateFormat(date.getDayOfMonth(), date.getMonthValue(), date.getYear());
            binding.dateInput.setText(dateString);

            binding.titleInput.setText(getArguments().get(Expense.TITLE).toString());

            String sum = Double.toString(getArguments().getDouble(Expense.SUM));
            binding.expenseSumInput.setText(sum);

            binding.addExpenseButton.setText(getResources().getString(R.string.save_edited_expense));
        }
    }


    /**     Shows the DatePickerDialog.
     *      DatePickerDialog starts month from 0, so the month passed into it has to be decreased by 1.
     */
    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), this, year, month - 1, dayOfMonth);
        datePickerDialog.show();
    }

    /**     Gets the user selected date value from the DatePickerDialog.
     *      DatePickerDialog starts month from 0, so the month returned has to be increased by 1.
     */
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        this.year = year;
        this.month = month + 1;
        this.dayOfMonth = dayOfMonth;
        String date = getDateFormat(dayOfMonth, month + 1, year);
        binding.dateInput.setText(date);
    }

    /**
     *      Sets the date that is used in the DateDialogPicker
     */
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


    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        super.onPrepareOptionsMenu(menu);
        MenuItem item = menu.findItem(R.id.save_expense);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.add_expense_menu, menu);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.save_expense) {
            saveNewExpense();
            return true;
        }
        else {
            return super.onOptionsItemSelected(item);
        }
    }

    /**
     *      Validate the textInput fields
     */
    private boolean validateInput() {
        System.out.println("validateInput()");

        if(binding.titleInput.getText().toString().isEmpty()) {
            binding.titleLayout.setErrorEnabled(true);
            binding.titleLayout.setError(getResources().getString(R.string.required));
            binding.titleInput.requestFocus();
            return false;
        }
        else {
            binding.titleLayout.setErrorEnabled(false);
        }

        if(binding.expenseSumInput.getText().toString().isEmpty()) {
            binding.expenseSumLayout.setErrorEnabled(true);
            binding.expenseSumLayout.setError(getResources().getString(R.string.required));
            binding.expenseSumInput.requestFocus();
            return false;
        }
        else {
            binding.expenseSumLayout.setErrorEnabled(false);
        }
        return true;
    }

    /**
     *      Create a new expense.
     */
    private void saveNewExpense() {

        LocalDate date = LocalDate.of(year, month, dayOfMonth);
        String title = binding.titleInput.getText().toString();
        double sum =  Double.parseDouble(binding.expenseSumInput.getText().toString());

        budgetMonthViewModel.addNewExpense(date, title, sum);
    }

    /**
     *      Edit an expense.
     */
    private void saveEditedExpense() {

        if (getArguments() != null) {
            String id = getArguments().getString(Expense.ID);
            Expense expense = budgetMonthViewModel.getExpense(id);

            // Could not find expense
            if (expense == null) {
                Toast.makeText(requireActivity(), "Something went wrong.", Toast.LENGTH_SHORT).show();
                return;
            }

            LocalDate date = LocalDate.of(year, month, dayOfMonth);
            String title = binding.titleInput.getText().toString();
            double sum = Double.parseDouble(binding.expenseSumInput.getText().toString());

            budgetMonthViewModel.editExpense(id, date, title, sum);
        }
    }

} // end AddExpenseFragment class