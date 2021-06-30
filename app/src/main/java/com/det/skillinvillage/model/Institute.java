package com.det.skillinvillage.model;

import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Institute {

    @SerializedName("Institute_ID")
    @Expose
    private String instituteID;
    @SerializedName("Cluster_ID")
    @Expose
    private String clusterID;
    @SerializedName("Academic_ID")
    @Expose
    private String academicID;
    @SerializedName("Institute_Name")
    @Expose
    private String instituteName;

    public String getInstituteID() {
        return instituteID;
    }

    public void setInstituteID(String instituteID) {
        this.instituteID = instituteID;
    }

    public String getClusterID() {
        return clusterID;
    }

    public void setClusterID(String clusterID) {
        this.clusterID = clusterID;
    }

    public String getAcademicID() {
        return academicID;
    }

    public void setAcademicID(String academicID) {
        this.academicID = academicID;
    }

    public String getInstituteName() {
        return instituteName;
    }

    public void setInstituteName(String instituteName) {
        this.instituteName = instituteName;
    }

    @NonNull
    @Override
    public String toString() {
        return instituteName;
    }

}
