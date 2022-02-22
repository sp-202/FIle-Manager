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

    public static String path1;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCommonBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent intent = getIntent();
        if (intent.hasExtra("pic_folder")) {

        } else if (intent.hasExtra("download_path")) {
            path1 = intent.getStringExtra("download_path");
        }

//        new Common_activity.InitTask().execute(this);
        ExecuteTask();
    }

    public void ExecuteTask(){
        File file = new File(path1);

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executor.execute(() -> {
            // Background work here
            long countFile = 0;
            Stack<File> s = new Stack<>();
            s.push(file);
            while (!s.empty()) {
                File tempFile = s.pop();
                if (tempFile.isFile()) {
                    countFile++;
                } else if (tempFile.isDirectory()) {
                    File[] f = tempFile.listFiles();
                    for (File fpp : f) {
                        s.push(fpp);
                    }
                }
            }

            long finalCountFile = countFile;
            handler.post(new Runnable() {
                @Override
                public void run() {
                    // UI work here
                    binding.fileItemCount.setText(String.valueOf(finalCountFile));
                }
            });
        });
    }

}