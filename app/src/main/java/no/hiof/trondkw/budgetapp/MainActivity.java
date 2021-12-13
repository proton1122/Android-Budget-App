package no.hiof.trondkw.budgetapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.firebase.ui.auth.AuthUI;

import java.time.LocalDate;

import no.hiof.trondkw.budgetapp.databinding.ActivityMainBinding;
import no.hiof.trondkw.budgetapp.interfaces.IBudgetDialogListener;
import no.hiof.trondkw.budgetapp.interfaces.IMonthYearPickerDialogListener;
import no.hiof.trondkw.budgetapp.models.BudgetMonth;
import no.hiof.trondkw.budgetapp.repositories.BudgetMonthRepository;
import no.hiof.trondkw.budgetapp.interfaces.OnGetDataListener;
import no.hiof.trondkw.budgetapp.viewmodels.BudgetMonthViewModel;

public class MainActivity extends AppCompatActivity implements IBudgetDialogListener, IMonthYearPickerDialogListener {

    private ActivityMainBinding binding;
    private BudgetMonthViewModel budgetMonthViewModel;
    private BudgetMonthRepository repository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set view model and repository
        repository = BudgetMonthRepository.getInstance();
        budgetMonthViewModel = new ViewModelProvider(this).get(BudgetMonthViewModel.class);

        // Set up data binding
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        binding.setLifecycleOwner(this);
        setContentView(binding.getRoot());

        // Set default month (this month)
        setDefaultMonth();

        // Set up navigation with appbar / bottom navigation / drawer
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);

        DrawerLayout drawerLayout = binding.drawerLayout;
        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.monthOverviewFragment,
                R.id.monthDetailsFragment,
                R.id.yearOverviewFragment,
                R.id.savingsOverviewFragment)
                .setOpenableLayout(drawerLayout)
                .build();

        NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController);
        NavigationUI.setupWithNavController(binding.navigationView, navController);

    } // end onCreate


    // Set current month to be displayed when app starts
    private void setDefaultMonth() {
        int year = LocalDate.now().getYear();
        int month = LocalDate.now().getMonth().getValue();
        loadMonth(year, month);
    }

    // Send input from EditBudgetDialog to viewModel
    @Override
    public void setNewBudget(String budgetInput) {
        budgetMonthViewModel.setBudget(budgetInput);
    }

    // Send input from MonthYearPickerDialog to viewModel
    @Override
    public void loadMonth(int year, int month) {
        binding.progressBar.setVisibility(View.VISIBLE);

        repository.getMonth(year, month, new OnGetDataListener() {
            @Override
            public void onSuccess(BudgetMonth budgetMonth) {
                binding.progressBar.setVisibility(View.GONE);
                budgetMonthViewModel.setBudgetMonth(budgetMonth);
            }

            @Override
            public void onFailure(Exception exception) {
                binding.progressBar.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "Error loading from database", Toast.LENGTH_LONG).show();
            }
        });
    }

    // Closes the navigation drawer instead of the application if the drawer is open
    @Override
    public void onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }

    // Set up overflow menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    // Handle overflow menu option selection
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.overflow_settings) {
            // TODO: Create settings fragment -> navigate
            return true;
        }

        else if(item.getItemId() == R.id.overflow_logout) {
            logOutUser();
            return true;
        }

        else {
            return super.onOptionsItemSelected(item);
        }
    }

    // Log out user
    private void logOutUser() {
        AuthUI.getInstance().signOut(getApplicationContext()).addOnCompleteListener(task -> {
            Toast.makeText(getApplicationContext(), "Signed out", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        });
    }


} // end MainActivity class