package com.det.skillinvillage.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Count {
    @SerializedName("State_Count")
    @Expose
    private String stateCount;
    @SerializedName("District_Count")
    @Expose
    private String districtCount;
    @SerializedName("Taluka_Count")
    @Expose
    private String talukaCount;
    @SerializedName("Panchayat_Count")
    @Expose
    private Object panchayatCount;
    @SerializedName("Village_Count")
    @Expose
    private String villageCount;
    @SerializedName("Year_Count")
    @Expose
    private String yearCount;
    @SerializedName("Sync_ID")
    @Expose
    private String syncID;
    @SerializedName("Sandbox_Count")
    @Expose
    private String sandboxCount;
    @SerializedName("Cluster_Count")
    @Expose
    private String clusterCount;
    @SerializedName("Institute_Count")
    @Expose
    private String instituteCount;
    @SerializedName("School_Count")
    @Expose
    private String schoolCount;
    @SerializedName("Level_Count")
    @Expose
    private String levelCount;

    public String getStateCount() {
        return stateCount;
    }

    public void setStateCount(String stateCount) {
        this.stateCount = stateCount;
    }

    public String getDistrictCount() {
        return districtCount;
    }

    public void setDistrictCount(String districtCount) {
        this.districtCount = districtCount;
    }

    public String getTalukaCount() {
        return talukaCount;
    }

    public void setTalukaCount(String talukaCount) {
        this.talukaCount = talukaCount;
    }

    public Object getPanchayatCount() {
        return panchayatCount;
    }

    public void setPanchayatCount(Object panchayatCount) {
        this.panchayatCount = panchayatCount;
    }

    public String getVillageCount() {
        return villageCount;
    }

    public void setVillageCount(String villageCount) {
        this.villageCount = villageCount;
    }

    public String getYearCount() {
        return yearCount;
    }

    public void setYearCount(String yearCount) {
        this.yearCount = yearCount;
    }

    public String getSyncID() {
        return syncID;
    }

    public void setSyncID(String syncID) {
        this.syncID = syncID;
    }

    public String getSandboxCount() {
        return sandboxCount;
    }

    public void setSandboxCount(String sandboxCount) {
        this.sandboxCount = sandboxCount;
    }

    public String getClusterCount() {
        return clusterCount;
    }

    public void setClusterCount(String clusterCount) {
        this.clusterCount = clusterCount;
    }

    public String getInstituteCount() {
        return instituteCount;
    }

    public void setInstituteCount(String instituteCount) {
        this.instituteCount = instituteCount;
    }

    public String getSchoolCount() {
        return schoolCount;
    }

    public void setSchoolCount(String schoolCount) {
        this.schoolCount = schoolCount;
    }

    public String getLevelCount() {
        return levelCount;
    }

    public void setLevelCount(String levelCount) {
        this.levelCount = levelCount;
    }

}
