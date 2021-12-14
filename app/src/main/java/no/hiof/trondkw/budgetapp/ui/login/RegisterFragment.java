package no.hiof.trondkw.budgetapp.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

import no.hiof.trondkw.budgetapp.MainActivity;
import no.hiof.trondkw.budgetapp.R;
import no.hiof.trondkw.budgetapp.databinding.FragmentRegisterBinding;
import no.hiof.trondkw.budgetapp.utils.Utilities;


public class RegisterFragment extends Fragment {

    private FragmentRegisterBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentRegisterBinding.inflate(getLayoutInflater());

        binding.registerButton.setOnClickListener(view -> {
            if(!Utilities.checkNetworkStatus(requireActivity())) {
                Toast.makeText(requireActivity(), "Can not register without internet access", Toast.LENGTH_SHORT).show();
            } else {
                registerUser();
            }
        });

        return binding.getRoot();
    } // end onCreateView()


    private void registerUser() {
        String email = binding.emailInput.getText().toString().trim();
        String password = binding.passwordInput.getText().toString().trim();
        String passwordConfirm = binding.passwordConfirmInput.getText().toString().trim();

        if(validateInput(email, password, passwordConfirm)) {

            binding.progressBar.setVisibility(View.VISIBLE);

            // Try to create new user
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {

                if(task.isSuccessful()) {
                    // User successfully registered
                    binding.progressBar.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "New account created", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(requireActivity(), MainActivity.class));
                }
                else {
                    // Registration failed
                    binding.progressBar.setVisibility(View.GONE);

                    if(task.getException() != null) {
                        try {
                            throw task.getException();
                        } catch(FirebaseAuthUserCollisionException e) {
                            Toast.makeText(requireActivity(), "Email address already in use", Toast.LENGTH_LONG).show();
                        } catch (FirebaseAuthWeakPasswordException e) {
                            Toast.makeText(requireActivity(), "Password is too weak", Toast.LENGTH_LONG).show();
                        } catch (FirebaseAuthInvalidCredentialsException e) {
                            Toast.makeText(requireActivity(), "Invalid email address", Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(getContext(), "Failed to create new account", Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(getContext(), "Failed to create new account", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    } // end registerUser()

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
    } // end validateInput()

} // end RegisterFragment