package com.example.capstone.Model;

public class LessonDoneInfo {
    private String LessonName;
    private String Date;
    private boolean isDone;


    public LessonDoneInfo(){

    }

    public LessonDoneInfo(String lessonName, String date, boolean isDone) {
        this.LessonName = lessonName;
        this.Date = date;
        this.isDone = isDone;
    }

    public String getLessonName() {
        return LessonName;
    }

    public void setLessonName(String lessonName) {
        LessonName = lessonName;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }
}
