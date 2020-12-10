package com.det.skillinvillage.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Cluster {

    @SerializedName("Cluster_ID")
    @Expose
    private String clusterID;
    @SerializedName("Sandbox_ID")
    @Expose
    private String sandboxID;
    @SerializedName("Academic_ID")
    @Expose
    private String academicID;
    @SerializedName("Cluster_Name")
    @Expose
    private String clusterName;

    public String getClusterID() {
        return clusterID;
    }

    public void setClusterID(String clusterID) {
        this.clusterID = clusterID;
    }

    public String getSandboxID() {
        return sandboxID;
    }

    public void setSandboxID(String sandboxID) {
        this.sandboxID = sandboxID;
    }

    public String getAcademicID() {
        return academicID;
    }

    public void setAcademicID(String academicID) {
        this.academicID = academicID;
    }

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    @Override
    public String toString() {
        return clusterName;
    }

}
