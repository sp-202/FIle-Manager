package com.example.fileexplorer.search_view;

public class FileListModel {
    private String fileName;
    private String relativePath;
    private String fileSize;
    private String dateOfModified;

    public FileListModel(String fileName, String relativePath, String fileSize, String dateOfModified) {
        this.fileName = fileName;
        this.relativePath = relativePath;
        this.fileSize = fileSize;
        this.dateOfModified = dateOfModified;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getRelativePath() {
        return relativePath;
    }

    public void setRelativePath(String relativePath) {
        this.relativePath = relativePath;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getDateOfModified() {
        return dateOfModified;
    }

    public void setDateOfModified(String dateOfModified) {
        this.dateOfModified = dateOfModified;
    }
}
