package com.example.capstone.Model;

public class ChapterInfo extends Student {
    private String ChapterName;
    private String ImageFile;
    private int ChapterNumber;
    private String LessonChapterImageURl;
    private String ChapterDescription;

    public ChapterInfo(){

    }

    public ChapterInfo(String chapterName, String imageFile, int ChapterNumber, String lessonChapterImageURl, String chapterDescription) {
       this.ChapterName = chapterName;
        this.ImageFile = imageFile;
        this.ChapterNumber=ChapterNumber;
        this.LessonChapterImageURl = lessonChapterImageURl;
        this.ChapterDescription = chapterDescription;
    }

    public String getChapterDescription() {
        return ChapterDescription;
    }

    public void setChapterDescription(String chapterDescription) {
        ChapterDescription = chapterDescription;
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
