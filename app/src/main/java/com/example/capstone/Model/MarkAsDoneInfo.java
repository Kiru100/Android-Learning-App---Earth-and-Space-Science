package com.example.capstone.Model;


public class MarkAsDoneInfo {
    private String DateMark;
    private String LessonNameDone;
    private int LessonScore;
    private boolean isDone;
    private String Type;

    public MarkAsDoneInfo(){

    }

    public MarkAsDoneInfo(String dateMark, String lessonNameDone, int lessonScore, boolean isDone, String type) {
        this.DateMark = dateMark;
        this.LessonNameDone = lessonNameDone;
        this. LessonScore = lessonScore;
        this.isDone = isDone;
        this.Type = type;
    }

    public MarkAsDoneInfo(String dateMark, String lessonNameDone, boolean isDone, String type) {
        this.DateMark = dateMark;
        this.LessonNameDone = lessonNameDone;
        this.isDone = isDone;
        this.Type = type;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getDateMark() {
        return DateMark;
    }

    public void setDateMark(String dateMark) {
        DateMark = dateMark;
    }

    public String getLessonNameDone() {
        return LessonNameDone;
    }

    public void setLessonNameDone(String lessonNameDone) {
        LessonNameDone = lessonNameDone;
    }

    public int getLessonScore() {
        return LessonScore;
    }

    public void setLessonScore(int lessonScore) {
        LessonScore = lessonScore;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }
}
