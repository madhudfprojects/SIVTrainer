package com.det.skillinvillage.model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class Class_PostUpdateStudentAssessmentList {
    @SerializedName("Assesment_ID")
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
    private Object studentMarks;
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

    public Object getStudentMarks() {
        return studentMarks;
    }

    public void setStudentMarks(Object studentMarks) {
        this.studentMarks = studentMarks;
    }

    public String getMarksStatus() {
        return marksStatus;
    }

    public void setMarksStatus(String marksStatus) {
        this.marksStatus = marksStatus;
    }

}
