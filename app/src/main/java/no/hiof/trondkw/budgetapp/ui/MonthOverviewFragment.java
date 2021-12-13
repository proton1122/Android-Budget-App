package no.hiof.trondkw.budgetapp.ui;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.time.LocalDate;

import no.hiof.trondkw.budgetapp.R;
import no.hiof.trondkw.budgetapp.databinding.FragmentMonthOverviewBinding;
import no.hiof.trondkw.budgetapp.models.BudgetMonth;
import no.hiof.trondkw.budgetapp.repositories.BudgetMonthRepository;
import no.hiof.trondkw.budgetapp.repositories.OnGetDataListener;
import no.hiof.trondkw.budgetapp.ui.dialogs.BudgetDialog;
import no.hiof.trondkw.budgetapp.ui.dialogs.MonthYearPickerDialog;
import no.hiof.trondkw.budgetapp.utils.Utilities;
import no.hiof.trondkw.budgetapp.viewmodels.BudgetMonthViewModel;

public class MonthOverviewFragment extends Fragment {

    private final String TAG = "MonthOverviewFragment";


    private BudgetMonthViewModel budgetMonthViewModel;
    private FragmentMonthOverviewBinding binding;
    private BudgetMonthRepository repository;
    private Canvas canvas;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get view model
        repository = BudgetMonthRepository.getInstance();
        budgetMonthViewModel = new ViewModelProvider(requireActivity()).get(BudgetMonthViewModel.class);

    } // end onCreate


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // test graph drawing on simple canvas
        Bitmap bitmap = Bitmap.createBitmap(1000, 1000, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);

        // Set data bindings
        binding = FragmentMonthOverviewBinding.inflate(inflater, container, false);
        binding.setCurrentMonth(budgetMonthViewModel);
        binding.setLifecycleOwner(requireActivity());
        binding.graphImage.setImageBitmap(bitmap);

        // set onclick listeners
        binding.currentYearMonth.setOnClickListener(view -> openDatePickerDialog());
        binding.currentBudget.setOnClickListener(view1 -> openEditBudgetDialog());

        return binding.getRoot();

    } // end onCreateView


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Observe viewModel
        budgetMonthViewModel.getCurrentBudgetMonth().observe(requireActivity(), budgetMonth -> {
            Log.d(TAG, "Observer called on ViewModel.currentMonth");
            System.out.println("Observer called on ViewModel.currentMonth in onViewCreated().");

            // ViewModel changed, update data binding
            binding.setCurrentMonth(budgetMonthViewModel);

            // TODO: Check if something has actually changed before drawing graph again?
            // Draw new graph
            Thread thread = new Thread(this::drawGraph);
            thread.start();

        });

        drawGraph();

    } // end onViewCreated

    public void openEditBudgetDialog() {
        BudgetDialog budgetDialog = new BudgetDialog();

        String budget = Utilities.decimalFormatter.format(budgetMonthViewModel.getBudget());

        Bundle args = new Bundle();
        args.putString(getResources().getString(R.string.BUDGET), budget);

        budgetDialog.setArguments(args);
        budgetDialog.show(requireActivity().getSupportFragmentManager(), null);

    } // end openEditBudgetDialog


    private void openDatePickerDialog() {
        MonthYearPickerDialog monthYearPicker = new MonthYearPickerDialog();

        // get current year / month
        int year = budgetMonthViewModel.getBudgetMonthYearValue();
        int month = budgetMonthViewModel.getBudgetMonthMonthValue();

        // send current month to date picker
        Bundle args = new Bundle();
        args.putInt(getResources().getString(R.string.YEAR), year);
        args.putInt(getResources().getString(R.string.MONTH), month);

        monthYearPicker.setArguments(args);
        monthYearPicker.show(requireActivity().getSupportFragmentManager(), null);

    } // end openDatePickerDialog()


    // Draw main graph
    // TODO: Move to separate class
    private void drawGraph() {

        Log.d(TAG, "Entered drawGraph()");

            RectF rectangle = new RectF(100, 100, 900, 900);

            float budget = budgetMonthViewModel.getBudget().floatValue();
            float expenses = budgetMonthViewModel.getTotalExpenses().floatValue();

            // getResources().getColor(int color) is deprecated and requires theme now
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

            int startPos = -90;
            float percent = (expenses / budget) * 100;
            float circle = (float)360;
            float angle = (circle / 100) * percent;

            canvas.drawArc(rectangle, startPos, 360, false, basePaint);
            canvas.drawArc(rectangle, startPos, angle, false, budgetPaint);

    } // end drawGraph()

} // end MonthOverviewFragment class