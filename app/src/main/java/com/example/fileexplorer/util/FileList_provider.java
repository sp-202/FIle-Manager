package com.example.fileexplorer.util;

import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.TreeSet;

public class FileList_provider {
    private static final String TAG = "myApp12344";

    public ArrayList<File> Downloads(String downloadPath) throws IOException {
        ArrayList<File> FileName = new ArrayList<>();
        ArrayList<File> FolderName = new ArrayList<>();
        DirectoryStream<Path> directoryStream;
        directoryStream = Files.newDirectoryStream(Paths.get(downloadPath));
        for (Path path1 : directoryStream) {
            if (path1.toFile().isDirectory())
                FolderName.add(path1.toFile());
            else FileName.add(path1.toFile());
        }

        FolderName.addAll(FileName);
        Log.d(TAG, "Downloads: " + FolderName);
        return FolderName;
    }

    public TreeSet<Path> countVideoFiles(String path) throws IOException {
        TreeSet<Path> FileName = new TreeSet<>();
        DirectoryStream<Path> directoryStream;

        directoryStream = Files.newDirectoryStream(Paths.get(path));
        for (Path path1 : directoryStream) {
            if (path1.toFile().isFile()) {
                if (path1.toFile().toString().toLowerCase().endsWith(".mp4") ||
                        path1.toFile().toString().toLowerCase().endsWith(".avi") ||
                        path1.toFile().toString().toLowerCase().endsWith(".gif")) {
                    FileName.add(path1);
                }
            }
        }
        return FileName;
    }
}
