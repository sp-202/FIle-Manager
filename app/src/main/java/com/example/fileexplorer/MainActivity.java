package com.example.fileexplorer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.fileexplorer.databinding.ActivityMainBinding;
import com.example.fileexplorer.fragments.Card_fragment;
import com.example.fileexplorer.fragments.Home_fragment;
import com.example.fileexplorer.fragments.Internal_fragment;
import com.example.fileexplorer.search_view.SearchView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    ActivityMainBinding binding;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(binding.getRoot());

        drawerLayout = findViewById(R.id.drawer_layout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        toolbar.setClickable(true);
        setSupportActionBar(toolbar);

        binding.searchBarTitle.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, SearchView.class);
            startActivity(intent);

        });

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Home_fragment()).commit();
        navigationView.setCheckedItem(R.id.nav_home);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                Home_fragment home_fragment = new Home_fragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, home_fragment).addToBackStack(null).commit();
                break;

            case R.id.nav_internal:
                Internal_fragment internal_fragment = new Internal_fragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, internal_fragment).addToBackStack(null).commit();
                break;

            case R.id.nav_sd_card:
                Card_fragment card_fragment = new Card_fragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, card_fragment).addToBackStack(null).commit();
                break;

            case R.id.nav_about:
                Toast.makeText(this, "About", Toast.LENGTH_SHORT).show();
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        getFragmentManager().popBackStackImmediate();
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else
            super.onBackPressed();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.memory_state){
            Toast.makeText(getApplicationContext(), "Memory View", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}