package no.hiof.trondkw.budgetapp.ui.login;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import no.hiof.trondkw.budgetapp.LoginActivity;
import no.hiof.trondkw.budgetapp.MainActivity;
import no.hiof.trondkw.budgetapp.R;
import no.hiof.trondkw.budgetapp.databinding.FragmentLoginBinding;
import no.hiof.trondkw.budgetapp.utils.Utilities;


public class LoginFragment extends Fragment {

    private FirebaseAuth mAuth;
    private FragmentLoginBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(getLayoutInflater());

        mAuth = FirebaseAuth.getInstance();

        // Set onclick listeners
        binding.forgotPasswordTextview.setOnClickListener(view -> { });
        binding.registerUserTextview.setOnClickListener(view -> Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_registerFragment));

        binding.loginButton.setOnClickListener(view -> userLogin());
        binding.noUserButton.setOnClickListener(view -> startActivity(new Intent(getContext(), MainActivity.class)));

        return binding.getRoot();
    }


    private void userLogin() {

        String email = binding.emailInput.getText().toString().trim();
        String password = binding.passwordInput.getText().toString().trim();


        if (validateInput(email, password)) {

            binding.progressBar.setVisibility(View.VISIBLE);

            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {

                if(task.isSuccessful()) {
                    // User registered successfully, redirect to main activity
                    startActivity(new Intent(getContext(), MainActivity.class));

                } else {
                    // Check what went wrong
                    binding.progressBar.setVisibility(View.GONE);

                    if(!Utilities.checkNetworkStatus(requireActivity())) {
                        Toast.makeText(getContext(), "Cannot connect, no internet access", Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(getContext(), "Failed to log in", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }


    private boolean validateInput(String email, String password) {

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

        return true;
    }







} // end LoginFragment