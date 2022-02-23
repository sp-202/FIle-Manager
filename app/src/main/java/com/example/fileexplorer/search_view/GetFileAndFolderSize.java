package com.example.fileexplorer.search_view;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.concurrent.atomic.AtomicLong;

public class GetFileAndFolderSize {

    @RequiresApi(api = Build.VERSION_CODES.O)
    public long FileSize(String path) throws IOException {
        Path dir = Paths.get(path);
        AtomicLong size = new AtomicLong(0);
        Files.walkFileTree(dir, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                size.addAndGet(attrs.size());
                return FileVisitResult.CONTINUE;
            }
        });
        return size.longValue();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public long countFolders(String path) throws IOException {
        long count = 0;
        DirectoryStream<Path> directoryStream;

        directoryStream = Files.newDirectoryStream(Paths.get(path));
        for (Path path1 : directoryStream) {
            if (path1.toFile().isDirectory()) {
                count++;
                count += countFolders(path1.toString());
            }
        }
        return count;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public long countFiles(String path) throws IOException {
        long count = 0;
        DirectoryStream<Path> directoryStream;

        directoryStream = Files.newDirectoryStream(Paths.get(path));
        for (Path path1 : directoryStream) {
            if (path1.toFile().isDirectory()) {
                count += countFiles(path1.toString());
            } else count++;
        }
        return count;
    }
}
