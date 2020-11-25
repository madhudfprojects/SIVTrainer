package com.det.skillinvillage.model;

import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Year {
    @SerializedName("Academic_ID")
    @Expose
    private String academicID;
    @SerializedName("Academic_Name")
    @Expose
    private String academicName;

    public String getAcademicID() {
        return academicID;
    }

    public void setAcademicID(String academicID) {
        this.academicID = academicID;
    }

    public String getAcademicName() {
        return academicName;
    }

    public void setAcademicName(String academicName) {
        this.academicName = academicName;
    }
    @NonNull
    @Override
    public String toString() {
        return academicName;
    }

}
