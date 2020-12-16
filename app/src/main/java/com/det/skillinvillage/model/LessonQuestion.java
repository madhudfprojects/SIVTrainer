package com.det.skillinvillage.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LessonQuestion {
    @SerializedName("ScheduleLesson_ID")
    @Expose
    private String scheduleLessonID;
    @SerializedName("Schedule_ID")
    @Expose
    private String scheduleID;
    @SerializedName("LessonQuestion_ID")
    @Expose
    private String lessonQuestionID;
    @SerializedName("ScheduleLesson_Answer")
    @Expose
    private String scheduleLessonAnswer;
    @SerializedName("Created_Date")
    @Expose
    private String createdDate;
    @SerializedName("Created_By")
    @Expose
    private String createdBy;
    @SerializedName("Question_Name")
    @Expose
    private String questionName;
    @SerializedName("Question_Order")
    @Expose
    private String questionOrder;

    public String getScheduleLessonID() {
        return scheduleLessonID;
    }

    public void setScheduleLessonID(String scheduleLessonID) {
        this.scheduleLessonID = scheduleLessonID;
    }

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

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getQuestionName() {
        return questionName;
    }

    public void setQuestionName(String questionName) {
        this.questionName = questionName;
    }

    public String getQuestionOrder() {
        return questionOrder;
    }

    public void setQuestionOrder(String questionOrder) {
        this.questionOrder = questionOrder;
    }

}
