package com.det.skillinvillage.util;

import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubjectList {
    @SerializedName("Subject_ID")
    @Expose
    private String subjectID;
    @SerializedName("Subject_Name")
    @Expose
    private String subjectName;

    public String getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(String subjectID) {
        this.subjectID = subjectID;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    @NonNull
    @Override
    public String toString() {
        return subjectName;
    }
}
