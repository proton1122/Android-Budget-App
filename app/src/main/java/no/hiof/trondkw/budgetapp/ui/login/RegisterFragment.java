package no.hiof.trondkw.budgetapp.ui.login;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Locale;

import no.hiof.trondkw.budgetapp.R;
import no.hiof.trondkw.budgetapp.databinding.FragmentRegisterBinding;


public class RegisterFragment extends Fragment {

    private FragmentRegisterBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentRegisterBinding.inflate(getLayoutInflater());


        binding.registerButton.setOnClickListener(view -> {
            if(validateInput()) {
                registerUser();
            }
        });

        return binding.getRoot();
    }



    private void registerUser() {



    }

    private boolean validateInput() {
        System.out.println("validateInput() called.......................");
        System.out.println("-------------------------------------------------------");

        String email = binding.emailInput.getText().toString().trim();
        String password = binding.passwordInput.getText().toString().trim();
        String passwordConfirm = binding.passwordConfirmInput.getText().toString().trim();

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