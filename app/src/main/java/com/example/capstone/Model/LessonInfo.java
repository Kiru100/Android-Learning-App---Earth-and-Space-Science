package com.example.capstone.Model;

public class LessonInfo extends ChapterInfo {
    private String LessonNumber;
    private String LessonName;
    private String Ltypes;
    private String YoutubeURL;
    private String TestName;


    public LessonInfo(){

    }

    public LessonInfo(String lessonNumber, String lessonName, String ltypes, String youtubeURL, String testName) {
        LessonNumber = lessonNumber;
        LessonName = lessonName;
        Ltypes = ltypes;
        YoutubeURL = youtubeURL;
        TestName = testName;
    }

    public String getLessonNumber() {
        return LessonNumber;
    }

    public String getTestName() {
        return TestName;
    }

    public void setTestName(String testName) {
        TestName = testName;
    }

    public void setLessonNumber(String lessonNumber) {
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
