package com.example.fileexplorer.search_view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.documentfile.provider.DocumentFile;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.example.fileexplorer.databinding.ActivitySerachViewBinding;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class SearchView extends AppCompatActivity {
    private static final String TAG = "myApp10";
    ActivitySerachViewBinding binding;
    HashSet<String> videoHashList = new HashSet<>();
    ArrayList<FileListModel> fileHashList = new ArrayList<>();
    String[] porjection = {
            MediaStore.Video.VideoColumns.DATA, MediaStore.Video.Media.DISPLAY_NAME
    };

    String[] projection = {
            MediaStore.Files.FileColumns.DATA, MediaStore.Files.FileColumns.DISPLAY_NAME
    };
    @SuppressLint("SdCardPath")
    String path = "/sdcard/Android/media/com.whatsapp/WhatsApp/Media/.Statuses";

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

        ExecuteTask12();

    }

    private void ExecuteTask12() {
        Executor executors = Executors.newFixedThreadPool(1);
        Handler handler = new Handler(Looper.getMainLooper());
        executors.execute(new Runnable() {
            @Override
            public void run() {
//                mediaListInDevice();
                long start = System.currentTimeMillis();
                FileListInFolderQuery();
                mediaQuery();
                long end = System.currentTimeMillis();
                Log.d(TAG, "run: " + (end - start));
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

    public void mediaListInDevice() {
        Cursor cursor = getApplicationContext()
                .getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                        porjection,
                        null, null, null);
        try {
            cursor.moveToFirst();
            do {
                videoHashList.add(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)));

            } while (cursor.moveToNext());
            cursor.close();
        } catch (Exception e) {
            Log.d(TAG, "mediaListInDevice: " + e.toString());
        }
        Log.d(TAG, "mediaListInDevice: " + videoHashList);
    }

    public void FileListInFolderQuery() {
        Cursor cursor = getApplicationContext()
                .getContentResolver().query(MediaStore.Files.getContentUri(MediaStore.VOLUME_INTERNAL),
                        null,
                        MediaStore.Files.FileColumns.DATA + " LIKE ? ", new String[]{path +"%/"}, null);
        Log.d(TAG, "FileListInFolderQuery: " + cursor.getCount());
        try {
            cursor.moveToFirst();
            do {

                String fileName = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DISPLAY_NAME));
                String filePath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATA));
                String fileSize = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.SIZE));
                String modified = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATE_MODIFIED));
                fileHashList.add(new FileListModel(fileName, filePath, fileSize, modified));

            } while (cursor.moveToNext());
            cursor.close();
        } catch (Exception e) {
            Log.d(TAG, "mediaListInDevice: " + e.toString());
        }
        for (int i = 0; i < fileHashList.size(); i++) {
            Log.d(TAG, "FileListInFolderQuery: " + fileHashList.get(i).getRelativePath());
        }

    }


    @SuppressLint("Range")
    public void mediaQuery() {
        ArrayList<String> list = new ArrayList<>();
        String hiddenFiles;
        try {
            String nonMediaCondition = MediaStore.Files.FileColumns.MEDIA_TYPE + " = "
                    + MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;
            String where = nonMediaCondition + " LIKE ? ";
            String[] params = new String[]{path + "/%"};
            Cursor cursor = getApplicationContext().getContentResolver().query(MediaStore.Files.getContentUri("external"),
                    new String[]{MediaStore.Files.FileColumns.DATA},
                    where, params, null);
            Log.d(TAG, "cursor count: " + cursor.getCount());
//            Toast.makeText(getApplicationContext(), cursor.getColumnCount(), Toast.LENGTH_SHORT).show();
//            Toast.makeText(getApplicationContext(), cursor.getCount(), Toast.LENGTH_SHORT).show();
            while (cursor.moveToNext()) {
                hiddenFiles = cursor.getString(cursor.getColumnIndex(MediaStore.Files.FileColumns.DATA));
                Log.d(TAG, "hidden files : " + hiddenFiles);
                if (hiddenFiles != null)
                    list.add(hiddenFiles);
            }
            cursor.close();
        } catch (Exception e) {
            Log.d(TAG, "mediaQuery: " + e.toString());
        }
        Log.d(TAG, "mediaQuery: " + list.size());
    }
}