package com.d.coronavirustracker.activities;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.d.coronavirustracker.R;
import com.d.coronavirustracker.fragments.CountryFragment;
import com.d.coronavirustracker.fragments.HomeFragment;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class MainActivity extends AppCompatActivity implements ChipNavigationBar.OnItemSelectedListener {

    ChipNavigationBar navigationBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mapping();

        navigationBar.setOnItemSelectedListener(this);

        if (savedInstanceState == null) {
            navigationBar.setItemSelected(R.id.home, true);
        }
    }

    private void mapping() {
        navigationBar = findViewById(R.id.navigation_bar);
    }

    private void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemSelected(int i) {
        if (i == R.id.home) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment(getApplicationContext())).commit();
        }

        if (i == R.id.countries) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CountryFragment(getApplicationContext())).commit();
        }
    }
}
