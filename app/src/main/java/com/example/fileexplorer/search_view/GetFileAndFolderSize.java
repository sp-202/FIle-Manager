package com.example.fileexplorer.search_view;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.StatFs;
import android.provider.MediaStore;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.StreamSupport;

public class GetFileAndFolderSize {

    private static final String TAG = "myApp100";

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

    public long FileSize1(String path) throws IOException {
        Path dir = Paths.get(path);
        AtomicLong size = new AtomicLong(0);
        Iterable<File> files = com.google.common.io.Files.fileTraverser().breadthFirst(dir.toFile());
        size.addAndGet(StreamSupport.stream(files.spliterator(), false).filter(File::isFile).mapToLong(File::length).sum());
        return size.longValue();
    }

    public long FileSize2(String path) throws IOException {
        Path dir = Paths.get(path);
        long size = 0;
        StatFs statFs = new StatFs(path);
        size += statFs.getBlockSizeLong();
        return size;
    }

    public long getFolderSize(Context context, File file) {
        File directory = readlink(file); // resolve symlinks to internal storage
        String path = directory.getAbsolutePath();
        Cursor cursor = null;
        int count = 0;
        long size = 0;
        try {
            cursor = context.getContentResolver().query(MediaStore.Files.getContentUri("external"),
                    new String[]{MediaStore.MediaColumns.SIZE},
                    MediaStore.MediaColumns.DATA + " LIKE ?",
                    new String[]{path + "/%"},
                    null);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    size += cursor.getLong(0);
                    Log.d(TAG, "getFolderSize: " + cursor.getColumnCount());
                    count++;
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        Log.d(TAG, "getFolderSize: " + count);

        return size;
    }

    public static File readlink(File file) {
        File f;
        try {
            f = file.getCanonicalFile();
        } catch (IOException e) {
            return file;
        }
        if (f.getAbsolutePath().equals(file.getAbsolutePath())) {
            return f;
        }
        return readlink(f);
    }

    public int getFolderSize123(Context context, File file) {
        int contFilesInFolder = 0;
        Cursor allFiles = null;
        File directory = readlink(file); // resolve symlinks to internal storage
        String path = directory.getAbsolutePath();
        Uri uri = MediaStore.Files.getContentUri("external");
        ContentResolver resolver = context.getContentResolver();
        String[] projection = null;
        String selection = MediaStore.MediaColumns.SIZE + " ="
                + MediaStore.Files.FileColumns.MEDIA_TYPE_NONE;
        String[] selectionArgs = new String[]{path + "/%"};
        Log.d(TAG, "getFolderSize123: " + selection);
        Log.d(TAG, "getFolderSize123: " + Arrays.toString(selectionArgs));
//        String[] sortOrder = null;
//        try {
//            allFiles = resolver.query(uri, null, selection, selectionArgs, null);
//            if (allFiles != null && allFiles.moveToFirst()) {
//                do {
//                    contFilesInFolder += allFiles.getLong(0);
//                } while (allFiles.moveToNext());
//            }
//        } catch (Exception e){
//            Log.d(TAG, "getFolderSize123: "+ e.toString());
//            assert allFiles != null;
//            Log.d(TAG, "getFolderSize123: " + allFiles.getCount());
//        }

//        finally {
//            if (allFiles != null) {
//                allFiles.close();
//            }
//        }

        return contFilesInFolder;
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

    public HashSet<String> FileListInFolderQuery(Context context, String path) {
        HashSet<String> listOfFiles = new HashSet<>();
        Cursor cursor = context
                .getContentResolver().query(MediaStore.Files.getContentUri("external"),
                        new String[]{MediaStore.MediaColumns.DISPLAY_NAME},
                        MediaStore.MediaColumns.DATA + " LIKE ?", new String[]{path + "/%"}, null);
        try {
            cursor.moveToFirst();
            do {
                listOfFiles.add(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DISPLAY_NAME)));

            } while (cursor.moveToNext());
            cursor.close();
        } catch (Exception e) {
            Log.d(TAG, "mediaListInDevice: " + e.toString());
        }
        Log.d(TAG, "mediaListInDevice: " + listOfFiles);
        return listOfFiles;
    }

    public HashSet<String> FileListInFolderQuery123(Context context, String path) {
        HashSet<String> listOfFiles = new HashSet<>();
        Cursor cursor = context
                .getContentResolver().query(MediaStore.Files.getContentUri("external"),
                        new String[]{MediaStore.MediaColumns.RELATIVE_PATH},
                        MediaStore.MediaColumns.DATA + " LIKE ?", new String[]{path + "/%"}, null);
        try {
            cursor.moveToFirst();
            do {
                listOfFiles.add(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.RELATIVE_PATH)));

            } while (cursor.moveToNext());
            cursor.close();
        } catch (Exception e) {
            Log.d(TAG, "mediaListInDevice: " + e.toString());
        }
        Log.d(TAG, "mediaListInDevice: " + listOfFiles);
        return listOfFiles;
    }


    // To return all files in a specific folder
    public ArrayList<FileListModel> FileListInFolder(Context context, String path) {
        ArrayList<FileListModel> listOfFiles = new ArrayList<>();
        Cursor cursor = context
                .getContentResolver().query(MediaStore.Files.getContentUri("external"),
                        new String[]{MediaStore.MediaColumns.DISPLAY_NAME,
                                MediaStore.MediaColumns.DATA,
                                MediaStore.MediaColumns.SIZE,
                                MediaStore.MediaColumns.DATE_MODIFIED},
                        MediaStore.MediaColumns.DATA + " LIKE ?", new String[]{path + "/%"}, null);
        try {
            cursor.moveToFirst();
            do {
//                String relativePath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATA));
//                Path path1 = Paths.get(relativePath);
//                if (!path1.toFile().isDirectory()) {
//                    String fileName = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DISPLAY_NAME));
//                    String filePath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATA));
//                    String fileSize = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.SIZE));
//                    String modified = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATE_MODIFIED));
//                    listOfFiles.add(new FileListModel(fileName, filePath, fileSize, modified));
//                }
                String fileName = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DISPLAY_NAME));
                String filePath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATA));
                String fileSize = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.SIZE));
                String modified = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATE_MODIFIED));
                listOfFiles.add(new FileListModel(fileName, filePath, fileSize, modified));
            } while (cursor.moveToNext());
            cursor.close();
        } catch (Exception e) {
            Log.d(TAG, "mediaListInDevice: " + e.toString());
        }
        Log.d(TAG, "mediaListInDevice: " + listOfFiles);
        return listOfFiles;
    }


    /*
    @ To return all folder in a folder
     */
    public ArrayList<FolderListProvider> FolderListInFolder(Context context, String path) {
        ArrayList<FolderListProvider> listOfFiles = new ArrayList<>();
        Cursor cursor = context
                .getContentResolver().query(MediaStore.Files.getContentUri("external"),
                        new String[]{MediaStore.MediaColumns.DISPLAY_NAME,
                                MediaStore.MediaColumns.DATA,
                                MediaStore.MediaColumns.DATE_MODIFIED},
                        MediaStore.MediaColumns.DATA + " LIKE ?", new String[]{path + "/%"}, null);
        try {
            cursor.moveToFirst();
            do {
                String relativePath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATA));
                Path path1 = Paths.get(relativePath);
                if (path1.toFile().isDirectory()) {
                    String fileName = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DISPLAY_NAME));
                    String filePath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATA));
                    String modified = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATE_MODIFIED));
                    listOfFiles.add(new FolderListProvider(fileName, filePath, modified));
                }

            } while (cursor.moveToNext());
            cursor.close();
        } catch (Exception e) {
            Log.d(TAG, "mediaListInDevice: " + e.toString());
        }
        Log.d(TAG, "mediaListInDevice: " + listOfFiles);
        return listOfFiles;
    }
}
