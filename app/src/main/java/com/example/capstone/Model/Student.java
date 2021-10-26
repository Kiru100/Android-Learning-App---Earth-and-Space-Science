package com.example.capstone.Model;

public class Student {
    private String SFname, SLname, SEmail, Ssection;
    private int ChapterProgress;

    public Student() {

    }

    public Student(String SFname, String SLname, String SEmail, String ssection, int chapterProgress) {
        this.SFname = SFname;
        this.SLname = SLname;
        this.SEmail = SEmail;
        Ssection = ssection;
        ChapterProgress = chapterProgress;
    }

    public String getSFname() {
        return SFname;
    }

    public void setSFname(String SFname) {
        this.SFname = SFname;
    }

    public String getSLname() {
        return SLname;
    }

    public void setSLname(String SLname) {
        this.SLname = SLname;
    }

    public String getSEmail() {
        return SEmail;
    }

    public void setSEmail(String SEmail) {
        this.SEmail = SEmail;
    }

    public String getSsection() {
        return Ssection;
    }

    public void setSsection(String ssection) {
        Ssection = ssection;
    }

    public int getChapterProgress() {
        return ChapterProgress;
    }

    public void setChapterProgress(int chapterProgress) {
        ChapterProgress = chapterProgress;
    }
}

