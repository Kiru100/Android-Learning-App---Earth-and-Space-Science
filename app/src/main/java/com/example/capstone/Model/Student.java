package com.example.capstone.Model;

public class Student {
    private String SFname, SLname, SEmail, Ssection,Spicture;
    private String ChapterOnePreTest;
    private int Chapter_1_Progress,Chapter_2_Progress;


    public Student() {

    }

    public Student(String SFname, String SLname, String SEmail, String ssection) {
        this.SFname = SFname;
        this.SLname = SLname;
        this.SEmail = SEmail;
        Ssection = ssection;

    }

    public int getChapter_1_Progress() {
        return Chapter_1_Progress;
    }

    public void setChapter_1_Progress(int chapter_1_Progress) {
        Chapter_1_Progress = chapter_1_Progress;
    }

    public int getChapter_2_Progress() {
        return Chapter_2_Progress;
    }

    public void setChapter_2_Progress(int chapter_2_Progress) {
        Chapter_2_Progress = chapter_2_Progress;
    }

    public String getSpicture() {
        return Spicture;
    }

    public void setSpicture(String spicture) {
        Spicture = spicture;
    }

    public String getChapterOnePreTest() {
        return ChapterOnePreTest;
    }

    public void setChapterOnePreTest(String chapterOnePreTest) {
        ChapterOnePreTest = chapterOnePreTest;
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

}

