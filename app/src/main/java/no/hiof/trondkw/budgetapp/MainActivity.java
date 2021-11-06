package no.hiof.trondkw.budgetapp;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import no.hiof.trondkw.budgetapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        // R.id.... get ID from NavHostFragment <fragment> in activity_main.xml
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController);
        NavigationUI.setupWithNavController(binding.navigationView, navController);



        /*

        -- REMOVED ALL NAVIGATION ELEMENTS FOR CLEAN START --


        //BottomNavigationView navView = findViewById(R.id.nav_view);
        //NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        */


    }
}