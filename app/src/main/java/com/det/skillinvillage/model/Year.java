package com.det.skillinvillage.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Year {
    @SerializedName("Academic_ID")
    @Expose
    private String academicID;
    @SerializedName("Academic_Name")
    @Expose
    private String academicName;
    @SerializedName("Sandbox_ID")
    @Expose
    private String sandbox_ID;

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

    public String getSandbox_ID() {
        return sandbox_ID;
    }

    public void setSandbox_ID(String sandbox_ID) {
        this.sandbox_ID = sandbox_ID;
    }


    @Override
    public String toString() {
        return academicName;
    }
}
