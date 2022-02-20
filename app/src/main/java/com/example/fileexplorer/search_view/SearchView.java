package com.example.fileexplorer.search_view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.example.fileexplorer.MainActivity;
import com.example.fileexplorer.R;
import com.example.fileexplorer.databinding.ActivitySerachViewBinding;
import com.example.fileexplorer.dynamic_tabs.DynamicActivity;

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

        binding.buttonNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchView.this, DynamicActivity.class);
                startActivity(intent);
            }
        });

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