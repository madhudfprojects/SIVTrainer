package com.det.skillinvillage;

import android.support.annotation.NonNull;

public class Class_Assessment_Status {
    String Status;
    String assessment_instituteID;
    String assessment_levelID;

    public Class_Assessment_Status(){}

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getAssessment_instituteID() {
        return assessment_instituteID;
    }

    public void setAssessment_instituteID(String assessment_instituteID) {
        this.assessment_instituteID = assessment_instituteID;
    }

    public String getAssessment_levelID() {
        return assessment_levelID;
    }

    public void setAssessment_levelID(String assessment_levelID) {
        this.assessment_levelID = assessment_levelID;
    }

    @NonNull
    @Override
    public String toString() {
        return Status;
    }

}
