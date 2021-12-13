package no.hiof.trondkw.budgetapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import no.hiof.trondkw.budgetapp.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get firebase instance
        mAuth = FirebaseAuth.getInstance();

        // Set data binding
        ActivityLoginBinding binding = ActivityLoginBinding.inflate(getLayoutInflater());
        binding.setLifecycleOwner(this);
        setContentView(binding.getRoot());

        // Set up navigation and toolbar
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_login);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        Toolbar toolbar = binding.toolbar;

        NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration);

    } // end onCreate



    // Check if user is logged in
    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            startActivity(new Intent(this, MainActivity.class));
        }
    }

} // end LoginActivity