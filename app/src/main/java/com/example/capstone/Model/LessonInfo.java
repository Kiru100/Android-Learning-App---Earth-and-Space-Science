package com.example.capstone.Model;

public class LessonInfo extends ChapterInfo {
    private int LessonNumber;
    private String LessonName;
    private String Ltypes;
    private String YoutubeURL;


    public LessonInfo(){

    }

    public LessonInfo(int lessonNumber, String lessonName, String ltypes, String youtubeURL) {
        LessonNumber = lessonNumber;
        LessonName = lessonName;
        Ltypes = ltypes;
        YoutubeURL = youtubeURL;
    }

    public int getLessonNumber() {
        return LessonNumber;
    }

    public void setLessonNumber(int lessonNumber) {
        LessonNumber = lessonNumber;
    }

    public String getLessonName() {
        return LessonName;
    }

    public void setLessonName(String lessonName) {
        LessonName = lessonName;
    }

    public String getLtypes() {
        return Ltypes;
    }

    public void setLtypes(String ltypes) {
        Ltypes = ltypes;
    }

    public String getYoutubeURL() {
        return YoutubeURL;
    }

    public void setYoutubeURL(String youtubeURL) {
        YoutubeURL = youtubeURL;
    }
}
