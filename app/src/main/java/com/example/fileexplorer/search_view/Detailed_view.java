package com.example.fileexplorer.search_view;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.example.fileexplorer.databinding.ActivityDetailedViewBinding;

import java.io.File;
import java.text.DecimalFormat;
import java.util.Stack;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Detailed_view extends AppCompatActivity {

    ActivityDetailedViewBinding binding;
    public static String path1;

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

//        new Common_activity.InitTask().execute(this);
        ExecuteTask();

    }

    @SuppressLint("SetTextI18n")
    public void ExecuteTask() {
        File file = new File(path1);

        ExecutorService executor = Executors.newFixedThreadPool(4);
        Handler handler = new Handler(Looper.getMainLooper());
        Handler handler2 = new Handler(Looper.getMainLooper());
        executor.execute(() -> {
            // Background work here
            long countFile = 0;
            long countFolder = 0;
            Stack<File> s = new Stack<>();
            s.push(file);
            while (!s.empty()) {
                File tempFile = s.pop();
                if (tempFile.isFile()) {
                    countFile++;
                } else if (tempFile.isDirectory()) {
                    File[] f = tempFile.listFiles();
                    countFolder++;

                    for (File fpp : f) {
                        s.push(fpp);
                    }
                }
            }

            long finalCountFile = countFile;
            long finalCountFolder = countFolder;

            handler.post(() -> {
                // UI work here
                binding.detailedPageFolderInfo.setText(finalCountFolder + "  folders");
                binding.detailedPageFileInfo.setText(finalCountFile + "  files");
            });
        });
        executor.execute(() -> {
            // Background work here
            long sizeOf = 0;
            Stack<File> s = new Stack<>();
            s.push(file);
            while (!s.empty()) {
                File tempFile = s.pop();
                if (tempFile.isFile()) {
                    sizeOf += tempFile.length();
                } else if (tempFile.isDirectory()) {
                    File[] f = tempFile.listFiles();
                    for (File fpp : f) {
                        s.push(fpp);
                    }
                }
            }
            long finalSizeOf = sizeOf;
            handler2.post(() -> {
                // UI work here
                DecimalFormat df = new DecimalFormat("#.##");
                if (finalSizeOf >= Math.pow(10, 9)) {
                    binding.detailedPageSizeOfFiles.setText(df.format((finalSizeOf / Math.pow(10, 9))) + "  GB");
                } else if (finalSizeOf >= Math.pow(10, 6)) {
                    binding.detailedPageSizeOfFiles.setText(df.format((finalSizeOf / Math.pow(10, 6))) + "  MB");
                } else if (finalSizeOf >= Math.pow(10, 3)) {
                    binding.detailedPageSizeOfFiles.setText(df.format((finalSizeOf / Math.pow(10, 3))) + "  KB");
                } else {
                    binding.detailedPageSizeOfFiles.setText(finalSizeOf + "  B");
                }
            });

        });
    }

}