package com.example.fileexplorer.dynamic_tabs;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.fileexplorer.R;
import com.example.fileexplorer.databinding.ActivityCommonBinding;
import com.example.fileexplorer.search_view.FileListModel;
import com.example.fileexplorer.search_view.FolderListProvider;
import com.example.fileexplorer.search_view.GetFileAndFolderSize;
import com.example.fileexplorer.util.FileList_provider;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Common_activity extends AppCompatActivity {

    private static final String TAG = "myApp458";
    ActivityCommonBinding binding;
    public ArrayList<FolderListProvider> folderArrayList;
    public ArrayList<FileListModel> fileArrayList;
    GetFileAndFolderSize fileAndFolderSize;
    String path;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCommonBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        fileAndFolderSize = new GetFileAndFolderSize();
        Toolbar toolbar = findViewById(R.id.common_activity_toolBar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        path = getIntent().getStringExtra("path_from");
        Path p = Paths.get(path);
        if (p.toFile().canRead() && p.toFile().canWrite()) {
            binding.nothingTextView.setVisibility(View.GONE);
            executeFileFolderTask();
        } else {
            Toast.makeText(getApplicationContext(), "Can not open", Toast.LENGTH_SHORT).show();
            binding.nothingTextView.setVisibility(View.VISIBLE);
            binding.commonActivityRecyclerView.setVisibility(View.GONE);
        }
    }


    private void executeFileFolderTask() {
        ExecutorService executor = Executors.newFixedThreadPool(1);
        Handler handler = new Handler(Looper.getMainLooper());
        executor.execute(() -> {
            binding.progressBar.setVisibility(View.VISIBLE);
            binding.commonActivityRecyclerView.setVisibility(View.GONE);
            folderArrayList = fileAndFolderSize.FolderListInFolder(getApplicationContext(), path);
            fileArrayList = fileAndFolderSize.FileListInFolder(getApplicationContext(), path);
            Log.d(TAG, "executeFileFolderTask: " + fileArrayList.size());
            handler.post(() -> {
                binding.progressBar.setVisibility(View.GONE);
                binding.commonActivityRecyclerView.setVisibility(View.VISIBLE);
                binding.commonActivityRecyclerView.setLayoutManager(new LinearLayoutManager(this));
                binding.commonActivityRecyclerView.setAdapter(new Common_adapter(this, fileArrayList, folderArrayList));
            });
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.common_menu_bar, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.c_activity_selctAll:
                Toast.makeText(getApplicationContext(), "Memory View", Toast.LENGTH_SHORT).show();
                break;
            case R.id.c_activity_addNewFolder:
                Toast.makeText(getApplicationContext(), "New folder", Toast.LENGTH_SHORT).show();
                break;
            case R.id.c_activity_sortBy:
                Toast.makeText(getApplicationContext(), "Sort by", Toast.LENGTH_SHORT).show();
                break;
            case R.id.grid_view:
                Toast.makeText(getApplicationContext(), "Grid view", Toast.LENGTH_SHORT).show();
                break;
            case android.R.id.home:
                this.finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}