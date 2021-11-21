package no.hiof.trondkw.budgetapp.ui;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import no.hiof.trondkw.budgetapp.R;
import no.hiof.trondkw.budgetapp.databinding.FragmentMonthOverviewBinding;
import no.hiof.trondkw.budgetapp.ui.dialogs.BudgetDialog;
import no.hiof.trondkw.budgetapp.ui.dialogs.MonthYearPickerDialog;
import no.hiof.trondkw.budgetapp.utils.Utilities;
import no.hiof.trondkw.budgetapp.viewmodels.BudgetMonthViewModel;

public class MonthOverviewFragment extends Fragment {

    private BudgetMonthViewModel budgetMonthViewModel;
    private FragmentMonthOverviewBinding binding;
    private Canvas canvas;

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

        // test graph drawing on simple canvas
        Bitmap bitmap = Bitmap.createBitmap(1000, 1000, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
        drawGraph();

        binding.graphImage.setImageBitmap(bitmap);

        // set onclick listeners
        binding.currentYearMonth.setOnClickListener(view -> openDatePickerDialog());
        binding.currentBudget.setOnClickListener(view1 -> openEditBudgetDialog());

        // observe viewModel
        budgetMonthViewModel.getBudget().observe(requireActivity(), aDouble -> {
            binding.setCurrentMonth(budgetMonthViewModel);
            drawGraph();
        });
        budgetMonthViewModel.getExpenseList().observe(requireActivity(), expenses -> {
            binding.setCurrentMonth(budgetMonthViewModel);
            drawGraph();
        });

        //budgetMonthViewModel.getCurrentMonthId().observe(requireActivity(), integer -> binding.setCurrentMonth(budgetMonthViewModel));

        return binding.getRoot();
    }



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

        Paint basePaint = new Paint();
        basePaint.setColor(getResources().getColor(R.color.background_dark_grey));
        basePaint.setStyle(Paint.Style.STROKE);
        basePaint.setStrokeWidth(30);


        Paint budgetPaint = new Paint();
        budgetPaint.setStyle(Paint.Style.STROKE);
        budgetPaint.setStrokeWidth(25);

        if (expenses > budget)
            budgetPaint.setColor(getResources().getColor(R.color.rally_orange));
        else
            budgetPaint.setColor(getResources().getColor(R.color.rally_primary_green));



        int startPos = -90;

        float percent = (expenses / budget) * 100;

        float circle = (float)360;
        float angle = (circle / 100) * percent;


        canvas.drawArc(rectangle, -90, 360, false, basePaint);
        canvas.drawArc(rectangle, -90, angle, false, budgetPaint);


    }



} // end MonthOverviewFragment class