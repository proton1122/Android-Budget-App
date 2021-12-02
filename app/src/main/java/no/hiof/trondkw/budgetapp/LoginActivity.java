package no.hiof.trondkw.budgetapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import no.hiof.trondkw.budgetapp.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityLoginBinding binding = ActivityLoginBinding.inflate(getLayoutInflater());
        binding.setLifecycleOwner(this);
        setContentView(R.layout.activity_login);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_login);
        Toolbar toolbar = binding.toolbar;

        NavigationUI.setupWithNavController(toolbar, navController);
    }


}