package no.hiof.trondkw.budgetapp.ui.login;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.auth.data.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Locale;

import no.hiof.trondkw.budgetapp.R;
import no.hiof.trondkw.budgetapp.databinding.FragmentRegisterBinding;


public class RegisterFragment extends Fragment {

    private FirebaseAuth mAuth;
    private FragmentRegisterBinding binding;
    private boolean userRegistered = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentRegisterBinding.inflate(getLayoutInflater());

        mAuth = FirebaseAuth.getInstance();

        binding.registerButton.setOnClickListener(view -> {
            registerUser();

            // TODO: fix redirect to loginFragment or main activity
            System.out.println("New user registered, try to navigate...");

            /*
            if (userRegistered) {
                System.out.println("Try to navigate to loginFragment..");
                Navigation.findNavController(view).navigate(R.id.action_registerFragment_to_loginFragment);
            }
            */


        });

        return binding.getRoot();
    }



    private void registerUser() {

        String email = binding.emailInput.getText().toString().trim();
        String password = binding.passwordInput.getText().toString().trim();
        String passwordConfirm = binding.passwordConfirmInput.getText().toString().trim();

        if(validateInput(email, password, passwordConfirm)) {

            binding.progressBar.setVisibility(View.VISIBLE);

            // Create new user
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {

                        if(task.isSuccessful()) {
                            binding.progressBar.setVisibility(View.GONE);

                            FirebaseDatabase.getInstance("https://eksamen-budgetapp-default-rtdb.europe-west1.firebasedatabase.app").getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue("Test")
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            if(task.isSuccessful()) {
                                                Toast.makeText(getContext(), "New account created", Toast.LENGTH_LONG).show();


                                                // TODO: After creating user, navigate to MainActivity or LoginFragment so user can login?

                                            }
                                            else {
                                                Toast.makeText(getContext(), "Failed to create new account", Toast.LENGTH_LONG).show();
                                            }
                                        }
                            });

                            // navigate here

                            // Doesn't work
                            userRegistered = true;

                        }
                        else {
                            binding.progressBar.setVisibility(View.GONE);
                            Toast.makeText(getContext(), "Failed to create new account", Toast.LENGTH_LONG).show();
                        }
                    });
        }
    }

    private boolean validateInput(String email, String password, String passwordConfirm) {

        if (email.isEmpty()) {
            binding.emailLayout.setErrorEnabled(true);
            binding.emailLayout.setError(getResources().getString(R.string.required));
            binding.emailLayout.requestFocus();
            return false;
        } else {
            binding.emailLayout.setErrorEnabled(false);
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.emailLayout.setErrorEnabled(true);
            binding.emailLayout.setError(getResources().getString(R.string.email_not_valid));
            binding.emailLayout.requestFocus();
            return false;
        } else {
            binding.emailLayout.setErrorEnabled(false);
        }

        if(password.isEmpty()) {
            binding.passwordLayout.setErrorEnabled(true);
            binding.passwordLayout.setError(getResources().getString(R.string.required));
            binding.passwordLayout.requestFocus();
            return false;
        } else {
            binding.passwordLayout.setErrorEnabled(false);
        }

        if(password.length() < 6) {
            binding.passwordLayout.setErrorEnabled(true);
            binding.passwordLayout.setError(getResources().getString(R.string.password_too_short));
            binding.passwordLayout.requestFocus();
            return false;
        } else {
            binding.passwordLayout.setErrorEnabled(false);
        }

        if(passwordConfirm.isEmpty()) {
            binding.passwordConfirmLayout.setErrorEnabled(true);
            binding.passwordConfirmLayout.setError(getResources().getString(R.string.required));
            binding.passwordConfirmLayout.requestFocus();
            return false;
        } else {
            binding.passwordConfirmLayout.setErrorEnabled(false);
        }

        if (!password.equals(passwordConfirm)) {
            binding.passwordConfirmLayout.setErrorEnabled(true);
            binding.passwordConfirmLayout.setError(getResources().getString(R.string.password_not_confirmed));
            binding.passwordConfirmLayout.requestFocus();
            return false;
        } else {
            binding.passwordConfirmLayout.setErrorEnabled(false);
        }

        return true;
    }



} // end RegisterFragment