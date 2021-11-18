package no.hiof.trondkw.budgetapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import no.hiof.trondkw.budgetapp.databinding.ActivityMainBinding;
import no.hiof.trondkw.budgetapp.interfaces.IBudgetDialogListener;
import no.hiof.trondkw.budgetapp.interfaces.IMonthYearPickerDialogListener;
import no.hiof.trondkw.budgetapp.viewmodels.BudgetMonthViewModel;

public class MainActivity extends AppCompatActivity implements IBudgetDialogListener, IMonthYearPickerDialogListener {

    private ActivityMainBinding binding;
    private BudgetMonthViewModel budgetMonthViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        budgetMonthViewModel = new ViewModelProvider(this).get(BudgetMonthViewModel.class);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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

        //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController);
        NavigationUI.setupWithNavController(binding.navigationView, navController);


    }


    // send input from EditBudgetDialog to viewModel
    @Override
    public void setNewBudget(String budgetInput) {
        budgetMonthViewModel.setBudget(budgetInput);
    }

    // send input from MonthYearPickerDialog to viewModel
    @Override
    public void loadMonth(int year, int month) {
        // TODO: create method in viewModel
        budgetMonthViewModel.setCurrentMonth(year, month);
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

} // end MainActivity class