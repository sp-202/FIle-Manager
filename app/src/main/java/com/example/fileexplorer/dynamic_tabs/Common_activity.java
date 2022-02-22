package com.example.fileexplorer.dynamic_tabs;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fileexplorer.databinding.ActivityCommonBinding;

import java.io.File;
import java.util.Stack;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Common_activity extends AppCompatActivity {

    private static final String TAG = "myApp458";
    ActivityCommonBinding binding;



    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCommonBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }
}