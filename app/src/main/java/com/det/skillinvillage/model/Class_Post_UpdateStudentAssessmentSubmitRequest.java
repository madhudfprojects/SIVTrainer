package com.det.skillinvillage.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Class_Post_UpdateStudentAssessmentSubmitRequest {

    @SerializedName("User_ID")
    @Expose
    private String UserID;

    @SerializedName("Assesment_ID")
    @Expose
    private String AssesmentID;


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
}
