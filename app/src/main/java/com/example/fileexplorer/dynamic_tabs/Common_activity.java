package com.example.fileexplorer.dynamic_tabs;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.fileexplorer.databinding.ActivityCommonBinding;
import com.example.fileexplorer.util.FileList_provider;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Common_activity extends AppCompatActivity {

    private static final String TAG = "myApp458";
    ActivityCommonBinding binding;
    public ArrayList<File> fileArrayList;
    FileList_provider list_provider;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCommonBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        list_provider = new FileList_provider();
        executeFileFolderTask();
    }

    private void executeFileFolderTask() {
        ExecutorService executor = Executors.newFixedThreadPool(1);
        Handler handler = new Handler(Looper.getMainLooper());
        executor.execute(() -> {
            try {
                fileArrayList = list_provider.Downloads("/storage/emulated/0/Download");
            } catch (IOException e) {
                e.printStackTrace();
                Log.d(TAG, "executeFileFolderTask: " + e);
            }
            binding.progressBar.setVisibility(View.VISIBLE);
            binding.commonActivityRecyclerView.setVisibility(View.GONE);
            handler.post(() -> {
                binding.progressBar.setVisibility(View.GONE);
                binding.commonActivityRecyclerView.setVisibility(View.VISIBLE);
                binding.commonActivityRecyclerView.setLayoutManager(new LinearLayoutManager(this));
                binding.commonActivityRecyclerView.setAdapter(new Common_adapter(this, fileArrayList));
            });
        });
    }
}