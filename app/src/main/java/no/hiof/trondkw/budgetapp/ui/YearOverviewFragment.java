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

    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        budgetMonthViewModel = new ViewModelProvider(requireActivity()).get(BudgetMonthViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentYearOverviewBinding.inflate(inflater, container, false);

        requireActivity().setTitle("Year Overview");

        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();


        binding.testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                budgetMonthViewModel.testSaveToDatabase();


                /*
                FirebaseDatabase.getInstance("https://eksamen-budgetapp-default-rtdb.europe-west1.firebasedatabase.app").getReference("users")
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .setValue("Test")
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if(task.isSuccessful()) {
                                    Toast.makeText(getContext(), "onComplete success", Toast.LENGTH_LONG).show();

                                    // TODO: After creating user, navigate to MainActivity or LoginFragment so user can login?

                                }
                                else {
                                    Toast.makeText(getContext(), "onComplete failed", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                */

            }
        });


        return binding.getRoot();
    }


} // end YearOverviewFragment class