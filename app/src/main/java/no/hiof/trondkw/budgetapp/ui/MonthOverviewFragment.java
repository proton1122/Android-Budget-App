package no.hiof.trondkw.budgetapp.ui;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import no.hiof.trondkw.budgetapp.R;
import no.hiof.trondkw.budgetapp.databinding.FragmentMonthOverviewBinding;
import no.hiof.trondkw.budgetapp.models.BudgetMonth;
import no.hiof.trondkw.budgetapp.models.Expense;
import no.hiof.trondkw.budgetapp.ui.dialogs.BudgetDialog;
import no.hiof.trondkw.budgetapp.ui.dialogs.MonthYearPickerDialog;
import no.hiof.trondkw.budgetapp.utils.Utilities;
import no.hiof.trondkw.budgetapp.viewmodels.BudgetMonthViewModel;

public class MonthOverviewFragment extends Fragment {

    // shared preference
    // persistent login

    private BudgetMonthViewModel budgetMonthViewModel;
    private FragmentMonthOverviewBinding binding;
    private Canvas canvas;

    // Time to do some hacky solution
    private int currentMonthId;
    private double budget;
    private int expensesListSize;

    // ----------- NEW STUFF -------------------------
    private BudgetMonth currentMonth;
    // --------------------------------------------------------




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        budgetMonthViewModel = new ViewModelProvider(requireActivity()).get(BudgetMonthViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // test graph drawing on simple canvas
        Bitmap bitmap = Bitmap.createBitmap(1000, 1000, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);

        // Set data bindings
        binding = FragmentMonthOverviewBinding.inflate(inflater, container, false);
        binding.setCurrentMonth(budgetMonthViewModel);
        binding.graphImage.setImageBitmap(bitmap);

        // set onclick listeners
        binding.currentYearMonth.setOnClickListener(view -> openDatePickerDialog());
        binding.currentBudget.setOnClickListener(view1 -> openEditBudgetDialog());

        // Set fragment title
        requireActivity().setTitle("Monthly Overview");

        // ----------- NEW STUFF -------------------------
        currentMonth = budgetMonthViewModel.getCurrentMonth_2().getValue();
        // --------------------------------------------------------


        // Hacky solution
        currentMonthId = budgetMonthViewModel.getCurrentMonthId().getValue();
        budget = budgetMonthViewModel.getBudget().getValue();
        expensesListSize = budgetMonthViewModel.getExpenseList().getValue().size();
        // --------------------------------------------------------

        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Observe viewModel
        budgetMonthViewModel.getCurrentMonth_2().observe(requireActivity(), new Observer<BudgetMonth>() {
            @Override
            public void onChanged(BudgetMonth budgetMonth) {
                System.out.println("Observer called on ViewModel.currentMonth in onViewCreated().");

                // ViewModel changed, update data binding
                binding.setCurrentMonth(budgetMonthViewModel);

                // TODO: Check if something has actually changed before drawing graph again?
                // Draw new graph
                drawGraph();
            }
        });






        // TODO: fix too many calls after changing fragment
        System.out.println("calling drawGraph() from onViewCreated\n");
        drawGraph();

        // ------------ OLD STUFF --------------------
        /*
        budgetMonthViewModel.getCurrentMonthId().observe(requireActivity(), integer -> {

            if(currentMonthId != integer) {
                System.out.println("monthId changed, calling drawGraph\n");
                drawGraph();
            }
        });
        budgetMonthViewModel.getBudget().observe(requireActivity(), aDouble -> {
            // aDouble is the budget value -> TODO: change name?
            binding.setCurrentMonth(budgetMonthViewModel);

            //System.out.println("----------------------------------------------------");
            //System.out.println("ViewModel.budget changed in the observer");
            //System.out.println("----------------------------------------------------");

            // Hacky code :)
            if(budget != aDouble) {
                System.out.println("budget changed, calling drawGraph from budget observer");
                System.out.println("Current budget in fragment: " + budget + " ---- " + "ViewModel.budget: " + aDouble);
                budget = aDouble;
                System.out.println("Calling drawGraph from budget observer");
                drawGraph();
                System.out.println("----------------------------------------------------");

            }
        });
        budgetMonthViewModel.getExpenseList().observe(requireActivity(), expenses -> {
            // expenses is the updated list of expenses
            binding.setCurrentMonth(budgetMonthViewModel);

            //System.out.println("----------------------------------------------------");
            //System.out.println("ViewModel.expenseList changed in the observer");
            //System.out.println("----------------------------------------------------");


            // Hacky code :)
            if(expensesListSize != expenses.size()) {
                System.out.println("expenseListSize changed, calling drawGraph from expenseList observer");
                System.out.println("Current listSize in Fragment: " + expensesListSize + " --- " + "ViewModel.listSize: " + expenses.size());

                expensesListSize = expenses.size();

                System.out.println("Calling drawGraph from expenseList observer");
                drawGraph();
                System.out.println("----------------------------------------------------");

            }
        });
        */
        // --------------------------------------------------------

    } // end onViewCreated

    public void openEditBudgetDialog() {
        BudgetDialog budgetDialog = new BudgetDialog();

        String budget = Utilities.decimalFormatter.format(budgetMonthViewModel.getBudget().getValue());

        Bundle args = new Bundle();
        args.putString(getResources().getString(R.string.BUDGET), budget);

        budgetDialog.setArguments(args);
        budgetDialog.show(requireActivity().getSupportFragmentManager(), null);

        // try to get the soft keyboard to open when opening the dialog, but getWindows gives nullRef error
        //budgetDialog.getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
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


    private void drawGraph() {
        RectF rectangle = new RectF(100, 100, 900, 900);


        float budget = budgetMonthViewModel.getBudget().getValue().floatValue();
        float expenses = budgetMonthViewModel.getTotalExpenses().getValue().floatValue();


        System.out.println("\nEntered drawGraph...");
        System.out.println("viewModel budget: " + budget);
        System.out.println("viewModel expenses: " + expenses);
        System.out.println("----------------------------------------------------");

        Paint basePaint = new Paint();

        basePaint.setColor(Color.parseColor("#27272f"));
        //basePaint.setColor(getResources().getColor(R.color.background_dark_grey));
        basePaint.setStyle(Paint.Style.STROKE);
        basePaint.setStrokeWidth(30);


        Paint budgetPaint = new Paint();
        budgetPaint.setStyle(Paint.Style.STROKE);
        budgetPaint.setStrokeWidth(25);

        if(budget == 0) {
            budgetPaint.setColor(Color.parseColor("#27272f"));
        }
        else if (expenses >= budget) {
            //budgetPaint.setColor(getResources().getColor(R.color.rally_orange));
            budgetPaint.setColor(Color.parseColor("#ff6859"));

        }
        else {
            //budgetPaint.setColor(getResources().getColor(R.color.rally_primary_green));
            budgetPaint.setColor(Color.parseColor("#1eb980"));
        }


        // --test color numbers-----------------------------------------------
        Paint base_grey = new Paint();
        base_grey.setColor(Color.parseColor("#27272f"));
        Paint rally_orange = new Paint();
        rally_orange.setColor(Color.parseColor("#ff6859"));
        Paint rally_p_green = new Paint();
        rally_p_green.setColor(Color.parseColor("#1eb980"));
        // -------------------------------------------------------------------
        System.out.println("Base grey = " + base_grey.getColor() + " | " + "Rally orange = " + rally_orange.getColor() + " | " + "Rally green = " + rally_p_green.getColor());
        System.out.println("drawGraph.budgetPaint.color: " + budgetPaint.getColor());
        System.out.println("----------------------------------------------------");
        // -------------------------------------------------------------------



        int startPos = -90;

        float percent = (expenses / budget) * 100;

        float circle = (float)360;
        float angle = (circle / 100) * percent;


        canvas.drawArc(rectangle, -90, 360, false, basePaint);
        canvas.drawArc(rectangle, -90, angle, false, budgetPaint);


    }



} // end MonthOverviewFragment class