package com.det.skillinvillage.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostScheduleLessonUpdateRequest {
    @SerializedName("Schedule_ID")
    @Expose
    private String scheduleID;
    @SerializedName("LessonQuestion_ID")
    @Expose
    private String lessonQuestionID;
    @SerializedName("ScheduleLesson_Answer")
    @Expose
    private String scheduleLessonAnswer;
    @SerializedName("User_ID")
    @Expose
    private String userID;
    @SerializedName("Lesson_Remarks")
    @Expose
    private String lessonRemarks;
    @SerializedName("Lesson_Status")
    @Expose
    private String lessonStatus;

    public String getScheduleID() {
        return scheduleID;
    }

    public void setScheduleID(String scheduleID) {
        this.scheduleID = scheduleID;
    }

    public String getLessonQuestionID() {
        return lessonQuestionID;
    }

    public void setLessonQuestionID(String lessonQuestionID) {
        this.lessonQuestionID = lessonQuestionID;
    }

    public String getScheduleLessonAnswer() {
        return scheduleLessonAnswer;
    }

    public void setScheduleLessonAnswer(String scheduleLessonAnswer) {
        this.scheduleLessonAnswer = scheduleLessonAnswer;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getLessonRemarks() {
        return lessonRemarks;
    }

    public void setLessonRemarks(String lessonRemarks) {
        this.lessonRemarks = lessonRemarks;
    }

    public String getLessonStatus() {
        return lessonStatus;
    }

    public void setLessonStatus(String lessonStatus) {
        this.lessonStatus = lessonStatus;
    }

}
