package com.det.skillinvillage.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Assessment {

    @SerializedName("Assessment_ID")
    @Expose
    private String assessment_ID;
    @SerializedName("Assessment_Name")
    @Expose
    private String assessment_Name;

    public String getAssessment_ID() {
        return assessment_ID;
    }

    public void setAssessment_ID(String assessment_ID) {
        this.assessment_ID = assessment_ID;
    }

    public String getAssessment_Name() {
        return assessment_Name;
    }

    public void setAssessment_Name(String assessment_Name) {
        this.assessment_Name = assessment_Name;
    }
}
