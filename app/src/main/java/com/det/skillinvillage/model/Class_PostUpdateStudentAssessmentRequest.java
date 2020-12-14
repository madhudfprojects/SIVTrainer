package com.det.skillinvillage.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Class_PostUpdateStudentAssessmentRequest {

    @SerializedName("User_ID")
    @Expose
    private String UserID;

    @SerializedName("Assessment_ID")
    @Expose
    private String AssesmentID;

    @SerializedName("Marks_ID")
    @Expose
    private String StudentID;

    //Student_Marks
    @SerializedName("Marks")
    @Expose
    private String StudentMarks;


    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getAssesmentID() {
        return AssesmentID;
    }

    public void setAssesmentID(String assesmentID) {
        AssesmentID = assesmentID;
    }

    public String getStudentID() {
        return StudentID;
    }

    public void setStudentID(String studentID) {
        StudentID = studentID;
    }

    public String getStudentMarks() {
        return StudentMarks;
    }

    public void setStudentMarks(String studentMarks) {
        StudentMarks = studentMarks;
    }
}
