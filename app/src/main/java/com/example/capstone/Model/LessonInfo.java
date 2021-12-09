package com.example.capstone.Model;

public class LessonInfo extends ChapterInfo {
    private String LessonNumber;
    private String LessonName;
    private String Ltypes;
    private String YoutubeURL;
    private String TestName;
    private String IntroMessage;
    private String ChapterObjectives;
    private boolean isAvailable;
    private int TestItemNumber;
    private String FirstLineLessonLecture,SecondLineLessonLecture,ThirdLineLessonLecture,
                   FirstLessonImage,SecondLessonImage
                    ,FirstFigureNumber,SecondFigureNumber
                    ,YoutubeVideoTitle
                     ,LessonReference;


    public LessonInfo(){

    }

    public LessonInfo(String lessonNumber, String lessonName, String ltypes, String youtubeURL,
                      String testName, String introMessage,
                      String chapterObjectives, int testItemNumber) {
        LessonNumber = lessonNumber;
        LessonName = lessonName;
        Ltypes = ltypes;
        YoutubeURL = youtubeURL;
        TestName = testName;
        IntroMessage = introMessage;
        ChapterObjectives = chapterObjectives;
        TestItemNumber = testItemNumber;
    }


    public String getLessonReference() {
        return LessonReference;
    }

    public void setLessonReference(String lessonReference) {
        LessonReference = lessonReference;
    }

    @Override
    public boolean isAvailable() {
        return isAvailable;
    }

    @Override
    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public String getThirdLineLessonLecture() {
        return ThirdLineLessonLecture;
    }

    public void setThirdLineLessonLecture(String thirdLineLessonLecture) {
        ThirdLineLessonLecture = thirdLineLessonLecture;
    }

    public String getSecondFigureNumber() {
        return SecondFigureNumber;
    }

    public void setSecondFigureNumber(String secondFigureNumber) {
        SecondFigureNumber = secondFigureNumber;
    }

    public String getSecondLessonImage() {
        return SecondLessonImage;
    }

    public void setSecondLessonImage(String secondLessonImage) {
        SecondLessonImage = secondLessonImage;
    }

    public String getYoutubeVideoTitle() {
        return YoutubeVideoTitle;
    }

    public void setYoutubeVideoTitle(String youtubeVideoTitle) {
        YoutubeVideoTitle = youtubeVideoTitle;
    }

    public String getFirstFigureNumber() {
        return FirstFigureNumber;
    }

    public void setFirstFigureNumber(String firstFigureNumber) {
        FirstFigureNumber = firstFigureNumber;
    }

    public String getSecondLineLessonLecture() {
        return SecondLineLessonLecture;
    }

    public void setSecondLineLessonLecture(String secondLineLessonLecture) {
        SecondLineLessonLecture = secondLineLessonLecture;
    }

    public String getFirstLessonImage() {
        return FirstLessonImage;
    }

    public void setFirstLessonImage(String firstLessonImage) {
        FirstLessonImage = firstLessonImage;
    }

    public String getFirstLineLessonLecture() {
        return FirstLineLessonLecture;
    }

    public void setFirstLineLessonLecture(String firstLineLessonLecture) {
        FirstLineLessonLecture = firstLineLessonLecture;
    }

    public int getTestItemNumber() {
        return TestItemNumber;
    }

    public void setTestItemNumber(int testItemNumber) {
        TestItemNumber = testItemNumber;
    }

    public String getChapterObjectives() {
        return ChapterObjectives;
    }

    public void setChapterObjectives(String chapterObjectives) {
        ChapterObjectives = chapterObjectives;
    }

    public String getIntroMessage() {
        return IntroMessage;
    }

    public void setIntroMessage(String introMessage) {
        IntroMessage = introMessage;
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
