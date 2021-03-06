package no.hiof.trondkw.budgetapp.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import no.hiof.trondkw.budgetapp.R;
import no.hiof.trondkw.budgetapp.databinding.FragmentSavingsOverviewBinding;
import no.hiof.trondkw.budgetapp.databinding.FragmentYearOverviewBinding;
import no.hiof.trondkw.budgetapp.viewmodels.BudgetMonthViewModel;

public class YearOverviewFragment extends Fragment {

    private BudgetMonthViewModel budgetMonthViewModel;
    private FragmentYearOverviewBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        budgetMonthViewModel = new ViewModelProvider(requireActivity()).get(BudgetMonthViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentYearOverviewBinding.inflate(inflater, container, false);

        //binding.testButton.setOnClickListener(view -> budgetMonthViewModel.testSaveToDatabase());
        //binding.testButton2.setOnClickListener(view -> budgetMonthViewModel.testGetData());

        return binding.getRoot();
    }


} // end YearOverviewFragment class