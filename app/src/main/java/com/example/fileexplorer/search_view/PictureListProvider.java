package com.example.fileexplorer.search_view;

public class PictureListProvider {
    private String ImgName;
    private String ImgPath;
    private long ImgSize;
    private long ModificationDate;

    public String getImgName() {
        return ImgName;
    }

    public void setImgName(String imgName) {
        ImgName = imgName;
    }

    public String getImgPath() {
        return ImgPath;
    }

    public void setImgPath(String imgPath) {
        ImgPath = imgPath;
    }

    public long getImgSize() {
        return ImgSize;
    }

    public void setImgSize(long imgSize) {
        ImgSize = imgSize;
    }

    public long getModificationDate() {
        return ModificationDate;
    }

    public void setModificationDate(long modificationDate) {
        ModificationDate = modificationDate;
    }

    public PictureListProvider(String imgName, String imgPath, long imgSize, long modificationDate) {
        ImgName = imgName;
        ImgPath = imgPath;
        ImgSize = imgSize;
        ModificationDate = modificationDate;
    }
}
