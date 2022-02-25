package com.example.fileexplorer.util;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.TreeSet;

public class FileList_provider {
    public TreeSet<String> Downloads(String downloadPath) throws IOException {
        TreeSet<String> FileName = new TreeSet<>();
        DirectoryStream<Path> directoryStream;
        directoryStream = Files.newDirectoryStream(Paths.get(downloadPath));
        for (Path path1 : directoryStream){
            FileName.add(path1.getFileName().toString());
        }
        return FileName;
    }

    public TreeSet<Path> countVideoFiles(String path) throws IOException {
        TreeSet<Path> FileName = new TreeSet<>();
        DirectoryStream<Path> directoryStream;

        directoryStream = Files.newDirectoryStream(Paths.get(path));
        for (Path path1 : directoryStream) {
            if (path1.toFile().isFile()){
                if (path1.toFile().toString().toLowerCase().endsWith(".mp4") ||
                        path1.toFile().toString().toLowerCase().endsWith(".avi") ||
                        path1.toFile().toString().toLowerCase().endsWith(".gif")){
                    FileName.add(path1);
                }
            }
        }
        return FileName;
    }
}
