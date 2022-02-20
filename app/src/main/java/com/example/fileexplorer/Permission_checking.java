package com.example.fileexplorer;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.view.Window;

import com.example.fileexplorer.databinding.ActivityPermissionCheckingBinding;

import java.io.File;
import java.util.Objects;

public class Permission_checking extends AppCompatActivity {

    private static final String TAG = "myApp";
    ActivityPermissionCheckingBinding binding;
    private static final int REQUEST_CODE = 101;

    @RequiresApi(api = Build.VERSION_CODES.S)
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPermissionCheckingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // To hide the top bar in app
//        Objects.requireNonNull(getSupportActionBar()).hide();
        // To make the status bar color same with Main Activity
        Window window = getWindow();
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.maniPage_back));
        // Declaration of the permission array
        String[] PERMISSIONS = new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
        // Works only for build version is Android 10 or less than that
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
            requestPermissions(PERMISSIONS, REQUEST_CODE);
        }

        // To show this activity for once when the user installs the app for the first time
        SharedPreferences preferences = getSharedPreferences("PREFERENCE", MODE_PRIVATE);
        String FirstTime = preferences.getString("FirstTimeInstall", "");

        if (FirstTime.equals("Yes")) {
            Intent intent = new Intent(Permission_checking.this, MainActivity.class);
            String path = Environment.getExternalStorageDirectory().getPath();
            intent.putExtra("path", path);
            startActivity(intent);
            Log.d(TAG, "onCreate: Triggered and the absolute path is " + path);
            File file = new File("/storage/emulated/0");
            Log.d(TAG, "onCreate: " + Objects.requireNonNull(file.listFiles()).length);
            finishAffinity();
        } else {
            @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = preferences.edit();
            editor.putString("FirstTimeInstall", "Yes");
            editor.apply();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        boolean isPermissionGranted;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            isPermissionGranted = Environment.isExternalStorageManager();
            if (!isPermissionGranted) {
                new AlertDialog.Builder(this)
                        .setTitle("All Files Permission")
                        .setMessage("Due to Android 11 restrictions this app requires all files permission")
                        .setPositiveButton("Allow", (dialogInterface, i) -> takePermission())
                        .setNegativeButton("Deny", (dialogInterface, i) -> finish())
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setCancelable(false)
                        .show();
            } else {
                startApp();
            }
        }
    }

    private void takePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            try {
                Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                intent.addCategory("android.intent.category.DEFAULT");
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivityForResult(intent, 101);
            } catch (Exception e) {
                e.printStackTrace();
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                startActivityForResult(intent, 101);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE) {// If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted. Continue the action or workflow
                // in your app.
//                Toast.makeText(MainActivity.this, "Read external storage permission granted", Toast.LENGTH_SHORT).show();
                startApp();
            } else {
                // Explain to the user that the feature is unavailable because
                // the features requires a permission that the user has denied.
                // At the same time, respect the user's decision. Don't link to
                // system settings in an effort to convince the user to change
                // their decision.
                AlertDialog.Builder builder = new AlertDialog.Builder(Permission_checking.this);
                builder.setMessage(R.string.dialog_message).setTitle(R.string.dialog_title);
                builder.setCancelable(false);
                // set ok button on alert dialog
                builder.setPositiveButton(R.string.dialog_ok, (dialogInterface, i) -> ActivityCompat.requestPermissions(Permission_checking.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE));

                // set cancel on alert dialog
                builder.setNegativeButton(R.string.dialog_cancel, (dialogInterface, i) -> {
                    dialogInterface.dismiss();
                    finish();
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        }
    }

    public void startApp() {
        binding.nextBtn.setOnClickListener(view -> {
            Intent intent = new Intent(Permission_checking.this, MainActivity.class);
            String path = Environment.getExternalStorageDirectory().getPath();
            intent.putExtra("path", path);
            startActivity(intent);
            finishAffinity();
        });
    }
}