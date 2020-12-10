package com.det.skillinvillage.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Level {
    @SerializedName("Level_ID")
    @Expose
    private String levelID;
    @SerializedName("Institute_ID")
    @Expose
    private String instituteID;
    @SerializedName("Cluster_ID")
    @Expose
    private String clusterID;
    @SerializedName("Academic_ID")
    @Expose
    private String academicID;
    @SerializedName("Level_Name")
    @Expose
    private String levelName;
    @SerializedName("Admission_Fee")
    @Expose
    private String admissionFee;

    public String getLevelID() {
        return levelID;
    }

    public void setLevelID(String levelID) {
        this.levelID = levelID;
    }

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

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public String getAdmissionFee() {
        return admissionFee;
    }

    public void setAdmissionFee(String admissionFee) {
        this.admissionFee = admissionFee;
    }

    @Override
    public String toString() {
        return levelName;
    }

}
