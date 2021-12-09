package com.example.capstone.Model;

public class Student {
    private String SFname, SLname, SEmail, Ssection,Spicture;
    private String ChapterOnePreTest;

    public Student() {

    }

    public Student(String SFname, String SLname, String SEmail, String ssection) {
        this.SFname = SFname;
        this.SLname = SLname;
        this.SEmail = SEmail;
        Ssection = ssection;

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

