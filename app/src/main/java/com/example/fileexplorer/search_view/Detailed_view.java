package com.example.fileexplorer.search_view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.fileexplorer.databinding.ActivityDetailedViewBinding;

public class Detailed_view extends AppCompatActivity {

    ActivityDetailedViewBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding  = ActivityDetailedViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


    }
}