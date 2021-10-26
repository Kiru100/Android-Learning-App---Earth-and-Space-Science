package com.example.capstone.Model;

public class ChapterInfo extends Student {
    private String ChapterName;
    private String ImageFile;
    private int ChapterNumber;

    public ChapterInfo(){

    }

    public ChapterInfo(String chapterName, String imageFile,int ChapterNumber) {
       this.ChapterName = chapterName;
        this.ImageFile = imageFile;
        this.ChapterNumber=ChapterNumber;
    }

    public String getChapterName() {
        return ChapterName;
    }

    public void setChapterName(String chapterName) {
        ChapterName = chapterName;
    }


    public String getImageFile() {
        return ImageFile;
    }

    public void setImageFile(String imageFile) {
        ImageFile = imageFile;
    }

    public int getChapterNumber() {
        return ChapterNumber;
    }

    public void setChapterNumber(int chapterNumber) {
        ChapterNumber = chapterNumber;
    }
}
