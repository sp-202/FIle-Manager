package com.example.fileexplorer.search_view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;

import com.example.fileexplorer.databinding.ActivitySerachViewBinding;

import java.util.Objects;

public class SearchView extends AppCompatActivity {
    ActivitySerachViewBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySerachViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.searchView.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

        setSupportActionBar(binding.toolbarSearchView);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);



    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            this.finish();
            imm.hideSoftInputFromWindow(binding.searchView.getWindowToken(), 0);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}