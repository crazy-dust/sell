package com.demotwo.sell.dto;

import java.io.InputStream;

public class ImageHolder {

    private InputStream FileInputStream;
    private String FileName;

    public ImageHolder() {
    }

    public ImageHolder(InputStream fileInputStream, String fileName) {
        FileInputStream = fileInputStream;
        FileName = fileName;
    }

    public InputStream getFileInputStream() {
        return FileInputStream;
    }

    public void setFileInputStream(InputStream fileInputStream) {
        FileInputStream = fileInputStream;
    }

    public String getFileName() {
        return FileName;
    }

    public void setFileName(String fileName) {
        FileName = fileName;
    }
}
