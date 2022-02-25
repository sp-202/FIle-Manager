package com.example.fileexplorer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.pddstudio.highlightjs.HighlightJsView;
import com.pddstudio.highlightjs.models.Language;
import com.pddstudio.highlightjs.models.Theme;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.Objects;

public class FileReaderForAnyFile extends AppCompatActivity {

    private static final String TAG = "myApp436";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_reader_for_any_file);
        Toolbar toolbar = findViewById(R.id.FileReaderViewToolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        String str = "/storage/emulated/0/Download/AndroidManifest.xml";
        String content = onSharedIntent();
        HighlightJsView highlightJsView = findViewById(R.id.file_content_view);
        highlightJsView.setTheme(Theme.GITHUB);
        highlightJsView.setHighlightLanguage(Language.AUTO_DETECT);
        highlightJsView.setSource(content);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.file_reader_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.copy_line) {
            Toast.makeText(getApplicationContext(), "Copy the line", Toast.LENGTH_SHORT).show();
        }

        if (id == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private String onSharedIntent(){
        Intent receiveIntent = getIntent();
        Uri fileUri = receiveIntent.getData();
        String content = null;
        InputStream inputStream = null;
        try {
            inputStream = getContentResolver().openInputStream(fileUri);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (inputStream != null) {
            byte[] buffer = new byte[1024];
            int result = 0;
            content = "";
            while (true) {
                try {
                    if ((result = inputStream.read(buffer)) == -1) break;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                content = content.concat(new String(buffer, 0, result));
            }
        }

//        Log.d(TAG, "onSharedIntent: " + content);
        return content;
    }
}
