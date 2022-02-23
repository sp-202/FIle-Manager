package com.example.fileexplorer.search_view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fileexplorer.databinding.ActivityDetailedViewBinding;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Detailed_view extends AppCompatActivity {

    ActivityDetailedViewBinding binding;
    public static String path1;

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailedViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        if (intent.hasExtra("pic_folder")) {

        } else if (intent.hasExtra("download_path")) {
            path1 = intent.getStringExtra("download_path");
        }

        binding.detailedPageFolderInfo.setText("0  folders");
        binding.detailedPageFileInfo.setText("0  files");
        binding.detailedPageSizeOfFiles.setText("0  B");
        ExecuteTask();

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    public void ExecuteTask() {
        ExecutorService executor = Executors.newFixedThreadPool(3);
        Handler handler = new Handler(Looper.getMainLooper());
        Handler handler2 = new Handler(Looper.getMainLooper());
        Handler handler3 = new Handler(Looper.getMainLooper());
        executor.execute(() -> {
            // Background work here
            long size = 0;
            GetFileAndFolderSize getFileAndFolderSize = new GetFileAndFolderSize();
            try {
                size = getFileAndFolderSize.FileSize(path1);
            } catch (IOException e) {
                e.printStackTrace();
            }

            long finalSize = size;
            handler.post(() -> {

                DecimalFormat df = new DecimalFormat("#.##");
                if (finalSize >= Math.pow(10, 9)) {
                    binding.detailedPageSizeOfFiles.setText(df.format((finalSize / Math.pow(10, 9))) + "  GB");
                } else if (finalSize >= Math.pow(10, 6)) {
                    binding.detailedPageSizeOfFiles.setText(df.format((finalSize / Math.pow(10, 6))) + "  MB");
                } else if (finalSize >= Math.pow(10, 3)) {
                    binding.detailedPageSizeOfFiles.setText(df.format((finalSize / Math.pow(10, 3))) + "  KB");
                } else {
                    binding.detailedPageSizeOfFiles.setText(finalSize + "  B");
                }
            });
        });
        executor.execute(() -> {
            long count = 0;
            GetFileAndFolderSize getFileAndFolderSize = new GetFileAndFolderSize();
            try {
                count = getFileAndFolderSize.countFolders(path1);
            } catch (IOException e) {
                e.printStackTrace();
            }

            long finalCount = count;
            handler2.post(() -> binding.detailedPageFolderInfo.setText(finalCount + " folders"));
        });

        executor.execute(() -> {
            long count = 0;
            GetFileAndFolderSize getFileAndFolderSize = new GetFileAndFolderSize();
            try {
                count = getFileAndFolderSize.countFiles(path1);
            } catch (IOException e) {
                e.printStackTrace();
            }

            long finalCount = count;
            handler3.post(() -> binding.detailedPageFileInfo.setText(finalCount + "  files"));
        });
    }
}