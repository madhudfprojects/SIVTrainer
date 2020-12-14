package com.det.skillinvillage.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Class_PostUpdateAssessmentSubmitList {
    @SerializedName("Assessment_ID")
    @Expose
    private Integer assesmentID;
    @SerializedName("User_ID")
    @Expose
    private Integer userID;
    @SerializedName("Marks_ID")
    @Expose
    private String studentID;
    @SerializedName("Marks")
    @Expose
    private String studentMarks;
    @SerializedName("Marks_Status")
    @Expose
    private String marksStatus;

    public Integer getAssesmentID() {
        return assesmentID;
    }

    public void setAssesmentID(Integer assesmentID) {
        this.assesmentID = assesmentID;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getStudentMarks() {
        return studentMarks;
    }

    public void setStudentMarks(String studentMarks) {
        this.studentMarks = studentMarks;
    }

    public String getMarksStatus() {
        return marksStatus;
    }

    public void setMarksStatus(String marksStatus) {
        this.marksStatus = marksStatus;
    }
}
