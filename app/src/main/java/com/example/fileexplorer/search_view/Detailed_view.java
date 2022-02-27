package com.example.fileexplorer.search_view;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.fileexplorer.R;
import com.example.fileexplorer.databinding.ActivityDetailedViewBinding;
import com.example.fileexplorer.util.DataHolder;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Detailed_view extends AppCompatActivity {

    private static final String TAG = "myApp400";
    ActivityDetailedViewBinding binding;
    public static String path1;
    GetFileAndFolderSize getFileAndFolderSize = new GetFileAndFolderSize();

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailedViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = findViewById(R.id.detailed_page_titleBar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");


        path1 = DataHolder.getInstance().getData();
        Log.d(TAG, "onCreate: " + path1);
        Path dir = Paths.get(path1);
        Date lastModified = new Date(dir.toFile().lastModified());
        @SuppressLint("SimpleDateFormat")
        Format f = new SimpleDateFormat("dd MMMM yy");
        @SuppressLint("SimpleDateFormat")
        Format f1 = new SimpleDateFormat("HH:mm aa");
        String date = f.format(lastModified);
        String timeStamp = f1.format(lastModified);
        binding.detailedPageFolderPath.setText(path1);
        binding.folderNameTextDetailed.setText(dir.toFile().getName());
        binding.detailedPageModifiedDetail.setText("Modified " + date + ", " + timeStamp);
        if (dir.toFile().isDirectory()) {
            ExecuteTask();
            binding.detailedPageFolderInfo.setText("0  folders");
            binding.detailedPageFileInfo.setText("0  files");
            binding.detailedPageSizeOfFiles.setText("0  B");
        } else {
            if (dir.toFile().toString().toLowerCase().endsWith(".pdf")) {
                binding.detailedViewFileIcon.setImageResource(R.drawable.ic_icon_pdf_file);
                binding.detailedPageImgFolderOrFile.setImageResource(R.drawable.ic_icon_pdf_file);
            } else if (dir.toFile().toString().toLowerCase().endsWith(".jpg")
                    || dir.toFile().toString().toLowerCase().endsWith(".jpeg")
                    || dir.toFile().toString().toLowerCase().endsWith(".png")) {
                binding.detailedViewFileIcon.setImageResource(R.drawable.ic_twotone_photo_24);
                binding.detailedPageImgFolderOrFile.setImageResource(R.drawable.ic_twotone_photo_24);
            } else if (dir.toFile().toString().toLowerCase().endsWith(".xml")) {
                binding.detailedViewFileIcon.setImageResource(R.drawable.ic_icon_xml_file);
                binding.detailedPageImgFolderOrFile.setImageResource(R.drawable.ic_icon_xml_file);
            } else if (dir.toFile().toString().toLowerCase().endsWith(".js")) {
                binding.detailedViewFileIcon.setImageResource(R.drawable.ic_icon_js_file);
                binding.detailedPageImgFolderOrFile.setImageResource(R.drawable.ic_icon_js_file);
            } else if (dir.toFile().toString().toLowerCase().endsWith(".xlsx")
                    || dir.toFile().toString().toLowerCase().endsWith(".csv")) {
                binding.detailedViewFileIcon.setImageResource(R.drawable.ic_icon_xlsx_file);
                binding.detailedPageImgFolderOrFile.setImageResource(R.drawable.ic_icon_xlsx_file);
            } else if (dir.toFile().toString().toLowerCase().endsWith(".docx")) {
                binding.detailedViewFileIcon.setImageResource(R.drawable.ic_home_icon);
                binding.detailedPageImgFolderOrFile.setImageResource(R.drawable.ic_home_icon);
            } else if (dir.toFile().toString().toLowerCase().endsWith(".ppt")) {
                binding.detailedViewFileIcon.setImageResource(R.drawable.ic_icon_ppt_file);
                binding.detailedPageImgFolderOrFile.setImageResource(R.drawable.ic_icon_ppt_file);
            } else if (dir.toFile().toString().toLowerCase().endsWith(".c")
                    || dir.toFile().toString().toLowerCase().endsWith(".cpp")
                    || dir.toFile().toString().toLowerCase().endsWith(".h")
                    || dir.toFile().toString().toLowerCase().endsWith(".c++")
                    || dir.toFile().toString().toLowerCase().endsWith(".cxx")) {
                binding.detailedViewFileIcon.setImageResource(R.drawable.ic_icon_cpp_file);
                binding.detailedPageImgFolderOrFile.setImageResource(R.drawable.ic_icon_cpp_file);
            } else if (dir.toFile().toString().toLowerCase().endsWith(".html")) {
                binding.detailedViewFileIcon.setImageResource(R.drawable.ic_icon_html_2_file);
                binding.detailedPageImgFolderOrFile.setImageResource(R.drawable.ic_icon_html_2_file);
            } else if (dir.toFile().toString().toLowerCase().endsWith(".css")) {
                binding.detailedViewFileIcon.setImageResource(R.drawable.ic_icon_css_file);
                binding.detailedPageImgFolderOrFile.setImageResource(R.drawable.ic_icon_css_file);
            } else if (dir.toFile().toString().toLowerCase().endsWith(".rar")
                    || dir.toFile().toString().toLowerCase().endsWith(".zip")) {
                binding.detailedViewFileIcon.setImageResource(R.drawable.ic_icon_zip_folder);
                binding.detailedPageImgFolderOrFile.setImageResource(R.drawable.ic_icon_zip_folder);
            } else if (dir.toFile().toString().toLowerCase().endsWith(".apk")) {
                binding.detailedViewFileIcon.setImageResource(R.drawable.ic_baseline_android_24);
                binding.detailedPageImgFolderOrFile.setImageResource(R.drawable.ic_baseline_android_24);
            } else {
                binding.detailedViewFileIcon.setImageResource(R.drawable.ic_file_icon);
                binding.detailedPageImgFolderOrFile.setImageResource(R.drawable.ic_file_icon);
            }
            ExecuteFileInfo();
            binding.detailedPageFolderInfo.setVisibility(View.GONE);
            binding.detailedPageFileInfo.setVisibility(View.GONE);
            binding.detailedPageSizeOfFiles.setVisibility(View.VISIBLE);
        }

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
            try {
                size = getFileAndFolderSize.FileSize(path1);
            } catch (IOException e) {
                e.printStackTrace();
            }

            long finalSize = size;
            handler.post(() -> {
                fileSize_calculation(finalSize);
            });
        });
        executor.execute(() -> {
            long count = 0;
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
            try {
                count = getFileAndFolderSize.countFiles(path1);
            } catch (IOException e) {
                e.printStackTrace();
            }

            long finalCount = count;
            handler3.post(() -> binding.detailedPageFileInfo.setText(finalCount + "  files"));
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void ExecuteFileInfo() {
        ExecutorService executor = Executors.newFixedThreadPool(1);
        Handler handler = new Handler(Looper.getMainLooper());
        executor.execute(() -> {
            long fileSize = 0;
            try {
                fileSize = getFileAndFolderSize.FileSize(path1);
            } catch (IOException e) {
                e.printStackTrace();
            }

            long finalSize = fileSize;
            handler.post(() -> fileSize_calculation(finalSize));
        });
    }

    @SuppressLint("SetTextI18n")
    private void fileSize_calculation(long finalSize) {
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
    }

}