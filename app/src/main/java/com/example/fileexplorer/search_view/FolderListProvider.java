package com.example.fileexplorer.search_view;

public class FolderListProvider {
    private String folderName;
    private String folderPath;
    private String folderModified;

    public FolderListProvider(String folderName, String folderPath, String folderModified) {
        this.folderName = folderName;
        this.folderPath = folderPath;
        this.folderModified = folderModified;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public String getFolderPath() {
        return folderPath;
    }

    public void setFolderPath(String folderPath) {
        this.folderPath = folderPath;
    }

    public String getFolderModified() {
        return folderModified;
    }

    public void setFolderModified(String folderModified) {
        this.folderModified = folderModified;
    }
}
