package com.example.fileexplorer.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fileexplorer.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Internal_fragment extends Fragment {
    private static final String TAG = "myApp123";
    View view;
    private RecyclerView recyclerView;
    private List<File> fileList;
    private ImageView img_back;
    private TextView tv_path_holder;
    File storage;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_internal, container, false);

        tv_path_holder = view.findViewById(R.id.tv_path_folder);
        img_back = view.findViewById(R.id.img_back);


        String internalStorage = requireActivity().getIntent().getStringExtra("path");
        storage = new File(internalStorage);
        Log.d(TAG, "onCreateView: "+storage);

        tv_path_holder.setText(storage.getAbsolutePath());
        runtimePermission();
        return view;
    }

    public ArrayList<File> findFiles(File file) {
        ArrayList<File> arrayList = new ArrayList<>();
        File[] files = file.listFiles();
        for (File singleFiles : files) {
            if (singleFiles.isDirectory() && !singleFiles.isHidden()) {
                arrayList.add(singleFiles);
            }
        }
        for (File singleFile : files) {
            if (singleFile.getName().toLowerCase().endsWith(".jpeg") ||
                    singleFile.getName().toLowerCase().endsWith(".png") ||
                    singleFile.getName().toLowerCase().endsWith(".jpg") ||
                    singleFile.getName().toLowerCase().endsWith(".rar") ||
                    singleFile.getName().toLowerCase().endsWith(".pdf") ||
                    singleFile.getName().toLowerCase().endsWith(".txt") ||
                    singleFile.getName().toLowerCase().endsWith(".html") ||
                    singleFile.getName().toLowerCase().endsWith(".docx") ||
                    singleFile.getName().toLowerCase().endsWith(".xlsx") ||
                    singleFile.getName().toLowerCase().endsWith(".csv") ||
                    singleFile.getName().toLowerCase().endsWith(".apk") ||
                    singleFile.getName().toLowerCase().endsWith(".mp3") ||
                    singleFile.getName().toLowerCase().endsWith(".wav") ||
                    singleFile.getName().toLowerCase().endsWith(".mp4")){
                arrayList.add(singleFile);
            }
        }
        return arrayList;
    }

    private void runtimePermission() {
        // here ask the run time permission
        displayFiles();
    }

    private void displayFiles(){
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        fileList = new ArrayList<>();
        fileList.addAll(findFiles(storage));
        Log.d(TAG, "displayFiles: " + fileList.size());

    }
}
