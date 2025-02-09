package com.example.recetas;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    private NavController navController; // Declaramos la variable global

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Configurar el BottomNavigationView con el NavController
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        NavHostFragment navHostFragment = (NavHostFragment)
                getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);

        if (navHostFragment != null) {
            navController = navHostFragment.getNavController();
            NavigationUI.setupWithNavController(bottomNavigationView, navController);
        }

        // Listener personalizado para el BottomNavigationView:
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (navController == null) return false;
                int id = item.getItemId();
                if (id == R.id.listaRecetasFragment) {
                    // Navegar a la lista y limpiar la pila hasta llegar a ese fragmento:
                    NavOptions options = new NavOptions.Builder()
                            .setPopUpTo(R.id.listaRecetasFragment, true)
                            .build();
                    navController.navigate(R.id.listaRecetasFragment, null, options);
                    return true;
                } else if (id == R.id.buscarRecetasFragment) {
                    navController.navigate(R.id.buscarRecetasFragment);
                    return true;
                }
                return false;
            }
        });

        FloatingActionButton fabAddReceta = findViewById(R.id.fab_add_receta);
        fabAddReceta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.nav_host_fragment);
                if (navHostFragment != null) {
                    NavController navController = navHostFragment.getNavController();
                    navController.navigate(R.id.anadirRecetaFragment);
                }
            }
        });
    }
}
