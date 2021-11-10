package com.example.capstone.Model;

public class ChapterInfo extends Student {
    private String ChapterName;
    private String ImageFile;
    private int ChapterNumber;
    private String LessonChapterImageURl;

    public ChapterInfo(){

    }

    public ChapterInfo(String chapterName, String imageFile, int ChapterNumber, String lessonChapterImageURl) {
       this.ChapterName = chapterName;
        this.ImageFile = imageFile;
        this.ChapterNumber=ChapterNumber;
        LessonChapterImageURl = lessonChapterImageURl;
    }

    public String getChapterName() {
        return ChapterName;
    }

    public void setChapterName(String chapterName) {
        ChapterName = chapterName;
    }

    public String getLessonChapterImageURl() {
        return LessonChapterImageURl;
    }

    public void setLessonChapterImageURl(String lessonChapterImageURl) {
        LessonChapterImageURl = lessonChapterImageURl;
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
