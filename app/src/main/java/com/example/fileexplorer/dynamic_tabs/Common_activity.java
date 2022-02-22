package com.example.fileexplorer.dynamic_tabs;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fileexplorer.databinding.ActivityCommonBinding;

import java.io.File;
import java.util.Stack;

public class Common_activity extends AppCompatActivity {

    private static final String TAG = "myApp458";
    ActivityCommonBinding binding;

    String path1;

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

        new Common_activity.InitTask().execute(this);
    }

    protected class InitTask extends AsyncTask<Context, Integer, Integer> {

        File file = new File(path1);
        long countFile = 0;

        @Override
        protected Integer doInBackground(Context... contexts) {
            Stack<File> s = new Stack<>();
            s.push(file);
            while (!s.empty()) {
                File tempFile = s.pop();
                if (tempFile.isFile()) {
                    countFile++;
                    publishProgress((int) countFile);
                } else if (tempFile.isDirectory()) {
                    File[] f = tempFile.listFiles();
                    for (File fpp : f) {
                        s.push(fpp);
                    }
                }
            }
            return (int) countFile;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            binding.fileItemCount.setText(String.valueOf(values[0]));
        }

    }

    public static int countFiles(File file) {
        int count = 0;
        Stack<File> s = new Stack<>();
        s.push(file);
        while (!s.empty()) {
            File tempFile = s.pop();
            if (tempFile.isFile()) {
                count++;
            } else if (tempFile.isDirectory()) {
                File[] f = tempFile.listFiles();
                for (File fpp : f) {
                    s.push(fpp);
                }
            }
        }
        return count;
    }
}